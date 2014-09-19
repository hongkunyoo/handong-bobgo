package edu.handong.babgo.dao;

import java.util.List;

import edu.handong.babgo.model.Message;

public interface MessageDao {
	@Deprecated
	public Message getMessage(String key);
	public void setMessage(Message message);
	public void deleteMessage(String key);
	public List<Message> getMessages();
}
