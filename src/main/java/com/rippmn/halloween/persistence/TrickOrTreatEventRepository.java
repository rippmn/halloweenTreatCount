package com.rippmn.halloween.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rippmn.halloween.domain.TrickorTreatEvent;


public interface TrickOrTreatEventRepository extends CrudRepository<TrickorTreatEvent, Long>{

	@Query("select tt from TrickorTreatEvent tt where tt.eventDateTime >= :d")
	List<TrickorTreatEvent> getCurrentTTs(Date d);
	
	@Query("select tt from TrickorTreatEvent tt where tt.eventDateTime < CURRENT_DATE")
	List<TrickorTreatEvent> getPriorTTs();
	
	@Query("select tt from TrickorTreatEvent tt where YEAR(tt.eventDateTime) = :year")
	List<TrickorTreatEvent> getTtsByYear(Integer year);
	
	@Query("select sum(tt.count) from TrickorTreatEvent tt where YEAR(tt.eventDateTime) = :year")
	Integer getTtsTotalByYear(Integer year);
	
	
	@Query("select MIN(tt.eventDateTime) from TrickorTreatEvent tt")
	Date getMinDate();
	
	@Query("select tt from TrickorTreatEvent tt where HOUR(tt.eventDateTime) = :hour and (MINUTE(tt.eventDateTime) = :minMinute or MINUTE(tt.eventDateTime) = :maxMinute)")
	List<TrickorTreatEvent> getEventByTime(int hour, int minMinute, int maxMinute);

	@Query("select TIME(max(tt.eventDateTime)), YEAR(tt.eventDateTime) from TrickorTreatEvent tt group by YEAR(tt.eventDateTime)")
	List<List<String>> getLastTimeByYear();
		
}
