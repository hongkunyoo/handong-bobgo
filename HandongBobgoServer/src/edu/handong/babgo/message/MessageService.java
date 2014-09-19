package edu.handong.babgo.message;

import java.util.List;

import edu.handong.babgo.model.Message;

public interface MessageService {
	public List<Message> getNewUpdates(String key);
	public Message askMeal(Message m);
	public Message refuseMeal(Message m);
	public Message acceptMeal(Message m);
	public Message sendMessage(Message m);
}
