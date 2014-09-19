package edu.handong.babgo.database;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import edu.handong.babgo.util.Matcher;

public class DataStoreTemplate {
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	Entity ent;
	
	public <T> void put(String key, T obj, Matcher<T, EntityBuilder> matcher){
		EntityBuilder eb = matcher.match(obj);
		eb.setKey(key);
		ds.put(eb.build());
	}
	
	public <T> T get(String key, String kind, Matcher<Entity, T> dematcher){
		
		Key k = KeyFactory.createKey(kind, key);
		
		try {
			ent = ds.get(k);
			return dematcher.match(ent);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void delete(String key, String kind){
		
		Key k = KeyFactory.createKey(kind, key);
		ds.delete(k);
	}
	public void delete(long id, String kind){
		Key k = KeyFactory.createKey(kind, id);
		ds.delete(k);
	}
	public <T> List<T> getList(String kind, Matcher<PreparedQuery, List<T>> matcher){
		Query q = new Query(kind);
		PreparedQuery pq = ds.prepare(q);
		return matcher.match(pq);
	}
}
