package edu.handong.babgo.util;

import java.util.List;

import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.model.User;

public interface RequestHelper {
	public Day getTodaysBabgo(String id);
	public Schedule getScheduleBabo(String id);
	public List<User> getRandomBabgo(User user);
	public Message askMeal(Message m);
	public Message setSchedule(String id, Schedule s);
	public Message setAccount(String id, String hisnetId, String password, int random);
	public List<Message> getNewUpdates(String id);
	public Message acceptMeal(Message m);
	public Message refuseMeal(Message m);
	public Message sendMessage(String id, Message m);
}
