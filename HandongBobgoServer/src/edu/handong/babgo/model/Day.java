package edu.handong.babgo.model;

import edu.handong.babgo.util.GlobalVariables;



public class Day {
	private int month = 0;
	private int date = 0;
	private int dow = 0;
	private Partner breakfast = new Partner();
	private Partner lunch = new Partner();
	private Partner dinner = new Partner();
	
	public int getMonth(){
		return month;
	}
	public void setMonth(int month){
		this.month = month;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public int getDow(){
		return dow;
	}
	public void setDow(int dow){
		this.dow = dow;
	}
	public Partner getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(Partner breakfast) {
		this.breakfast = breakfast;
	}
	public Partner getLunch() {
		return lunch;
	}
	public void setLunch(Partner lunch) {
		this.lunch = lunch;
	}
	public Partner getDinner() {
		return dinner;
	}
	public void setDinner(Partner dinner) {
		this.dinner = dinner;
	}
	public Partner getMeal(String meal){
		if(meal.equals(GlobalVariables.BREAKFAST)) return this.getBreakfast();
		else if(meal.equals(GlobalVariables.LUNCH)) return this.getLunch();
		else return this.getDinner();
	}
	public void setMeal(String meal,Partner p){
		if(meal.equals(GlobalVariables.BREAKFAST)) this.setBreakfast(p);
		else if(meal.equals(GlobalVariables.LUNCH)) this.setLunch(p);
		else this.setDinner(p);
	}
}
