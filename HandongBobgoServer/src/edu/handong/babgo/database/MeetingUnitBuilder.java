package edu.handong.babgo.database;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class MeetingUnitBuilder extends MeetingUnit implements EntityBuilder{

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		super.setKey(key);
	}

	@Override
	public Entity build() {
		// TODO Auto-generated method stub
		if(this.getKey() == null) return null;
		
		Key k = KeyFactory.createKey("MeetingUnit", this.getKey());
		Entity ent = new Entity(k);
		
		ent.setProperty("key",this.getKey());
		ent.setProperty("date",this.getDate());
		ent.setProperty("month",this.getMonth());
		ent.setProperty("dow",this.getDow());
		ent.setProperty("meal",this.getMeal());
		ent.setProperty("partnerName",this.getPartnerName());
		ent.setProperty("partnerPhone",this.getPartnerPhone());
		ent.setProperty("partnerKey",this.getPartnerKey());
		ent.setProperty("fixed",this.getFixed());
		ent.setProperty("state",this.getState());
		
		return ent;
	}
}
