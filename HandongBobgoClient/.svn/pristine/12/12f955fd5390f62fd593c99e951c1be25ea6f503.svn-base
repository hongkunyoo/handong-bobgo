package com.example.bobgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.model.User;
import edu.handong.babgo.util.PreferenceHelper;
import edu.handong.babgo.util.RequestHelper;

public class FriendScheduleView extends Activity {
	ProgressDialog progressDialog;
	AlertDialog.Builder ab;
	ListView listView;
	User user;
	Schedule userSchedule;
	Schedule mySchedule;
	int position;
	ArrayList<HashMap<String,String>> schedule = new ArrayList<HashMap<String,String>>();
	SimpleAdapter adapter;
	String[] week = new String[]{"일","월","화","수","목","금","토"};
	GetScheduleAsyncTask async;
	RequestHelper helper = GlobalVariables.getRequestHelper();
	PreferenceHelper prefHelper;
	Context context;
	HashMap<String,String> map;
	String name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_friend_schedule_view);
        prefHelper = new PreferenceHelper(this);
        context = this.getApplicationContext();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        TextView title = (TextView)findViewById(R.id.scheduleName);
        
        ArrayList<User> userList = (ArrayList<User>)GlobalVariables.getInstance().getData("dataList");
        for(int i = 0 ; i < userList.size(); i++){
        	if(name.equals(userList.get(i).getName()))
        		user = userList.get(i);
        }
        title.setText(user.getName()+"("+user.getPhone()+")");
        
        listView = (ListView)findViewById(R.id.listView1);
        adapter = new SimpleAdapter(this.getApplicationContext(), schedule, R.layout.list_item,
        		new String[]{"dow","b","l","d"}, new int[]{R.id.dayofweek, R.id.breakfast, R.id.lunch, R.id.dinner});
        listView.setAdapter(adapter);
        async = new GetScheduleAsyncTask();
        //async.execute("");
        
        listView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(!schedule.get(arg2).containsValue("신청가능")){
					;
				}else{
					Intent i = new Intent(FriendScheduleView.this,AskMealActivity.class);
					GlobalVariables.getInstance().setData("schedule", userSchedule);
					GlobalVariables.getInstance().setData("mySchedule", mySchedule);
					i.putExtra("position",arg2);
					i.putExtra("name",name);
					startActivity(i);
				}
			}
			
		});
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	schedule.clear();
    	new GetScheduleAsyncTask().execute("");
    }
    
    private class GetScheduleAsyncTask extends AsyncTask<String,String,String>{
    	@Override
    	protected void onPreExecute(){
    		showDialog(0);
    	}
    	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			userSchedule = helper.getScheduleBabo(user.getPhone());
			GlobalVariables.getInstance().setData("context",context);
			if("".equals(prefHelper.getPhone())) return GlobalVariables.FAILED;
			mySchedule = helper.getScheduleBabo(prefHelper.getPhone());
			if(userSchedule == null) return GlobalVariables.FAILED;
			if(GlobalVariables.FAILED.equals(userSchedule.getKey())) return GlobalVariables.FAILED;
			Vector<Day> days = userSchedule.getSchedule();
			Vector<Day> myDays = mySchedule.getSchedule();
			for(int i = 0 ; i < 7 ; i++){
				Day d = days.get(i);
				Day myD = myDays.get(i);
				map = new HashMap<String,String>();
				map.put("dow", week[i]);
				if(d.getBreakfast().getPartnerName().equals("") && myD.getBreakfast().getPartnerName().equals(""))
					map.put("b", "신청가능");
				else{
					map.put("b", "");}
				
				if("신청대기".equals(d.getBreakfast().getPartnerName()))
					map.put("b", "");
				if(d.getLunch().getPartnerName().equals("") && myD.getLunch().getPartnerName().equals(""))
					map.put("l", "신청가능");
				else
					map.put("l", "");
				if("신청대기".equals(d.getLunch().getPartnerName())) 
					map.put("l","");
				
				if(d.getDinner().getPartnerName().equals("") && myD.getDinner().getPartnerName().equals(""))
					map.put("d", "신청가능");
				else
					map.put("d", "");
				if("신청대기".equals(d.getDinner().getPartnerName()))
					map.put("d", "");
				schedule.add(map);
				//schedule.add(week[i]+":"+strBreakfast+"/"+strLunch+"/"+strDinner);
			}
			return GlobalVariables.SUCCEED;
		}
    	
    	@Override
    	protected void onPostExecute(String arg){
    		removeDialog(0);
    		if(GlobalVariables.SUCCEED.equals(arg)){
	    		adapter.notifyDataSetChanged();
    		}else
    			showDialog(2);
    	}
    }
    
    @Override
	public Dialog onCreateDialog(int id) {
		switch(id){
		case 0:
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("please wait...");
			progressDialog.setIndeterminate(true);

			return progressDialog;
		case 1:
			ab = new AlertDialog.Builder(this);
			ab.setTitle("계정 설정");
			ab.setMessage("설정 완료");
			ab.setPositiveButton("확인", new android.content.DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//dialog.dismiss();
				}
			});
			return ab.create();
		case 2:
			ab = new AlertDialog.Builder(this);
			ab.setTitle("검색 실패");
			ab.setMessage("해당 사용자의 정보가 없습니다.");
			ab.setPositiveButton("확인", new android.content.DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//dialog.dismiss();
					Log.e("ERROR","here");
				}
			});
			return ab.create();
		case 3:
			ab = new AlertDialog.Builder(this);
			ab.setTitle("계정 설정");
			ab.setMessage("계정 설정을 해주시기 바랍니다.");
			ab.setPositiveButton("확인", new android.content.DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//dialog.dismiss();
					Log.e("ERROR","here");
				}
			});
			return ab.create();
		default:
			return null;
		}
	}
}
