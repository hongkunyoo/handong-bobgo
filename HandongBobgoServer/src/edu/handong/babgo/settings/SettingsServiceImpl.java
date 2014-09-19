package edu.handong.babgo.settings;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.handong.babgo.dao.ScheduleDao;
import edu.handong.babgo.dao.UserDao;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.model.User;

public class SettingsServiceImpl implements SettingsService {
	@Autowired ScheduleDao scheduleDao;
	@Autowired UserDao userDao;
	
	@Override
	public int setSchedule(String key, Schedule s) {
		// TODO Auto-generated method stub
		scheduleDao.setSchedule(key, s);
		
		return 1;
	}
	
	@Override
	public int setUser(String key, User u) {
		// TODO Auto-generated method stub
		//u.getHisnetId();
		List<User> userList = userDao.getUsers();
		for(int i = 0 ; i < userList.size() ; i++){
			if(userList.get(i).getPhone().equals(u.getPhone()))
				;//user
		}
		userDao.setUser(key, u);
		
		return 1;
	}
	
	
	public void setScheduleDao(ScheduleDao scheduleDao){
		this.scheduleDao = scheduleDao;
	}
	
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	

}
