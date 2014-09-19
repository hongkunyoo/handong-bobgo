package edu.handong.babgo.dao;

import org.springframework.beans.factory.annotation.Autowired;

import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Partner;

public class DayDaoImpl implements DayDao {
	@Autowired PartnerDao partnerDao;
	
	@Override
	public Day getDay(String key, int dow) {
		// TODO Auto-generated method stub
		Partner breakfast = partnerDao.getPartner(key, dow, "breakfast");
		Partner lunch = partnerDao.getPartner(key, dow, "lunch");
		Partner dinner = partnerDao.getPartner(key, dow, "dinner");
		Day d = new Day();
		d.setBreakfast(breakfast);
		d.setLunch(lunch);
		d.setDinner(dinner);
		d.setDow(dow);
		
		return d;
	}

	@Override
	public void setDay(String key, int dow,  Day day) {
		// TODO Auto-generated method stub
		partnerDao.setPartner(key, dow, "breakfast", day.getBreakfast());
		partnerDao.setPartner(key, dow, "lunch", day.getLunch());
		partnerDao.setPartner(key, dow, "dinner", day.getDinner());
		
	}
	
	@Override
	public void deleteDay(String key, int dow){
		partnerDao.deletePartner(key, dow, "breakfast");
		partnerDao.deletePartner(key, dow, "lunch");
		partnerDao.deletePartner(key, dow, "dinner");
	}
	
	public void setPartnerDao(PartnerDao partnerDao){
		this.partnerDao = partnerDao;
	}
}
