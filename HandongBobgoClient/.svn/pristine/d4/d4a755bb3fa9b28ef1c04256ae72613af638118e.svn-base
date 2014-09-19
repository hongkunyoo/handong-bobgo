package com.example.bobgo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.util.PreferenceHelper;
import edu.handong.babgo.util.RequestHelper;

public class GCMIntentService extends GCMBaseIntentService {
	RequestHelper helper = GlobalVariables.getRequestHelper();
	PreferenceHelper prefHelper;
	
	public GCMIntentService(){
		this(GlobalVariables.SENDER_ID);
	}

	public GCMIntentService(String senderId){
		super(senderId);
	}
	
	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		Log.e("ERROR","onError");
	}

	@Override
	protected void onMessage(Context arg0, Intent intent) {
		// TODO Auto-generated method stub
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager notiManager = (NotificationManager)getSystemService(ns);
		
		// instantiate the notification
		int icon = R.drawable.bob;
		String content = (String)intent.getExtras().get("notice");
		long howLong = System.currentTimeMillis();
		Notification notification = new Notification(icon, content, howLong);
		
		// Define the Notification's expanded message and intent
		Context context = getApplicationContext();
		String contentTitle = "Babgo Message";
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.setLatestEventInfo(context, contentTitle, content, contentIntent);
		Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibe.vibrate(600);
		notiManager.notify(1, notification);
	}

	@Override
	protected void onRegistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		prefHelper = new PreferenceHelper(arg0);
		Message m = new Message();
		m.setKey(prefHelper.getPhone());
		m.setMessage(GlobalVariables.REGISTER_ID);
		m.setMeal(arg1);
		helper.sendMessage(prefHelper.getPhone(),m);
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		Log.e("ERROR","onUnRegistered");
	}
	
	
}
