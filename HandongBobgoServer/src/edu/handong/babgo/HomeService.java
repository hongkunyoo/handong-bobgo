package edu.handong.babgo;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import edu.handong.babgo.dao.MessageDao;
import edu.handong.babgo.dao.PartnerDao;
import edu.handong.babgo.dao.ScheduleDao;
import edu.handong.babgo.dao.UserDao;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.model.Partner;
import edu.handong.babgo.model.User;
import edu.handong.babgo.util.GlobalVariables;
import edu.handong.babgo.util.TestProvider;

public class HomeService {
	@Autowired MessageDao messageDao;
	@Autowired PartnerDao partnerDao;
	@Autowired UserDao userDao;
	@Autowired ScheduleDao scheduleDao;
	
	public void setMessage(String param){
		Message m = new Message();
		m.setFrom(param);
		m.setFromId("0101234");
		m.setFromPhone("0101234");
		m.setConfirm(1);
		m.setDate(2);
		m.setDow(1);
		m.setMeal("아침");
		m.setMessage("Let's EAT!");
		m.setMonth(1);
		m.setState(0);
		m.setTo("honggun");
		m.setToId("01037958626");
		
		messageDao.setMessage(m);
	}
	
	public List<Message> getMessages(){
		return messageDao.getMessages();

	}
	public void deleteMessage(String key){
		messageDao.deleteMessage(key);
	}
	
	public void setSchedule(String param){
		scheduleDao.setSchedule(param, new TestProvider().getSchedule());
	}
	
	public void deleteSchedule(String param){
		scheduleDao.deleteSchedule(param);
	}
	

	public void deletePartner(String key,int dow, String meal){
		partnerDao.deletePartner(key, dow, meal);
	}
	
	public void testSomething(String arg){
		Partner p = new Partner();
		p.setDow(0);
		p.setFixed(0);
		p.setKey("");
		p.setMeal(arg);
		p.setPartnerKey("parttestKey");
		p.setPartnerName("etsNlame");
		p.setPartnerPhone("010");
		p.setState(0);
		partnerDao.setPartner("0100000test", 0, "testMeal", p);
	}
	
	public String sendMessage(String regid){
		
		com.google.android.gcm.server.Sender sender = new com.google.android.gcm.server.Sender(GlobalVariables.SENDER_ID);
		com.google.android.gcm.server.Message.Builder builder = new com.google.android.gcm.server.Message.Builder();
		Result result = null;
		String registerId = regid;
		builder.collapseKey(registerId);
		builder.addData("notice", "notice");
		com.google.android.gcm.server.Message googleMessage = builder.build();
		try {
			result = sender.send(googleMessage, registerId, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
		
		return result.getMessageId();
	}
	
	public void setMessageDao(MessageDao messageDao){
		this.messageDao = messageDao;
	}
	
	public void setPartnerDao(PartnerDao partnerDao){
		this.partnerDao = partnerDao;
	}
	
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	
	public void setScheduleDao(ScheduleDao scheduleDao){
		this.scheduleDao = scheduleDao;
	}
}
