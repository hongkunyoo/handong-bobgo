package edu.handong.babgo.util;

import java.util.Vector;

import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Partner;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.model.User;

public class StringPrinter {
	public static String toString(Partner p){
		String str = ""+p.getDow() +
		p.getFixed() +
		p.getKey() + 
		p.getMeal() +
		p.getPartnerKey() +
		p.getPartnerName() +
		p.getPartnerPhone() +
		p.getState();
		return str;
	}
	public static String toString(Day d){
		String str = StringPrinter.toString(d.getBreakfast()) +
				StringPrinter.toString(d.getLunch()) +
				StringPrinter.toString(d.getDinner()) +
				d.getDate() +
				d.getDow() +
				d.getMonth();
		return str;
	}
	public static String toString(Schedule s){
		StringBuffer sb = new StringBuffer();
		Vector<Day> days = s.getSchedule();
		sb.append(s.getKey());
		for(int i = 0 ; i < 7 ; i++){
			sb.append(StringPrinter.toString(days.get(i)));
		}
		
		return sb.toString();
	}
	public static String toString(User u){
		String str = ""+u.getName() +
				u.getHisnetId() +
				u.getGender() +
				u.getMajor() +
				u.getPhone() + 
				u.getRegisterId() +
				u.getId() + 
				u.getRandom();
		return str;
	}
}
