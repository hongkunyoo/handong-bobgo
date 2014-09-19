package edu.handong.babgo.common;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import edu.handong.babgo.util.HttpRequestHelper;
import edu.handong.babgo.util.RequestHelper;

public class GlobalVariables {
	//public static final String SERVER_ADDR = "http://localhost:8888";
	public static final String SERVER_ADDR = "http://hgyoo89.appspot.com";
	
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
	public static final String NETWORK_FAIL = "network_fail";
	public static final String EMPTY = "empty";
	public static final String DUPLICATED = "duplicated";
	public static final String LOGOUT = "logout";
	public static final String CLEAR_SCHEDULE = "clearSchedule";
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
	
	public static final String SENDER_ID = "683861881743";
	
	private static GlobalVariables instance = new GlobalVariables();
	//private static RequestHelper requestInstance = new TestRequestHelper();
	private static RequestHelper requestInstance = new HttpRequestHelper();
	
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
	
	public static RequestHelper getRequestHelper(){
		return requestInstance;
	}
	
	public static boolean isConnected(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = ni.isConnected();
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = ni.isConnected();

		if(isWifiConn==false && isMobileConn==false) return false;
		return true;
	}
}
