package com.rippmn.halloween;

import java.util.Date;

public class GenerateEmptyData {

	public static void main(String[] args) {
		String[] years = new String[]{"2014","2013","2012","2011","2010","2009","2008","2007"};
		String[] hours = new String[]{"17","18","19","20","21"};
		String[] tenMins = new String[]{"0","1","2","3","4","5"};
		String[] mins = new String[]{"0","2","4","6","8",};
		
		for(String year:years){
			for(String hour:hours){
				for(String tenMin : tenMins){
					for(String min:mins){
						System.out.println("insert into tt_event (count, event_date_time) values(0, '".concat(year).concat("-10-31 ").concat(hour).concat(":").concat(tenMin).concat(min).concat(":00');"));
					}
				}
			}
		}
		
		System.out.println(new Date(1414809120000l));

	}

}
