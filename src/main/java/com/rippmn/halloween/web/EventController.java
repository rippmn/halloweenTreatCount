package com.rippmn.halloween.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rippmn.halloween.domain.TrickorTreatEvent;
import com.rippmn.halloween.persistence.TrickOrTreatEventRepository;

@RestController
public class EventController {

	@Autowired
	private TrickOrTreatEventRepository repo;
	
	
	@RequestMapping("/getTT")
	public TrickorTreatEvent getTreatEvent(@RequestParam(value="id", required=true) long id){
		return repo.findOne(id);
	}

	@RequestMapping("/getTTs")
	public Iterable<TrickorTreatEvent> getTrickOrTreatEvents(){
		return repo.findAll();
	}
	
	@RequestMapping("/trickOrTreat")
	public void trickOrTreat(@RequestParam(value="count", required=true) int count){
		TrickorTreatEvent tte = new TrickorTreatEvent();
		tte.setCount(count);
		repo.save(tte);
	}
	
}
