package com.rippmn.halloween.web;

import com.rippmn.halloween.domain.TrickorTreatEvent;
import com.rippmn.halloween.persistence.TrickOrTreatEventRepository;
import com.rippmn.halloween.service.TheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;



@RestController
public class EventController {

	@Autowired
	private TrickOrTreatEventRepository repo;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");

	@Autowired
	private TheService service;


	@RequestMapping("/getTT/id/{id}")
	public TrickorTreatEvent getTreatEvent(@PathVariable long id){
		java.util.Optional<TrickorTreatEvent> ttEvent = repo.findById(id);
		return ttEvent.orElse(null);
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
	public Integer trickOrTreat( @RequestParam(value="count", required=true) int count){
		TrickorTreatEvent tte = new TrickorTreatEvent();
		tte.setCount(count);
		repo.save(tte);
		
		return thisYear();
	}

	@RequestMapping("/thisYear")
	public Integer thisYear() {
		return service.getYearTotal(new GregorianCalendar().get(GregorianCalendar.YEAR));
	}
	
	@RequestMapping("/getTTsByYear/year/{year}")
	public Iterable<TrickorTreatEvent> getByYear(@PathVariable Integer year){
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
		
		HashMap<String, Integer> hm = new HashMap<>();
		
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

	@CrossOrigin(origins="*")
	@RequestMapping("/totals")
	public Map<String, String> totals(){
		return service.getTotals();
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping("/totalsByTime")
	public Map<String, List<Object>> totalsByTime(){

		return service.getTotalsByTime();
	}




	//TODO - add a method that returns totals based upon an integer

//
//	@RequestMapping(value="/trickOrTreat", method=RequestMethod.GET)
//		tte.setCount(count);
//		repo.save(tte);
//
//		return "success";
//
//	}


}
