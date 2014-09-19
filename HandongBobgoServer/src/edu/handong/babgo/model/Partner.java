package edu.handong.babgo.model;


public class Partner {
	private String key = "";
	private int dow = 0;
	private String partnerKey = "";
	private String partnerName = "";
	private String partnerPhone = "";
	private int fixed = 0;
	private int state = 0;
	private String meal = "";
	private int month = 0;
	private int date = 0;
	
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
	public int getDow(){
		return dow;
	}
	public void setDow(int dow){
		this.dow = dow;
	}
	public String getPartnerKey() {
		return partnerKey;
	}
	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPartnerPhone() {
		return partnerPhone;
	}
	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}
	public int getFixed() {
		return fixed;
	}
	public void setFixed(int fixed) {
		this.fixed = fixed;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMeal(){
		return meal;
	}
	public void setMeal(String meal){
		this.meal = meal;
	}
}
