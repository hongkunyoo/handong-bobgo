package com.example.bobgo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.util.PreferenceHelper;

public class SetScheduleActivity extends Activity {
	ListView listView;
	Button btnSave;
	Button btnInit;
	SimpleAdapter adapter;
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	String[] strArr;
	ProgressDialog progressDialog;
	AlertDialog.Builder ab;
	Schedule s;
	String [] week = new String[]{"일","월","화","수","목","금","토"};
	PreferenceHelper prefHelper;
	HashMap<String,String> map;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_setting_schedule);
		
		//////////////////////////////////////////////////////////
		GlobalVariables.getInstance().setData("context", this.getApplicationContext());
		//////////////////////////////////////////////////////////
		
		prefHelper = new PreferenceHelper(SetScheduleActivity.this);
		strArr = getResources().getStringArray(R.array.week_list);
		list = new ArrayList<HashMap<String,String>>();
		
		listView = (ListView)findViewById(R.id.listViewSchedule);
		btnSave = (Button)findViewById(R.id.btn_save);
		btnInit = (Button)findViewById(R.id.btn_init);
		
		
		adapter = new SimpleAdapter(this.getApplicationContext(),list,R.layout.list_item,
				new String[]{"dow","b","l","d"}, new int[]{R.id.dayofweek,R.id.breakfast,R.id.lunch,R.id.dinner});
		listView.setAdapter(adapter);
		
		new GetScheduleAsyncTask().execute("");
		listView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SetScheduleActivity.this,SetMealActivity.class);
				Schedule s = (Schedule)GlobalVariables.getInstance().getData("schedule");
				Vector<Day> days = s.getSchedule();
				i.putExtra("position", arg2);
				i.putExtra("breakfast", days.get(arg2).getBreakfast().getPartnerName());
				i.putExtra("lunch", days.get(arg2).getLunch().getPartnerName());
				i.putExtra("dinner", days.get(arg2).getDinner().getPartnerName());
				startActivityForResult(i, GlobalVariables.FROM_SET_SCHEDULE);
			}
			
		});
		
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(prefHelper.getHisnetId().equals(""))
					showDialog(3);
				else
					new SetScheduleAsyncTask().execute("");
			}
		});
	
		
		
		
		btnInit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(prefHelper.getHisnetId().equals(""))
					showDialog(3);
				else{
					new ClearScheduleAsyncTask().execute("");
				}
			}
		});
		
	}
	/*
	@Override    
	 public void onBackPressed() {   
		super.onBackPressed();
	    	 if(prefHelper.getHisnetId().equals(""))
					showDialog(3);
				else
					new SetScheduleAsyncTask().execute("");
	    	 finish();
	 }*/
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == Activity.RESULT_OK){
			
			int position = data.getIntExtra("position", -1);
			if(position == -1) return;
			Day d = (Day)GlobalVariables.getInstance().getData("day"+position);
			s.getSchedule().set(position, d);
			map = new HashMap<String, String>();
			map.put("dow", week[position]);
			map.put("b", d.getBreakfast().getPartnerName());
			map.put("l", d.getLunch().getPartnerName());
			map.put("d", d.getDinner().getPartnerName());
			list.set(position, map);
			//list.set(position, week[position]+":"+d.getBreakfast().getPartnerName()
					//+"/"+d.getLunch().getPartnerName()+"/"+d.getDinner().getPartnerName());
			adapter.notifyDataSetChanged();
		}
		
	}
	
	
	private class GetScheduleAsyncTask extends AsyncTask<String,String,String>{
		@Override
		protected void onPreExecute(){
			showDialog(0);
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			list.clear();
			String hisnetId = prefHelper.getHisnetId();
			String phone = prefHelper.getPhone();
			if(hisnetId.equals("")) return GlobalVariables.FAILED;
			s = (Schedule)GlobalVariables.getRequestHelper().getScheduleBabo(phone);
			GlobalVariables.getInstance().setData("schedule", s);
			Vector<Day> days = s.getSchedule();
			for(int i = 0 ; i < days.size() ; i++){
				Day d = days.get(i);
				map = new HashMap<String, String>();
				map.put("dow", week[i]);
				map.put("b", d.getBreakfast().getPartnerName());
				map.put("l", d.getLunch().getPartnerName());
				map.put("d", d.getDinner().getPartnerName());
				list.add(map);
			}
			return GlobalVariables.SUCCEED;
		}
		
		@Override
		protected void onPostExecute(String arg){
			removeDialog(0);
			adapter.notifyDataSetChanged();
		}
	}
	
	
	
	private class SetScheduleAsyncTask extends AsyncTask<String,String,String>{
		@Override
		protected void onPreExecute(){
			showDialog(0);
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			PreferenceHelper prefHelper = new PreferenceHelper(SetScheduleActivity.this);
			if(prefHelper.getHisnetId().equals("")) return GlobalVariables.FAILED;
			Message m = (Message)GlobalVariables.getRequestHelper().setSchedule(prefHelper.getPhone(),s);
			
			return m.getMessage();
		}
		
		@Override
		protected void onPostExecute(String arg){
			removeDialog(0);
			if(GlobalVariables.SUCCEED.equals(arg)){
				new GetScheduleAsyncTask().execute("");
			} else{
				showDialog(2);
			}
			
			//adapter.notifyDataSetChanged();
		}
	}
	
	private class ClearScheduleAsyncTask extends AsyncTask<String,String,String>{
		@Override
		protected void onPreExecute(){
			showDialog(0);
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			PreferenceHelper prefHelper = new PreferenceHelper(SetScheduleActivity.this);
			if(prefHelper.getHisnetId().equals("")) return GlobalVariables.FAILED;
			Message sendMessage = new Message();
			sendMessage.setMessage(GlobalVariables.CLEAR_SCHEDULE);
			sendMessage.setKey(prefHelper.getPhone());
			Message m = (Message)GlobalVariables.getRequestHelper().sendMessage(prefHelper.getPhone(),sendMessage);
			
			return m.getMessage();
		}
		
		@Override
		protected void onPostExecute(String arg){
			removeDialog(0);
			if(GlobalVariables.SUCCEED.equals(arg)){
				new GetScheduleAsyncTask().execute("");
			} else{
				showDialog(2);
			}
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
			ab.setTitle("설정 실패");
			ab.setMessage("스케줄 설정에 실패하였습니다");
			ab.setPositiveButton("확인", new android.content.DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//dialog.dismiss();
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
				}
			});
			return ab.create();
		default:
			return null;
		}
	}
}
