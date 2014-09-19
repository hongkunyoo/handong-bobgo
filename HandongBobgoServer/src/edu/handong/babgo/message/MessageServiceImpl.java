package edu.handong.babgo.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import edu.handong.babgo.dao.MessageDao;
import edu.handong.babgo.dao.ScheduleDao;
import edu.handong.babgo.dao.UserDao;
import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.model.Partner;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.model.User;
import edu.handong.babgo.util.GlobalVariables;

public class MessageServiceImpl implements MessageService {
	@Autowired MessageDao messageDao;
	@Autowired ScheduleDao scheduleDao;
	@Autowired UserDao userDao;
	
	@Override
	public List<Message> getNewUpdates(String key) {
		// TODO Auto-generated method stub
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
		// 고정이 아니고 날짜가 지난 약속들은 삭제한다.
		Schedule mySchedule = scheduleDao.getSchedule(key);
		List<Day> days = mySchedule.getSchedule();
		for(int i = 0 ; i < days.size() ; i++){
			Day d = days.get(i);
			if(d.getBreakfast().getFixed() == 1) continue;
			if(d.getBreakfast().getDate() < cal.get(Calendar.DATE) )
				if(d.getBreakfast().getMonth() <= cal.get(Calendar.MONTH)+1)
					d.setBreakfast(new Partner());
			if(d.getLunch().getFixed() == 1) continue;
			if(d.getLunch().getDate() < cal.get(Calendar.DATE) )
				if(d.getLunch().getMonth() <= cal.get(Calendar.MONTH)+1)
					d.setLunch(new Partner());
			if(d.getDinner().getFixed() == 1) continue;
			if(d.getDinner().getDate() < cal.get(Calendar.DATE) )
				if(d.getDinner().getMonth() <= cal.get(Calendar.MONTH)+1)
					d.setDinner(new Partner());
		}
		scheduleDao.setSchedule(key, mySchedule);
		
		// 해당 성보를 찾는다.
		List<Message> returnMessages = new ArrayList<Message>();
		List<Message> messages = messageDao.getMessages();
		for(int i = 0 ; i < messages.size() ; i++){
			if(messages.get(i).getToId().equals(key)){
				returnMessages.add(messages.get(i));
			}
		}
		
		for(int i = 0 ; i < returnMessages.size() ; i++){
			Message m = returnMessages.get(i);
			if(m.getDate() < cal.get(Calendar.DATE))
				if(m.getMonth() <= cal.get(Calendar.MONTH)+1){
					messageDao.deleteMessage(m.getKey());
					returnMessages.remove(i);
				}
		}
		
		return returnMessages;
	}
	
