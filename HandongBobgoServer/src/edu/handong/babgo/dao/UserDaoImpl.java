package edu.handong.babgo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;

import edu.handong.babgo.database.DataStoreTemplate;
import edu.handong.babgo.database.EntityBuilder;
import edu.handong.babgo.database.UserBuilder;
import edu.handong.babgo.model.User;
import edu.handong.babgo.util.Matcher;

public class UserDaoImpl implements UserDao{
	@Autowired DataStoreTemplate template;
	
	@Override
	public List<User> getUsers(){
		List<User> list = template.getList("User", new Matcher<PreparedQuery, List<User>>(){

			@Override
			public List<User> match(PreparedQuery pq) {
				List<User> list = new ArrayList<User>();
				// TODO Auto-generated method stub
				for (Entity result : pq.asIterable()) {
					String name = (String)result.getProperty("name");
					String hisnetId = (String)result.getProperty("hisnetId");
					String id = (String)result.getProperty("id");
					String phone = (String)result.getProperty("phone");
					int gender = ((Long)result.getProperty("gender")).intValue();
					String major = (String)result.getProperty("major");
					int random = ((Long)result.getProperty("random")).intValue();
					String registerId = (String)result.getProperty("registerId");
					User u = new User();
					u.setName(name);
					u.setHisnetId(hisnetId);
					u.setId(id);
					u.setPhone(phone);
					u.setGender(gender);
					u.setMajor(major);
					u.setRandom(random);
					u.setRegisterId(registerId);
					list.add(u);
				}
				return list;
			}
			
		});
		
		return list;
	}
	
	@Override
	public User getUser(String key) {
		// TODO Auto-generated method stub
		
		return template.get(key+0+"user", "User", new Matcher<Entity, User>(){

			@Override
			public User match(Entity ent) {
				// TODO Auto-generated method stub
				UserUnit unit = new UserUnit(ent);
				User user = new User();
				user.setName(unit.getName());
				user.setHisnetId(unit.getHisnetId());
				user.setPhone(unit.getPhone());
				user.setGender(unit.getGender());
				user.setMajor(unit.getMajor());
				user.setRandom(unit.getRandom());
				user.setRegisterId(unit.getRegisterId());
				
				return user;
			}
			
		});
	}

	@Override
	public void setUser(String key, User user) {
		// TODO Auto-generated method stub
		template.put(key+0+"user", user, new Matcher<User,EntityBuilder>(){

			@Override
			public UserBuilder match(User obj) {
				// TODO Auto-generated method stub
				UserBuilder ub = new UserBuilder();
				ub.setId(obj.getId());
				ub.setHisnetId(obj.getHisnetId());
				ub.setMajor(obj.getMajor());
				ub.setName(obj.getName());
				ub.setPhone(obj.getPhone());
				ub.setGender(obj.getGender());
				ub.setRandom(obj.getRandom());
				ub.setRegisterId(obj.getRegisterId());
				
				return ub;
			}
			
		});
	}

	@Override
	public void deleteUser(String key) {
		// TODO Auto-generated method stub
		template.delete(key+0+"user", "User");
	}
	
	class UserUnit{
		Entity ent;
		
		public UserUnit(String key){
			Key k = KeyFactory.createKey("UserUnit", key);
			this.ent = new Entity(k);
		}
		public UserUnit(Entity ent){
			this.ent = ent;
		}
		public String getName() {
			return (String)ent.getProperty("name");
		}
		public void setName(String name) {
			ent.setProperty("name", name);
		}
		public String getHisnetId() {
			return (String)ent.getProperty("hisnetId");
		}
		public void setHisnetId(String hisnetId) {
			ent.setProperty("hisnetId", hisnetId);
		}
		public String getPhone() {
			return (String)ent.getProperty("phone");
		}
		public void setPhone(String phone) {
			ent.setProperty("phone", phone);
		}
		public int getGender() {
			return ((Long)ent.getProperty("gender")).intValue();
		}
		public void setGender(int gender) {
			ent.setProperty("gender",gender);
		}
		public String getMajor() {
			return (String)ent.getProperty("major");
		}
		public void setMajor(String major) {
			ent.setProperty("major", major);
		}
		public int getRandom() {
			return ((Long)(ent.getProperty("random"))).intValue();
		}
		public void setRandom(int random) {
			ent.setProperty("random",random);
		}
		public String getRegisterId() {
			return (String)ent.getProperty("registerId");
		}
		public void setRegisterId(String registerId) {
			ent.setProperty("registerId", registerId);
		}
		public Entity getEntity(){
			return ent;
		}
	}
	public void setTemplate(DataStoreTemplate template){
		this.template = template;
	}
}
