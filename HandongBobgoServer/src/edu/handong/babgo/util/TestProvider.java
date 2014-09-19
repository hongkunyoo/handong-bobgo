package edu.handong.babgo.util;

import java.util.Vector;

import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.model.Partner;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.model.User;

public class TestProvider {
	
	public Partner getPartner(){
		
		int num = (((int)Math.random() * 100)) % 3;
		
		if(num == 0){
			Partner p = new Partner();
			
			p.setKey("oldKEY0");
			p.setDow(0);
			p.setFixed(0);
			p.setPartnerKey("partnerKey1");
			p.setPartnerName("partnerName1");
			p.setPartnerPhone("partnerPhone1");
			p.setState(0);
			p.setMeal("here to define meal1");
			return p;
		}
		else if(num == 1){
			Partner p = new Partner();
			
			p.setKey("oldKEY1");
			p.setDow(0);
			p.setFixed(0);
			p.setPartnerKey("partnerKey2");
			p.setPartnerName("partnerName2");
			p.setPartnerPhone("partnerPhone2");
			p.setState(0);
			p.setMeal("here to define meal2");
			return p;
		}
		else{
			Partner p = new Partner();
			
			p.setKey("oldKEY2");
			p.setDow(0);
			p.setFixed(0);
			p.setPartnerKey("partnerKey3");
			p.setPartnerName("partnerName3");
			p.setPartnerPhone("partnerPhone3");
			p.setState(0);
			p.setMeal("here to define meal3");
			return p;
		}
	}
	
	public Day getDay(){
		
		Day d = new Day();
		d.setDate(10);
		d.setMonth(1);
		d.setDow(3);
		d.setBreakfast(this.getPartner());
		d.setLunch(this.getPartner());
		d.setDinner(this.getPartner());
		
		return d;
	}
	
	public Schedule getSchedule(){
		Schedule s = new Schedule();
		s.setKey("123412");
		Vector<Day> days = new Vector<Day>();
		days.add(this.getDay());
		days.add(this.getDay());
		days.add(this.getDay());
		days.add(this.getDay());
		days.add(this.getDay());
		days.add(this.getDay());
		days.add(this.getDay());
		s.setSchedule(days);
		return s;
	}
	
	public User getUser(){
		User user = new User();
		user.setName("NoName");
		user.setPhone("01012345345");
		user.setMajor("CSEE");
		user.setHisnetId("hisnetId");
		user.setGender(0);
		user.setId("01012345235");
		user.setRandom(0);
		user.setRegisterId("GoogleRegisterId");
		
		return user;
	}
	
	public Message getMessage(){
		
		Message m = new Message();
		m.setConfirm(0);
		m.setDate(1);
		m.setDow(2);
		m.setFrom("from");
		m.setFromId("fromId");
		m.setKey("010TEST010-provider");
		m.setMeal("meal");
		m.setMessage("message");
		m.setMonth(1);
		m.setState(0);
		m.setTo("to");
		m.setToId("toId");
		
		return m;
	}
}
