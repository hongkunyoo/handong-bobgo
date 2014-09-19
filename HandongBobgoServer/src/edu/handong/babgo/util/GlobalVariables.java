package edu.handong.babgo.util;

import java.util.HashMap;
import java.util.Map;

public class GlobalVariables {
	public static final String SERVER_ADDR = "http://localhost:8888";
	//public static final String SERVER_ADDR = "http://hgyoo89.appspot.com";
	
	public static final String ID = "id";
	public static final String HISNET_ID = "hisnetId";
	public static final String SETTINGS = "settings";
	public static final String PASSWORD = "passwork";
	public static final String PHONE = "phone";
	public static final String SCHEDULE = "schedule";
	public static final String REGISTER_ID = "registerId";
	public static final String IS_VALID = "isValid";
	
	public static final String RESULT = "result";
	public static final String SUCCEED = "succeed";
	public static final String FAILED = "failed";
	public static final String DUPLICATED = "duplicated";
	public static final String LOGOUT = "logout";
	public static final String CLEAR_SCHEDULE = "clearSchedule";
	public static final String EMPTY = "empty";
	public static final String YES = "yes";
	public static final String NO = "no";
	public static final int FROM_SET_SCHEDULE = 0;
	public static final int MAN = 1;
	public static final int WOMAN = 0;
	
	public static final String BREAKFAST = "breakfast";
	public static final String LUNCH = "lunch";
	public static final String DINNER = "dinner";
	
	public static final int ASK = 100;
	public static final int REFUSE = 200;
	public static final int ACCEPT = 300;
	public static final int CONFIRM = 400;
	
	public static final String SENDER_ID = "AIzaSyCMseM4PpHd3z_uNa5GEpC4L1zUIMhbd18";
	
	private static GlobalVariables instance = new GlobalVariables();
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	private GlobalVariables(){}
	public static GlobalVariables getInstance(){
		return instance;
	}
	
	public void setData(String name, Object obj){
		map.put(name, obj);
	}
	public Object getData(String name){
		return map.get(name);
	}
}
