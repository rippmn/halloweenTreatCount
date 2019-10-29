package com.rippmn.halloween.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="tt_event")
public class TrickorTreatEvent {

	@Column
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private Timestamp eventDateTime = new Timestamp(System.currentTimeMillis());
	
	@Column
	private int count;
	
	@Column
	private boolean adult = false;
//	
//	public boolean isAdult(){
//		return adult;
//	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
//	public String getTime(){
//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//		return sdf.format(eventDateTime);
//	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Timestamp getEventDateTime() {
		return eventDateTime;
	}
	public void setEventDateTime(Timestamp eventDateTime) {
		this.eventDateTime = eventDateTime;
	}
	
//	public String getYear(){
//		SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
//		return sdf.format(eventDateTime);
//		
//	}
	
	
	
	
}
