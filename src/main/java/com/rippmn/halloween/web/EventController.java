package com.rippmn.halloween.web;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rippmn.halloween.domain.TrickorTreatEvent;
import com.rippmn.halloween.persistence.TrickOrTreatEventRepository;

@RestController
public class EventController {

	@Autowired
	private TrickOrTreatEventRepository repo;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
	
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

	
	
	@RequestMapping(value="/trickOrTreat", method=RequestMethod.POST)
	public void trickOrTreat( @RequestParam(value="count", required=true) int count){
		TrickorTreatEvent tte = new TrickorTreatEvent();
		tte.setCount(count);
		repo.save(tte);
	}
	
}
