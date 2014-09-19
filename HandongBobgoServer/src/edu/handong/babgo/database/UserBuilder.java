package edu.handong.babgo.database;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import edu.handong.babgo.model.User;

public class UserBuilder extends User implements EntityBuilder {

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		this.setId(key);
	}

	@Override
	public Entity build() {
		// TODO Auto-generated method stub
		
		if(this.getId() == null) return null;
		
		Key k = KeyFactory.createKey("User", this.getId());
		Entity ent = new Entity(k);
		ent.setProperty("id", this.getId());
		ent.setProperty("name", this.getName());
		ent.setProperty("hisnetId", this.getHisnetId());
		ent.setProperty("phone", this.getPhone());
		ent.setProperty("gender", this.getGender());
		ent.setProperty("major", this.getMajor());
		ent.setProperty("random", this.getRandom());
		ent.setProperty("registerId", this.getRegisterId());
		
		return ent;
	}
}
