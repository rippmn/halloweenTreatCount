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
	
	@Query("select tt from TrickorTreatEvent tt where YEAR(tt.eventDateTime) = ?")
	public List<TrickorTreatEvent> getTtsByYear(Integer year);
	
	@Query("select MIN(tt.eventDateTime) from TrickorTreatEvent tt")
	public Date getMinDate();
	
	@Query("select tt from TrickorTreatEvent tt where HOUR(tt.eventDateTime) = ?1 and (MINUTE(tt.eventDateTime) = ?2 or MINUTE(tt.eventDateTime) = ?3)")
	public List<TrickorTreatEvent> getEventByTime(int hour, int minMinute, int maxMinute);
		
}
