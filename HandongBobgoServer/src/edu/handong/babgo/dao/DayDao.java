package edu.handong.babgo.dao;

import edu.handong.babgo.model.Day;

public interface DayDao {
	public Day getDay(String key, int dow);
	public void setDay(String key, int dow, Day day);
	public void deleteDay(String key, int dow);
}
