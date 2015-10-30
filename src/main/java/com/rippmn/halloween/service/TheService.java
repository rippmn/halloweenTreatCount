package com.rippmn.halloween.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rippmn.halloween.domain.TrickorTreatEvent;
import com.rippmn.halloween.persistence.TrickOrTreatEventRepository;

@Component
public class TheService {

	@Autowired
	private TrickOrTreatEventRepository repo;
	
	private HashMap<String, List<Object>> theData;
	
	private List<Object> labels = new ArrayList<Object>();
	
	private Calendar c = Calendar.getInstance();
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
	
	@PostConstruct
	public void initialize(){
		System.out.println("initializing");
		
		theData = new HashMap<String, List<Object>>();
		
		//theData.put("labels", labels);
		 
		 c.set(Calendar.HOUR_OF_DAY, 0);
		 c.set(Calendar.MINUTE, 0);
		 c.set(Calendar.SECOND, 0);
		 
		 
		while(c.getTimeInMillis() < System.currentTimeMillis()-120000l){
			
			//this is where we call to the thing that updates based upon time  (note the calendar probaby needs to be in the service)
			this.updateData();
			c.setTimeInMillis(c.getTimeInMillis()+120000l);
						
		}

		System.out.println(labels);
		System.out.println(labels.size());
		for(String key:theData.keySet()){
			System.out.println(theData.get(key).size());
		}
		
	}
	
	public void updateData(){
		
		Calendar eventCal = Calendar.getInstance();
		String year;
		
		int min = c.get(Calendar.MINUTE);
		
		if(min == 0)
			min = 60;
		
		List<TrickorTreatEvent> events = repo.getEventByTime(c.get(Calendar.HOUR_OF_DAY), min-2, min-1);
		
		
		if(labels.size() > 0 || (events != null && events.size() > 0)){
			labels.add(sdf.format(c.getTime()));
		}
		
		//so if we have labels already we need to transfer totals
		if(labels.size() > 0){
			for(String key: theData.keySet()){
				theData.get(key).add(theData.get(key).get((theData.get(key).size()-1)));
			}
		}
		
		for (TrickorTreatEvent event :events){
			eventCal.setTime(event.getEventDateTime());
			
			year = Integer.toString(eventCal.get(Calendar.YEAR));
			
			//get the oldest data 
			List<Object> yrData = theData.get(year);
			
			if(yrData != null){

				yrData.set(yrData.size()-1, ((Integer)yrData.get(yrData.size()-1))+event.getCount());
				
			}else{
				yrData = new ArrayList<Object>();
				theData.put(year, yrData);
				yrData.add(event.getCount());
			}
			
		}
	}
	
	public Map<String, String> getTotals(){
		
		TreeMap<String, String> totals = new TreeMap<String, String>();
		
		for(String key: theData.keySet()){
			totals.put(key, theData.get(key).get(theData.get(key).size()-1).toString());
		}
		
		return totals;
	}
	
}
