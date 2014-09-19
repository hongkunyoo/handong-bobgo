package edu.handong.babgo.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Entity;

import edu.handong.babgo.database.DataStoreTemplate;
import edu.handong.babgo.database.EntityBuilder;
import edu.handong.babgo.database.MeetingUnitBuilder;
import edu.handong.babgo.model.Partner;
import edu.handong.babgo.util.Matcher;

public class PartnerDaoImpl implements PartnerDao {
	@Autowired DataStoreTemplate template;
	
	@Override
	public Partner getPartner(String key, int dow, String meal) {
		// TODO Auto-generated method stub
		Partner p = template.get(key+dow+meal,"MeetingUnit", new Matcher<Entity, Partner>(){

			@Override
			public Partner match(Entity ent) {
				// TODO Auto-generated method stub
				Partner p = new Partner();
				p.setPartnerKey((String)ent.getProperty("partnerKey"));
				p.setPartnerName((String)ent.getProperty("partnerName"));
				p.setPartnerPhone((String)ent.getProperty("partnerPhone"));
				p.setFixed(((Long)ent.getProperty("fixed")).intValue());
				p.setState(((Long)ent.getProperty("state")).intValue());
				p.setMeal((String)ent.getProperty("meal"));
				p.setMonth(((Long)ent.getProperty("month")).intValue());
				p.setDate(((Long)ent.getProperty("date")).intValue());
				p.setDow(((Long)ent.getProperty("dow")).intValue());
				return p;
			}
			
		});
		if(p == null) return new Partner();
		return p;
	}

	@Override
	public void setPartner(String key, int dow, String meal, Partner partner) {
		// TODO Auto-generated method stub
		
		template.put(key+dow+meal, partner, new Matcher<Partner, EntityBuilder>(){
			
			@Override
			public MeetingUnitBuilder match(Partner obj) {
				// TODO Auto-generated method stub
				MeetingUnitBuilder mb = new MeetingUnitBuilder();
				
				mb.setPartnerKey(obj.getPartnerKey());
				mb.setPartnerName(obj.getPartnerName());
				mb.setPartnerPhone(obj.getPartnerPhone());
				mb.setFixed(obj.getFixed());
				mb.setState(obj.getState());
				mb.setDow(obj.getDow());
				mb.setMeal(obj.getMeal());
				mb.setDate(obj.getDate());
				mb.setMonth(obj.getMonth());
				
				return mb;
			}
			
		});
	}
	
	@Override
	public void deletePartner(String key, int dow, String meal) {
		// TODO Auto-generated method stub
		this.template.delete(key+dow+meal,"MeetingUnit");
	}
	
	public void setTemplate(DataStoreTemplate template){
		this.template = template;
	}
}
