package edu.handong.babgo.todays;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;

import edu.handong.babgo.dao.DayDao;
import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Partner;

public class TodaysServiceImpl implements TodaysService {
	//@Autowired TodaysDao todaysDao;
	@Autowired DayDao dayDao;
	
	@Override
	public Day getTodaysBabgo(String id) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
		
		Day today = dayDao.getDay(id, cal.get(Calendar.DAY_OF_WEEK)-1);
		Partner breakfast = today.getBreakfast();
		Partner lunch = today.getLunch();
		Partner dinner = today.getDinner();
		
		if(breakfast.getDate() != 0 && breakfast.getDate() != cal.get(Calendar.DATE)) today.setBreakfast(new Partner());
		if(lunch.getDate() != 0 && lunch.getDate() != cal.get(Calendar.DATE)) today.setLunch(new Partner());
		if(dinner.getDate() != 0 && dinner.getDate() != cal.get(Calendar.DATE)) today.setDinner(new Partner());
		return today;
	}

	@Override
	public Day getTodaysBabgo(String id, int dow) {
		// TODO Auto-generated method stub
		return dayDao.getDay(id, dow);
	}

	@Override
	public int putDay(String id, Day d) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
		dayDao.setDay(id, cal.get(Calendar.DAY_OF_WEEK)-1, d);
		
		return 1;
	}
	public void setDayDao(DayDao dayDao){
		this.dayDao = dayDao;
	}
}
