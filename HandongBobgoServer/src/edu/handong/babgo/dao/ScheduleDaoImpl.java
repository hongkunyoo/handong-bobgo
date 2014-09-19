package edu.handong.babgo.dao;

import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;

import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Schedule;

public class ScheduleDaoImpl implements ScheduleDao{
	@Autowired DayDao dayDao;
	
	@Override
	public Schedule getSchedule(String key) {
		// TODO Auto-generated method stub
		Schedule s = new Schedule();
		Vector<Day> list = new Vector<Day>();
		for(int i = 0 ; i < 7 ; i++){
			list.add(dayDao.getDay(key, i));
		}
		s.setSchedule(list);
		s.setKey(key);
		
		return s;
	}

	@Override
	public void setSchedule(String key, Schedule s) {
		// TODO Auto-generated method stub
		Vector<Day> list = s.getSchedule();
		
		for(int i = 0 ; i < list.size() ; i++){
			Day d = list.get(i);
			dayDao.setDay(key, i, d);
		}
	}
	
	@Override
	public void deleteSchedule(String key){
		
		for(int i = 0 ; i < 7 ; i++){
			dayDao.deleteDay(key, i);
		}
	}
	
	public void setDayDao(DayDao dayDao){
		this.dayDao = dayDao;
	}

}
