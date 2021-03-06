package edu.handong.babgo.util;

import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;
import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Partner;
import edu.handong.babgo.model.Schedule;

public class PreferenceHelper {
	Context context;
	public PreferenceHelper(Context context){
		this.context = context;
	}
	
	public void setHisnetId(String hisnetId){
		SharedPreferences spf = context.getSharedPreferences(edu.handong.babgo.common.GlobalVariables.SETTINGS, 0);
		SharedPreferences.Editor edit = spf.edit();
		edit.putString(GlobalVariables.HISNET_ID, hisnetId);
		edit.commit();
	}
	public void setPassword(String password){
		SharedPreferences spf = context.getSharedPreferences(edu.handong.babgo.common.GlobalVariables.SETTINGS, 0);
		SharedPreferences.Editor edit = spf.edit();
		edit.putString(GlobalVariables.PASSWORD, password);
		edit.commit();
	}
	public void setPhone(String phone){
		SharedPreferences spf = context.getSharedPreferences(edu.handong.babgo.common.GlobalVariables.SETTINGS, 0);
		SharedPreferences.Editor edit = spf.edit();
		edit.putString(GlobalVariables.PHONE, phone);
		edit.commit();
	}
	public void setDay(String key, Day d){
		SharedPreferences spf = context.getSharedPreferences("day", 0);
		SharedPreferences.Editor edit = spf.edit();
		edit.putInt("dayDate"+key, d.getDate());
		edit.putInt("dayDow"+key, d.getDow());
		edit.putInt("dayMonth"+key, d.getMonth());
		edit.commit();
		this.setPartner(key+"breakfast", d.getBreakfast());
		this.setPartner(key+"lunch", d.getLunch());
		this.setPartner(key+"dinner", d.getDinner());
	}
	public void setPartner(String key, Partner p){
		SharedPreferences spf = context.getSharedPreferences("partner", 0);
		SharedPreferences.Editor edit = spf.edit();
		edit.putString("partnerKey"+key, p.getKey());
		edit.putString("partnerMeal"+key, p.getMeal());
		edit.putString("partnerPartnerKey"+key, p.getPartnerKey());
		edit.putString("partnerPartnerName"+key, p.getPartnerName());
		edit.putString("partnerPartnerPhone"+key, p.getPartnerPhone());
		edit.putInt("partnerDow"+key, p.getDow());
		edit.putInt("partnerFixed"+key, p.getFixed());
		edit.putInt("partnerState"+key, p.getState());
		edit.commit();
	}
	public void setSchedule(Schedule s){
		SharedPreferences spf = context.getSharedPreferences(edu.handong.babgo.common.GlobalVariables.SETTINGS, 0);
		SharedPreferences.Editor edit = spf.edit();
		edit.putString(GlobalVariables.SCHEDULE, s.getKey());
		edit.commit();
		Vector<Day> days = s.getSchedule();
		for(int i = 0 ; i < days.size() ; i++){
			setDay(s.getKey()+i,days.get(i));
		}
	}
	public String getHisnetId(){
		SharedPreferences spf = context.getSharedPreferences(edu.handong.babgo.common.GlobalVariables.SETTINGS, 0);
		return spf.getString(GlobalVariables.HISNET_ID, "");
	}
	public String getPassword(){
		SharedPreferences spf = context.getSharedPreferences(edu.handong.babgo.common.GlobalVariables.SETTINGS, 0);
		return spf.getString(GlobalVariables.PASSWORD, "");
	}
	public String getPhone(){
		SharedPreferences spf = context.getSharedPreferences(edu.handong.babgo.common.GlobalVariables.SETTINGS, 0);
		return spf.getString(GlobalVariables.PHONE, "");
	}
	
	public Partner getPartner(String key){
		Partner p = new Partner();
		SharedPreferences spf = context.getSharedPreferences("partner", 0);
		p.setKey(spf.getString("partnerKey"+key, ""));
		p.setMeal(spf.getString("partnerMeal"+key, ""));
		p.setPartnerKey(spf.getString("partnerPartnerKey"+key, ""));
		p.setPartnerName(spf.getString("partnerPartnerName"+key, ""));
		p.setPartnerPhone(spf.getString("partnerPartnerPhone"+key, ""));
		p.setDow(spf.getInt("partnerDow"+key, 0));
		p.setFixed(spf.getInt("partnerFixed"+key, 0));
		p.setState(spf.getInt("partnerState"+key, 0));
		
		return p;
	}
	public Day getDay(String key){
		Day d = new Day();
		SharedPreferences spf = context.getSharedPreferences("day", 0);
		d.setDate(spf.getInt("dayDate"+key, 0));
		d.setDow(spf.getInt("dayDow"+key, 0));
		d.setMonth(spf.getInt("dayMonth"+key, 0));
		d.setBreakfast(this.getPartner(key+"breakfast"));
		d.setLunch(this.getPartner(key+"lunch"));
		d.setDinner(this.getPartner(key+"dinner"));
		
		return d;
	}
	
	public Schedule getSchedule(){
		Schedule s = new Schedule();
		Vector<Day> days = new Vector<Day>();
		SharedPreferences spf = context.getSharedPreferences(edu.handong.babgo.common.GlobalVariables.SETTINGS, 0);
		s.setKey(spf.getString(GlobalVariables.SCHEDULE, ""));
		
		for(int i = 0 ; i < 7 ; i++){
			days.add(this.getDay(s.getKey()+i));
		}
		s.setSchedule(days);
		return s;
	}
}
