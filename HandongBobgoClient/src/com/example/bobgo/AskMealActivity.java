package com.example.bobgo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.model.Schedule;
import edu.handong.babgo.util.PreferenceHelper;
import edu.handong.babgo.util.RequestHelper;

public class AskMealActivity extends Activity {
	TextView askMealDate;
	Button btnAskBreakfast;
	Button btnAskLunch;
	Button btnAskDinner;
	int position;
	Schedule schedule;
	Schedule mySchedule;
	Vector<Day> days;
	Vector<Day> myDays;
	ProgressDialog progressDialog;
	AlertDialog.Builder ab;
	AskMealAsyncTask askMealAsync;
	RequestHelper helper = GlobalVariables.getRequestHelper();
	PreferenceHelper prefHelper;
	String[] week = new String[]{"일","월","화","수","목","금","토"};
	int month;
	int date;
	int dow;
	Dialog dialog;
	TextView textViewConfirmMessage;
	EditText editTextMessage;
	Button btnConfirm;
	Button btnCancel;
	CheckBox checkBoxState;
	String name;
	String sendMessage;
	String meal;
	Spinner spn;
	List<String> list;
	ArrayAdapter<String> adapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_meal);
		
		prefHelper = new PreferenceHelper(this);
		//btnAskBreakfast = (Button)findViewById(R.id.btn_ask_breakfast);
		//btnAskLunch = (Button)findViewById(R.id.btn_ask_lunch);
		//btnAskDinner = (Button)findViewById(R.id.btn_ask_dinner);
		spn = (Spinner)findViewById(R.id.spinner_when);
		askMealDate = (TextView)findViewById(R.id.textView_askMealDate);
		askMealAsync = new AskMealAsyncTask();
		Intent i = getIntent();
		position = i.getIntExtra("position", -1);
		name = i.getStringExtra("name");
		dow = position;
		schedule = (Schedule)GlobalVariables.getInstance().getData("schedule");
		mySchedule = (Schedule)GlobalVariables.getInstance().getData("mySchedule");
		days = schedule.getSchedule();
		myDays = mySchedule.getSchedule();
		dialog = new Dialog(AskMealActivity.this);
		dialog.setTitle("밥 약속 신청");
		dialog.setContentView(R.layout.dialog_ask_meal);
		textViewConfirmMessage = (TextView)findViewById(R.id.textView_confirmMessage);
		editTextMessage = (EditText)findViewById(R.id.editText_message);
		btnConfirm = (Button)findViewById(R.id.askMeal_btnConfirm);
		btnCancel = (Button)findViewById(R.id.askMeal_btnCancel);
		checkBoxState = (CheckBox)findViewById(R.id.checkBoxState);
		
		Calendar cal = Calendar.getInstance();
		int todayDate = cal.get(Calendar.DATE); // 1~31
		int todayDow =  cal.get(Calendar.DAY_OF_WEEK)-1; // 0~6 
		int todayMonth = cal.get(Calendar.MONTH); // 0~11
		int thisYear = cal.get(Calendar.YEAR); // 0~11
		month = todayMonth+1;
		date = todayDate;
		if(dow <= todayDow)
			date += 7;

		date += dow-todayDow;
		// 2월인 경우
		if(todayMonth == 1){
			if (0 == (thisYear%4) && 0 != (thisYear%100) || 0 == thisYear%400 ){
				// 윤년
				if(date > 29){
					month++;
					date = date-29;
				}
		    }else{
		    	// 유년 아님
		    	if(date > 28){
		    		month++;
		    		date = date-28;
		    	}
		    }
		}
		if(date > 30){
			month++;
			// 31일
			if(todayMonth == 0 ||todayMonth == 2 || todayMonth == 4 || todayMonth == 6 || todayMonth == 7 || todayMonth == 9 || todayMonth == 11){
				date = date-31;
			}
			// 30일
			else{
				date = date-30;
			}
		}
		
		askMealDate.setText(month+"월 " + date+"일("+week[dow]+"요일)");
		

		

		list = new ArrayList<String>();
		if(("".equals(days.get(position).getBreakfast().getPartnerName()))
				&& "".equals(myDays.get(position).getBreakfast().getPartnerName())){
			//btnAskBreakfast.setEnabled(false);
			//btnAskBreakfast.setText("신청불가");
			list.add("아침");
		}
		if(("".equals(days.get(position).getLunch().getPartnerName()))
				&& "".equals(myDays.get(position).getLunch().getPartnerName())){
			//btnAskLunch.setEnabled(false);
			//btnAskLunch.setText("신청불가");
			list.add("점심");
		}
		if("".equals(days.get(position).getDinner().getPartnerName())
				&& "".equals(myDays.get(position).getDinner().getPartnerName())){
			//btnAskDinner.setEnabled(false);
			//btnAskDinner.setText("신청불가");
			list.add("저녁");
		}
		
		adapter = new ArrayAdapter<String>(AskMealActivity.this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn.setAdapter(adapter);
		
		/*
		
		btnAskBreakfast.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//askMealAsync.execute(GlobalVariables.BREAKFAST);
				textViewConfirmMessage.setText(name+"님과 아침식사를 하시겠습니까?");
				meal = GlobalVariables.BREAKFAST;
				dialog.show();
				
			}
		});
		
		btnAskLunch.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//askMealAsync.execute(GlobalVariables.LUNCH);
				textViewConfirmMessage.setText(name+"님과 점심식사를 하시겠습니까?");
				meal = GlobalVariables.LUNCH;
				dialog.show();
			}
		});
		
		btnAskDinner.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//askMealAsync.execute(GlobalVariables.DINNER);
				textViewConfirmMessage.setText(name+"님과 저녁식사를 하시겠습니까?");
				meal = GlobalVariables.DINNER;
				dialog.show();
			}
		});
	
		*/
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendMessage = editTextMessage.getText().toString();
				
				if(spn.getSelectedItem().toString().equals("아침"))
					meal = GlobalVariables.BREAKFAST;
				else if(spn.getSelectedItem().toString().equals("점심"))
					meal = GlobalVariables.LUNCH;
				else if(spn.getSelectedItem().toString().equals("저녁"))
					meal = GlobalVariables.DINNER;
				else
					;
				askMealAsync.execute("");
			}
		});
		
		btnCancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	
	}
	
	private class AskMealAsyncTask extends AsyncTask<String,String,String>{
		@Override
		protected void onPreExecute(){
			showDialog(0);
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Message m = new Message();
			
			m.setMeal(meal);
			//m.setFrom(from)
			m.setFromId(prefHelper.getPhone());
			m.setFromPhone(prefHelper.getPhone());
			m.setConfirm(checkBoxState.isChecked()? 1: 0);
			m.setDate(date);
			m.setDow(dow);
			m.setMonth(month);
			m.setToId(schedule.getKey());
			m.setTo(name);
			m.setState(GlobalVariables.ASK);
			m.setMessage(sendMessage);
			Message retMessage = helper.askMeal(m);
			return retMessage.getMessage();
		}
		
		@Override
		protected void onPostExecute(String arg){
			//removeDialog(0);
			//dialog.dismiss();
			if(GlobalVariables.SUCCEED.equals(arg)){
				showDialog(1);
			}else{
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
			ab.setTitle("약속 신청");
			ab.setMessage("신청 완료");
			ab.setPositiveButton("확인", new android.content.DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			return ab.create();
		case 2:
			ab = new AlertDialog.Builder(this);
			ab.setTitle("약속 신청");
			ab.setMessage("밥 약속 신청을 실패하였습니다");
			ab.setPositiveButton("확인", new android.content.DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
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
				}
			});
			return ab.create();
		default:
			return null;
		}
	}
}
