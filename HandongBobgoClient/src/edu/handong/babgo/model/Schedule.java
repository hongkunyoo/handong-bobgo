package edu.handong.babgo.model;

import java.util.Vector;

public class Schedule {
	private Vector<Day> schedule;
	private String key = "";
	
	
	public Vector<Day> getSchedule(){
		return schedule;
	}
	public void setSchedule(Vector<Day> list){
		schedule = list;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}
