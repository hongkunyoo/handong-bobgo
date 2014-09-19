package edu.handong.babgo.model;

public class User {
	private String id = "";
	private String name = "";
	private String hisnetId = ""; 
	private String phone = "";
	private int gender = 0;
	private String major = "";
	private int random = 0;
	private String registerId = "";
	
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHisnetId() {
		return hisnetId;
	}
	public void setHisnetId(String hisnetId) {
		this.hisnetId = hisnetId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public int getRandom() {
		return random;
	}
	public void setRandom(int random) {
		this.random = random;
	}
	public String getRegisterId() {
		return registerId;
	}
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
}