	@Override
	public Message askMeal(Message m) {
		// TODO Auto-generated method stub
		//List<Message> list = messageDao.getMessages();
		Message returnMessage = new Message();
		returnMessage.setMessage(GlobalVariables.FAILED);
		
		// user table에서 누구로 부터 온 것인지 fromId를 통해서 찾는다.
		User fromUser = userDao.getUser(m.getFromId());
		if(fromUser == null) return returnMessage;
		m.setFrom(fromUser.getName());
		
		// toId를 통해 해당 날짜에 실제로 약속이 비어있는지 확인한다. 만약 있으면 return FAIL;
		Schedule askToSchedule = scheduleDao.getSchedule(m.getToId());
		Vector<Day> askToDays = askToSchedule.getSchedule();
		Day askToDay = askToDays.get(m.getDow());
		Partner askToPartner;
		if(GlobalVariables.BREAKFAST.equals(m.getMeal())){
			askToPartner = askToDay.getBreakfast();
		} else if(GlobalVariables.LUNCH.equals(m.getMeal())){
			askToPartner = askToDay.getLunch();
		} else{
			askToPartner = askToDay.getDinner();
		}
		if(!"".equals(askToPartner.getPartnerName())) return returnMessage;
		
		// 없다면은 messageDao를 통해 해당 메세지를 저장한다.
		messageDao.setMessage(m);
		
		// fromId의 스케줄에 가서 해당 날짜를 신청대기 상태로 바꾼다.
		Schedule fromSchedule = scheduleDao.getSchedule(m.getFromId());
		Day fromDay = fromSchedule.getSchedule().get(m.getDow());
		Partner fromPartner;
		if(GlobalVariables.BREAKFAST.equals(m.getMeal())){
			fromPartner = fromDay.getBreakfast();
		} else if(GlobalVariables.LUNCH.equals(m.getMeal())){
			fromPartner = fromDay.getLunch();
		} else{
			fromPartner = fromDay.getDinner();
		}
		fromPartner.setPartnerName("신청대기");
		fromPartner.setPartnerKey(m.getTo());
		fromPartner.setDow(m.getDow());
		fromPartner.setFixed(m.getConfirm());
		fromPartner.setMeal(m.getMeal());
		fromPartner.setPartnerPhone(m.getToId());
		fromPartner.setMonth(m.getMonth());
		fromPartner.setDate(m.getDate());
		scheduleDao.setSchedule(m.getFromId(), fromSchedule);
		
		// 마지막으로 Google push server 관련 procedure 수행
		Sender sender = new Sender(GlobalVariables.SENDER_ID);
		com.google.android.gcm.server.Message.Builder builder = new com.google.android.gcm.server.Message.Builder();
		User toUser = userDao.getUser(m.getToId());
		String registerId = toUser.getRegisterId();
		builder.collapseKey(registerId);
		builder.addData("notice", fromUser.getName()+"님으로부터 메세지가 왔습니다.");
		com.google.android.gcm.server.Message googleMessage = builder.build();
		try {
			Result result = sender.send(googleMessage, registerId, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnMessage.setMessage(GlobalVariables.FAILED);
			returnMessage.setMeal(e.getMessage());
			return returnMessage;
		}
		
		
		// return SUCCESS;
		returnMessage.setMessage(GlobalVariables.SUCCEED);
		return returnMessage;
	}

	@Override
	public Message refuseMeal(Message m) {
		// TODO Auto-generated method stub
		
		Message ret = new Message();
		ret.setMessage(GlobalVariables.FAILED);
		// 해당 스케줄이 비었는지 아닌지 확인, 비었으면 그냥 리턴
		Schedule fromSchedule = scheduleDao.getSchedule(m.getFromId());
		Schedule toSchedule = scheduleDao.getSchedule(m.getToId());
		if(!("신청대기".equals(fromSchedule.getSchedule().get(m.getDow()).getMeal(m.getMeal()).getPartnerName()) &&
				"".equals(toSchedule.getSchedule().get(m.getDow()).getMeal(m.getMeal()).getPartnerName()))){
			//ret.setMessage(fromSchedule.getSchedule().get(m.getDow()).getMeal(m.getMeal()).getPartnerName()+"/"+toSchedule.getSchedule().get(m.getDow()).getMeal(m.getMeal()).getPartnerName());
			fromSchedule.getSchedule().get(m.getDow()).setMeal(m.getMeal(),new Partner());
			return ret;
		}
		
		// 중복이 되어있지 않으면 상대방의 스케줄을 조정한다.
		fromSchedule.getSchedule().get(m.getDow()).setMeal(m.getMeal(),new Partner());
		toSchedule.getSchedule().get(m.getDow()).setMeal(m.getMeal(),new Partner());
		scheduleDao.setSchedule(fromSchedule.getKey(), fromSchedule);
		scheduleDao.setSchedule(toSchedule.getKey(), toSchedule);
		// 상대방에게 약속 취소가 되었다는 메세지를 저장한다.
		Message newMessage = new Message();
		newMessage.setConfirm(m.getConfirm());
		newMessage.setDate(m.getDate());
		newMessage.setDow(m.getDow());
		newMessage.setFrom(m.getTo());
		newMessage.setFromId(m.getToId());
		newMessage.setFromPhone(m.getToId());
		newMessage.setMeal(m.getMeal());
		newMessage.setMessage(m.getMessage());
		newMessage.setMonth(m.getMonth());
		newMessage.setState(GlobalVariables.REFUSE);
		newMessage.setTo(m.getFrom());
		newMessage.setToId(m.getFromId());
		messageDao.setMessage(newMessage);
		
		// 기존 메시지를 삭제한다.
		messageDao.deleteMessage(m.getKey());
		
		// 마지막으로 Google push server 관련 procedure 수행
		Sender sender = new Sender(GlobalVariables.SENDER_ID);
		com.google.android.gcm.server.Message.Builder builder = new com.google.android.gcm.server.Message.Builder();
		User fromUser = userDao.getUser(m.getFromId());
		String registerId = fromUser.getRegisterId();
		builder.collapseKey(registerId);
		builder.addData("notice", userDao.getUser(m.getToId()).getName()+"님으로부터 메세지가 왔습니다.");
		com.google.android.gcm.server.Message googleMessage = builder.build();
		try {
			Result result = sender.send(googleMessage, registerId, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret.setMessage(GlobalVariables.FAILED);
			ret.setMeal(e.getMessage());
			return ret;
		}
		
		
		ret.setMessage(GlobalVariables.SUCCEED);
		
		return ret;
	}

	@Override
	public Message acceptMeal(Message m) {
		// TODO Auto-generated method stub
		
		Message ret = new Message();
		ret.setMessage(GlobalVariables.DUPLICATED);
		// 스케줄이 중복이 되었는지 확인한다. 중복이 되었으면 DUPLICATED
		Schedule fromSchedule = scheduleDao.getSchedule(m.getFromId());
		Schedule toSchedule = scheduleDao.getSchedule(m.getToId());
		if(!("신청대기".equals(fromSchedule.getSchedule().get(m.getDow()).getMeal(m.getMeal()).getPartnerName()) &&
				"".equals(toSchedule.getSchedule().get(m.getDow()).getMeal(m.getMeal()).getPartnerName()))){
			//ret.setMessage(fromSchedule.getSchedule().get(m.getDow()).getMeal(m.getMeal()).getPartnerName()+"/"+toSchedule.getSchedule().get(m.getDow()).getMeal(m.getMeal()).getPartnerName());
			return ret;
		}
			//return ret;
		// 중복이 되어있지 않으면 나의 스케줄을 조정하고 상대방의 스케줄을 조정한다.
		User fromUser = userDao.getUser(m.getFromId());
		User toUser = userDao.getUser(m.getToId());
		ret.setMessage(GlobalVariables.FAILED);
		if(fromUser == null || toUser == null) return ret;
		
		Partner fromPartner = fromSchedule.getSchedule().get(m.getDow()).getMeal(m.getMeal());
		Partner toPartner = toSchedule.getSchedule().get(m.getDow()).getMeal(m.getMeal());
		fromPartner.setDow(m.getDow());
		fromPartner.setFixed(m.getConfirm());
		fromPartner.setMeal(m.getMeal());
		fromPartner.setPartnerKey(toUser.getPhone());
		fromPartner.setPartnerName(toUser.getName());
		fromPartner.setPartnerPhone(toUser.getPhone());
		fromPartner.setMonth(m.getMonth());
		fromPartner.setDate(m.getDate());
		scheduleDao.setSchedule(fromSchedule.getKey(), fromSchedule);
		
		toPartner.setDow(m.getDow());
		toPartner.setFixed(m.getConfirm());
		toPartner.setMeal(m.getMeal());
		toPartner.setPartnerKey(fromUser.getPhone());
		toPartner.setPartnerName(fromUser.getName());
		toPartner.setPartnerPhone(fromUser.getPhone());
		toPartner.setMonth(m.getMonth());
		toPartner.setDate(m.getDate());
		scheduleDao.setSchedule(toSchedule.getKey(), toSchedule);
		// 상대방에게 약속 성사가 되었다는 메세지를 저장한다.
		Message newMessage = new Message();
		newMessage.setConfirm(m.getConfirm());
		newMessage.setDate(m.getDate());
		newMessage.setDow(m.getDow());
		newMessage.setFrom(m.getTo());
		newMessage.setFromId(m.getToId());
		newMessage.setFromPhone(m.getToId());
		newMessage.setMeal(m.getMeal());
		newMessage.setMessage(m.getMessage());
		newMessage.setMonth(m.getMonth());
		newMessage.setState(GlobalVariables.ACCEPT);
		newMessage.setTo(m.getFrom());
		newMessage.setToId(m.getFromId());
		messageDao.setMessage(newMessage);
		
		// 기존 메시지를 삭제한다.
		messageDao.deleteMessage(m.getKey());
		
		// 마지막으로 Google push server 관련 procedure 수행
		Sender sender = new Sender(GlobalVariables.SENDER_ID);
		com.google.android.gcm.server.Message.Builder builder = new com.google.android.gcm.server.Message.Builder();
		String registerId = fromUser.getRegisterId();
		builder.collapseKey(registerId);
		builder.addData("notice", userDao.getUser(m.getToId()).getName()+"님으로부터 메세지가 왔습니다.");
		com.google.android.gcm.server.Message googleMessage = builder.build();
		try {
			Result result = sender.send(googleMessage, registerId, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret.setMessage(GlobalVariables.FAILED);
			ret.setMeal(e.getMessage());
			return ret;
		}
		
		ret.setMessage(GlobalVariables.SUCCEED);
		
		return ret;
	}

	@Override
	public Message sendMessage(Message m) {
		// TODO Auto-generated method stub
		String message = m.getMessage();
		Message ret = new Message();
		ret.setMessage(GlobalVariables.SUCCEED);
		if(message.equals(GlobalVariables.LOGOUT)){
			scheduleDao.deleteSchedule(m.getKey());
			userDao.deleteUser(m.getKey());
			ret.setMessage(GlobalVariables.SUCCEED);
		} else if(message.equals(GlobalVariables.CLEAR_SCHEDULE)){
			scheduleDao.deleteSchedule(m.getKey());
			ret.setMessage(GlobalVariables.SUCCEED);
		} else if(message.equals(""+GlobalVariables.CONFIRM)){
			messageDao.deleteMessage(m.getKey());
		} else if(message.equals(GlobalVariables.REGISTER_ID)){
			User u = userDao.getUser(m.getKey());
			u.setRegisterId(m.getMeal());
			userDao.setUser(m.getKey(), u);
		}
		
		return ret;
	}
	
	public void setMessageDao(MessageDao messageDao){
		this.messageDao = messageDao;
	}
	public void setScheduleDao(ScheduleDao scheduleDao){
		this.scheduleDao = scheduleDao;
	}
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
}
