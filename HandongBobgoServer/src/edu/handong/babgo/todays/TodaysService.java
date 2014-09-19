package edu.handong.babgo.todays;

import edu.handong.babgo.model.Day;

public interface TodaysService {
	
	public Day getTodaysBabgo(String id);
	public Day getTodaysBabgo(String id, int dow);
	public int putDay(String id, Day d);
}
