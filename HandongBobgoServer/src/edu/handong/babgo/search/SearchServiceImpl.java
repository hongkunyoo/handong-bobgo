package edu.handong.babgo.search;

import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;

import edu.handong.babgo.dao.ScheduleDao;
import edu.handong.babgo.dao.UserDao;
import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.model.User;
import edu.handong.babgo.util.GlobalVariables;

public class SearchServiceImpl implements SearchService {
	@Autowired ScheduleDao scheduleDao;
	@Autowired UserDao userDao;
	
	@Override
	public Schedule getScheduleBabgo(String key) {
		// TODO Auto-generated method stub
		User u = userDao.getUser(key);
		Schedule s = new Schedule();
		s.setSchedule(new Vector<Day>());
		s.setKey(GlobalVariables.FAILED);
		if(u == null) return s;
		return scheduleDao.getSchedule(key);
		
	}
	
	public void setScheduleDao(ScheduleDao scheduleDao){
		this.scheduleDao = scheduleDao;
	}
	
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
}
