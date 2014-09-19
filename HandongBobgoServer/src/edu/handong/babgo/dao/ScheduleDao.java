package edu.handong.babgo.dao;

import edu.handong.babgo.model.Schedule;

public interface ScheduleDao {
	public Schedule getSchedule(String key);
	public void setSchedule(String key, Schedule s);
	public void deleteSchedule(String key);
}
