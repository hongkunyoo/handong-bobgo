package edu.handong.babgo.settings;

import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.model.User;

public interface SettingsService {
	public int setSchedule(String key, Schedule s);
	public int setUser(String key, User u);
	
}
