package edu.handong.babgo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;

import edu.handong.babgo.database.DataStoreTemplate;
import edu.handong.babgo.database.EntityBuilder;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.model.User;
import edu.handong.babgo.util.Matcher;

public class MessageDaoImpl implements MessageDao {
	@Autowired DataStoreTemplate template;
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
	
	@Deprecated
	@Override
	public Message getMessage(String key) {
		// TODO Auto-generated method stub
		List<Message> list = this.getMessages();
		for(int i = 0 ; i < list.size() ; i++){
			//list.get(i).getKey().equals(anObject)
		}
		return null;
	}

	@Override
	public void setMessage(Message message) {
		// TODO Auto-generated method stub
		
		Entity ent = new Entity("Message");
		
		//ent.setProperty("key", message.getKey());
		ent.setProperty("fromId", message.getFromId());
		ent.setProperty("from", message.getFrom());
		ent.setProperty("toId", message.getToId());
		ent.setProperty("to", message.getTo());
		ent.setProperty("date", message.getDate());
		ent.setProperty("month", message.getMonth());
		ent.setProperty("dow", message.getDow());
		ent.setProperty("meal", message.getMeal());
		ent.setProperty("fromPhone", message.getFromPhone());
		ent.setProperty("message", message.getMessage());
		ent.setProperty("state", message.getState());
		ent.setProperty("confirm", message.getConfirm());
		
		ds.put(ent);
		
	}
	
	
	/*
	@Override
	public Message getMessage(String key) {
		// TODO Auto-generated method stub
		return template.get(key,"Message", new Matcher<Entity, Message>(){

			@Override
			public Message match(Entity ent) {
				// TODO Auto-generated method stub
				Message m = new Message();
				m.setKey((String)ent.getProperty("key"));
				m.setFromId((String)ent.getProperty("fromId"));
				m.setFrom((String)ent.getProperty("from"));
				m.setToId((String)ent.getProperty("toId"));
				m.setTo((String)ent.getProperty("to"));
				m.setDate(((Long)ent.getProperty("date")).intValue());
				m.setMonth(((Long)ent.getProperty("month")).intValue());
				m.setDow(((Long)ent.getProperty("dow")).intValue());
				m.setMeal((String)ent.getProperty("meal"));
				m.setFromPhone((String)ent.getProperty("fromPhone"));
				m.setMessage((String)ent.getProperty("message"));
				m.setState(((Long)ent.getProperty("state")).intValue());
				m.setConfirm(((Long)ent.getProperty("confirm")).intValue());
				
				return m;
			}
			
		});
	}

	@Override
	public void setMessage(String key, Message message) {
		// TODO Auto-generated method stub
		template.put(key, message, new Matcher<Message, EntityBuilder>(){
			
			@Override
			public MessageBuilder match(Message obj) {
				// TODO Auto-generated method stub
				MessageBuilder mb = new MessageBuilder();
				
				mb.setKey(obj.getKey());
				mb.setFromId(obj.getFromId());
				mb.setFrom(obj.getFrom());
				mb.setToId(obj.getToId());
				mb.setTo(obj.getTo());
				mb.setDate(obj.getDate());
				mb.setMonth(obj.getMonth());
				mb.setDow(obj.getDow());
				mb.setMeal(obj.getMeal());
				mb.setFromPhone(obj.getFromPhone());
				mb.setMessage(obj.getMessage());
				mb.setState(obj.getState());
				mb.setConfirm(obj.getConfirm());
				
				return mb;
			}
			
		});
	}
	
	class MessageBuilder extends Message implements EntityBuilder{

		@Override
		public void setKey(String key) {
			// TODO Auto-generated method stub
			super.setKey(key);
		}

		@Override
		public Entity build() {
			// TODO Auto-generated method stub
			if(this.getKey() == null) return null;
			
			Key key = KeyFactory.createKey("Message",this.getKey());
			Entity ent = new Entity(key);
			
			ent.setProperty("key", this.getKey());
			ent.setProperty("fromId", this.getFromId());
			ent.setProperty("from", this.getFrom());
			ent.setProperty("toId", this.getToId());
			ent.setProperty("to", this.getTo());
			ent.setProperty("date", this.getDate());
			ent.setProperty("month", this.getMonth());
			ent.setProperty("dow", this.getDow());
			ent.setProperty("meal", this.getMeal());
			ent.setProperty("fromPhone", this.getFromPhone());
			ent.setProperty("message", this.getMessage());
			ent.setProperty("state", this.getState());
			ent.setProperty("confirm", this.getConfirm());
			
			return ent;
		}
	}
	
	*/
	@Override
	public void deleteMessage(String key) {
		// TODO Auto-generated method stub
		this.template.delete(Long.parseLong(key),"Message");
	}
	
	public void setTemplate(DataStoreTemplate template){
		this.template = template;
	}
	

	@Override
	public List<Message> getMessages() {
		// TODO Auto-generated method stub
		List<Message> list = template.getList("Message", new Matcher<PreparedQuery, List<Message>>(){

			@Override
			public List<Message> match(PreparedQuery pq) {
				List<Message> list = new ArrayList<Message>();
				// TODO Auto-generated method stub
				for (Entity result : pq.asIterable()) {
					String key = ""+result.getKey().getId();
					String fromId = (String)result.getProperty("fromId");
					String from = (String)result.getProperty("from");
					String toId = (String)result.getProperty("toId");
					String to = (String)result.getProperty("to");
					int date = ((Long)result.getProperty("date")).intValue();
					int month = ((Long)result.getProperty("month")).intValue();
					int dow = ((Long)result.getProperty("dow")).intValue();
					String meal = (String)result.getProperty("meal");
					String fromPhone = (String)result.getProperty("fromPhone");
					String message = (String)result.getProperty("message");
					int state = ((Long)result.getProperty("state")).intValue();
					int confirm = ((Long)result.getProperty("confirm")).intValue();
					Message m = new Message();
					m.setKey(key);
					m.setFromId(fromId);
					m.setFrom(from);
					m.setToId(toId);
					m.setTo(to);
					m.setDate(date);
					m.setMonth(month);
					m.setDow(dow);
					m.setMeal(meal);
					m.setFromPhone(fromPhone);
					m.setMessage(message);
					m.setState(state);
					m.setConfirm(confirm);
					
					list.add(m);
				}
				return list;
			}
			
		});
		
		return list;
	}
}
