package edu.handong.babgo.dao;

import edu.handong.babgo.model.Partner;

public interface PartnerDao {
	public Partner getPartner(String key, int dow, String meal);
	public void setPartner(String key, int dow, String meal, Partner partner);
	public void deletePartner(String key, int dow, String meal);
}
