package edu.handong.babgo.database;

import com.google.appengine.api.datastore.Entity;

public interface EntityBuilder{
	public void setKey(String key);
	public Entity build();
}
