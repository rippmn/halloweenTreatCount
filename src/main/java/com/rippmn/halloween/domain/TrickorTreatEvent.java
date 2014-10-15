package com.rippmn.halloween.domain;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TrickorTreatEvent {

	@Column
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private Date eventDateTime = new Date(System.currentTimeMillis());
	
	@Column
	private int count;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(eventDateTime);
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getEventDateTime() {
		return eventDateTime;
	}
	public void setEventDateTime(Date eventDateTime) {
		this.eventDateTime = eventDateTime;
	}
	
	
	
	
	
}
