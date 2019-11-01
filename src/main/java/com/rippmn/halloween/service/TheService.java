package com.rippmn.halloween.service;

import com.rippmn.halloween.domain.TrickorTreatEvent;
import com.rippmn.halloween.persistence.TrickOrTreatEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

//TODO - DOCUMENTION....
//TODO - make this resetable to restart calcs

@Component
@EnableScheduling
public class TheService {

	//the date offset is to adjust for server times in UTC  (1700 CDT == 2200 UTC, so we adjust all times by the offset of 5 for CENTRAL)
	private static final int DATE_OFFSET = Integer.parseInt(System.getenv("DATE_OFFSET")!=null?System.getenv("DATE_OFFSET"):"0");

	@Autowired
	private TrickOrTreatEventRepository repo;

	private HashMap<String, List<Object>> theData;

	private List<Object> labels = new ArrayList<>();

	private Calendar c = Calendar.getInstance();
	private Calendar labelC = Calendar.getInstance();

	private Date endTime;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");

	@PostConstruct
	public void initialize(){
		
		System.out.println("initializing");

		theData = new HashMap<>();

		 //sets the base time
		 c.set(Calendar.MONTH, Calendar.OCTOBER);
		 c.set(Calendar.DATE, 31);
		 c.set(Calendar.MINUTE, 0);
		 c.set(Calendar.SECOND, 0);

		 //the logic about this is all messed up due to issues with time conversions
		 //these are fine to use local time as adjustments to UTC happen later
		 int endTimeHour = System.getenv("END_HOUR")!=null?Integer.parseInt(System.getenv("END_HOUR")):21;
		 int startTimeHour = System.getenv("START_HOUR")!=null?Integer.parseInt(System.getenv("START_HOUR")):17;

		 //set to end so we can get the end time
		 c.set(Calendar.HOUR_OF_DAY, endTimeHour);
		 endTime = new Date(c.getTimeInMillis()+(DATE_OFFSET*60*60*1000));
		 
		 c.set(Calendar.HOUR_OF_DAY, startTimeHour + DATE_OFFSET);

		//this runs at beginning to create all the first data
		while(c.getTimeInMillis() <= endTime.getTime() && c.getTimeInMillis() < System.currentTimeMillis()){

			this.updateData();

		}

		System.out.println(labels);
		System.out.println(labels.size());
		for(String key:theData.keySet()){
			System.out.println(theData.get(key).size());
		}

	}

	@Scheduled(cron="0 */2 * * * *")
	public void updateData(){

		System.out.println("running update-"+ c.getTime() +":"+labelC.getTime());

		//skip not time to go yet
		if(c.getTimeInMillis() > System.currentTimeMillis()){
			System.out.println("Not updated as current time before:"+c);
			return;
		}


		//skip if we are past end time
		if(c.getTimeInMillis() > endTime.getTime()){
			System.out.println("After End Time:"+endTime);
			return;
		}

		labelC.setTimeInMillis(c.getTime().getTime()-(DATE_OFFSET*60*60*1000));


		Calendar eventCal = Calendar.getInstance();
		String year;

		int min = c.get(Calendar.MINUTE);
		int hour = c.get(Calendar.HOUR_OF_DAY);


		if(min == 0){
			min = 60;
			hour--;
			if(hour == -1){
				hour = 23;
			}
		}


		List<TrickorTreatEvent> events = repo.getEventByTime(hour, min-2, min-1);
		if(events.size() > 0)
		{
			System.out.println(sdf.format(c.getTime()) +":"+events.size());
		}

		if((labels.size() > 0) || (events.size() > 0)){
			labels.add(sdf.format(labelC.getTime()));
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
				yrData = new ArrayList<>();
				theData.put(year, yrData);
				yrData.add(event.getCount());
			}

		}

		//this needs to be able to deal with different intervals
		c.setTimeInMillis(c.getTimeInMillis()+120000L);

	}

	public Map<String, String> getTotals(){

		TreeMap<String, String> totals = new TreeMap<>();

		for(String key: theData.keySet()){
			totals.put(key, theData.get(key).get(theData.get(key).size()-1).toString());
		}

		return totals;
	}

	public Map<String, List<Object>> getTotalsByTime(){

		TreeMap<String, List<Object>>	 totals = new TreeMap<>();

		totals.put("labels", labels);

		for(String key: theData.keySet()){
			totals.put(key, theData.get(key));
		}


		return totals;
	}
	
	public Integer getYearTotal(int year) {
		return repo.getTtsTotalByYear(year);
		
	}

	public List<List<String>> getMaxTimeByYear(){
		return repo.getLastTimeByYear();
	}

}
