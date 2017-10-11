package com.rippmn.halloween.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rippmn.halloween.domain.TrickorTreatEvent;
import com.rippmn.halloween.persistence.TrickOrTreatEventRepository;
import com.rippmn.halloween.service.TheService;



@RestController
public class EventController {

	@Autowired
	private TrickOrTreatEventRepository repo;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");

	@Autowired
	TheService service;


	@RequestMapping("/getTT")
	public TrickorTreatEvent getTreatEvent(@RequestParam(value="id", required=true) long id){
		return repo.findOne(id);
	}

	@RequestMapping("/getTTs")
	public Iterable<TrickorTreatEvent> getTrickOrTreatEvents(){
		return repo.findAll();
	}

	@RequestMapping("/getPriorTTs")
	public Iterable<TrickorTreatEvent> getPriorTrickOrTreatEvents(){
		return repo.getPriorTTs();
	}

	@RequestMapping("/getTTsAfter/dateTime/{dateTime}")
	public Iterable<TrickorTreatEvent> getcurrentTrickOrTreatEvents(@PathVariable String dateTime)throws Exception{
		return repo.getCurrentTTs(sdf.parse(dateTime));
	}

	@RequestMapping("/greetings")
	public String greetings(){
		return "Greetings";
	}


	@RequestMapping(value="/trickOrTreat", method=RequestMethod.POST)
	public TrickorTreatEvent trickOrTreat( @RequestParam(value="count", required=true) int count){
		TrickorTreatEvent tte = new TrickorTreatEvent();
		tte.setCount(count);
		repo.save(tte);
		return tte;
	}

	@RequestMapping("/getTTsByYear/year/{year}")
	public Iterable<TrickorTreatEvent> getByYear(@PathVariable Integer year)throws Exception{
		return repo.getTtsByYear(year);
	}

	@RequestMapping("/getMinYear")
	public String getMinYear(){
		Calendar c = Calendar.getInstance();
		c.setTime(repo.getMinDate());

		return Integer.toString(c.get(Calendar.YEAR));
	}

	@RequestMapping("/getByTime/hour/{hour}/min/{min}")
	public Iterable<TrickorTreatEvent> getMinYear(@PathVariable Integer hour, @PathVariable Integer min){

		System.out.println(hour+":"+min);
		return repo.getEventByTime(hour, min-2, min-1);

	}

	@RequestMapping("/yearlyTotals")
	public Map<String, Integer> yearlyTotals(){
		
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
		
		
		for(TrickorTreatEvent tt: repo.findAll()){
			String year = sdf.format(tt.getEventDateTime());
			Integer count = hm.get(year);
			if(count == null) {
				count = tt.getCount();
			}else {
				count = count + tt.getCount();
			}
			hm.put(year, count);
		}
		
		return hm;
	}

	@RequestMapping("/totals")
	public Map<String, String> totals(){
		return service.getTotals();
	}

	@RequestMapping("/totalsByTime")
	public Map<String, List<Object>> totalsByTime(){

		return service.getTotalsByTime();
	}




	//TODO - add a method that returns totals based upon an integer

//
//	@RequestMapping(value="/trickOrTreat", method=RequestMethod.GET)
//	public TrickorTreatEvent trickOrTreatGet( @RequestParam(value="count", required=true) int count){
//		tte.setCount(count);
//		repo.save(tte);
//
//		return "success";
//
//	}


}
