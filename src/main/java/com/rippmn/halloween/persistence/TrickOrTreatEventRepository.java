package com.rippmn.halloween.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rippmn.halloween.domain.TrickorTreatEvent;

public interface TrickOrTreatEventRepository extends CrudRepository<TrickorTreatEvent, Long>{

	@Query("select tt from TrickorTreatEvent tt where tt.eventDateTime >= CURRENT_DATE")
	public List<TrickorTreatEvent> getCurrentTTs();
	
	@Query("select tt from TrickorTreatEvent tt where tt.eventDateTime < CURRENT_DATE")
	public List<TrickorTreatEvent> getPriorTTs();
}
