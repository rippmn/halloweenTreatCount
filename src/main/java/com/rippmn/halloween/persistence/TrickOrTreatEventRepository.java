package com.rippmn.halloween.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rippmn.halloween.domain.TrickorTreatEvent;

public interface TrickOrTreatEventRepository extends CrudRepository<TrickorTreatEvent, Long>{

	@Query("select tt from TrickorTreatEvent tt where tt.eventDateTime >= ?")
	public List<TrickorTreatEvent> getCurrentTTs(Date d);
	
	@Query("select tt from TrickorTreatEvent tt where tt.eventDateTime < CURRENT_DATE")
	public List<TrickorTreatEvent> getPriorTTs();
	
	
}
