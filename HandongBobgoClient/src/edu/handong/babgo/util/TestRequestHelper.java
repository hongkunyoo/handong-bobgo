package edu.handong.babgo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.util.Log;
import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.model.Partner;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.model.User;

public class TestRequestHelper implements RequestHelper {
	TestProvider provider = new TestProvider();
	PreferenceHelper helper = new PreferenceHelper((Context)GlobalVariables.getInstance().getData("context"));
	public Day getTodaysBabgo(String id) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Day d = new Day();
		Partner p1 = new Partner();
		Partner p2 = new Partner();
		Partner p3 = new Partner();
		p1.setPartnerName("홍길동");
		p2.setPartnerName("아무개");
		p3.setPartnerName("김영자");
		d.setBreakfast(p1);
		d.setLunch(p2);
		d.setDinner(p3);
		return d;
	}

	public Schedule getScheduleBabo(String id) {
		// TODO Auto-generated method stub
		Schedule s;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s = provider.getSchedule();
		Vector<Day> days = s.getSchedule();
		for(int i = 0 ; i < days.size() ; i++){
			days.get(i).getBreakfast().setPartnerName("홍길동");
			days.get(i).getLunch().setPartnerName("");
		}
			
		return s;
	}

	public User getRandomBabgo(String id) {
		// TODO Auto-generated method stub
		return provider.getUser();
	}

	public Message askMeal(Message m) {
		// TODO Auto-generated method stub
		return provider.getMessage();
	}

	public Message setSchedule(String id, Schedule s) {
		// TODO Auto-generated method stub
		helper.setSchedule(s);
		Message m = new Message();
		m.setMessage(GlobalVariables.SUCCEED);
		return m;
	}

	public Message setAccount(String id, String hisnetId, String password,
			int random) {
		// TODO Auto-generated method stub
		helper.setHisnetId(hisnetId);
		helper.setPhone(id);
		helper.setPassword(password);
		Message m = new Message();
		m.setMessage(GlobalVariables.SUCCEED);
		return m;
	}

	public List<Message> getNewUpdates(String id) {
		// TODO Auto-generated method stub
		List<Message> list = new ArrayList<Message>();
		//list.add(provider.getMessage());
		//list.add(provider.getMessage());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Message acceptMeal(Message m) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return provider.getMessage();
	}

	public Message refuseMeal(Message m) {
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return provider.getMessage();
	}

	public Message sendMessage(String id, Message m) {
		// TODO Auto-generated method stub
		if("clearSchedule".equals(m.getMessage())){
			Log.e("ERROR","in sendMessage");
			Schedule s = new Schedule();
			Vector<Day> days = new Vector<Day>();
			for(int i = 0 ; i < 7 ; i++){
				Day d = new Day();
				d.setBreakfast(new Partner());
				d.setLunch(new Partner());
				d.setDinner(new Partner());
				d.setDow(0);
				days.add(d);
			}
			s.setSchedule(days);
			this.helper.setSchedule(s);
		}
		Message m2 = new Message();
		m2.setMessage(GlobalVariables.SUCCEED);
		return m2;
	}

	public List<User> getRandomBabgo(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
