package com.example.bobgo;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Day;
import edu.handong.babgo.model.Partner;

public class SetMealActivity extends Activity {
	EditText breakfast;
	EditText lunch;
	EditText dinner;
	TextView setmealDate;
	Button btnConfirm;
	Button btnInit;
	int position;
	int flag = 0;
	AlertDialog.Builder ab;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_meal);
		
		breakfast = (EditText)findViewById(R.id.editTextBreakfast);
		lunch = (EditText)findViewById(R.id.editTextLunch);
		dinner = (EditText)findViewById(R.id.editTextDinner);
		btnConfirm = (Button)findViewById(R.id.btnConfirm);
		btnInit = (Button)findViewById(R.id.btnInit);
		
		Intent i = getIntent();
		position = i.getIntExtra("position",0);
		String strBreakfast = i.getStringExtra("breakfast");
		String strLunch = i.getStringExtra("lunch");
		String strDinner = i.getStringExtra("dinner");
		

		setmealDate = (TextView)findViewById(R.id.setmealDate);
		Calendar cal = Calendar.getInstance();
		int todayDate = cal.get(Calendar.DATE); // 1~31
		int todayDow =  cal.get(Calendar.DAY_OF_WEEK)-1; // 0~6 
		int todayMonth = cal.get(Calendar.MONTH); // 0~11
		int thisYear = cal.get(Calendar.YEAR); // 0~11
		int month = todayMonth+1;
		int date = todayDate;
		int dow =  getIntent().getIntExtra("position", -1);
		String [] week = new String[]{"일","월","화","수","목","금","토"};
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
		
		setmealDate.setText(month+"월 " + date+"일("+week[dow]+"요일)");
		
		breakfast.setText(strBreakfast);
		lunch.setText(strLunch);
		dinner.setText(strDinner);
		
		if("신청대기".equals(breakfast.getText().toString()))
			breakfast.setEnabled(false);
		if("신청대기".equals(lunch.getText().toString()))
				lunch.setEnabled(false);
		if("신청대기".equals(dinner.getText().toString()))
				dinner.setEnabled(false);
				
		breakfast.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(flag != 0) return false;
				flag++;
				if("신청대기".equals(breakfast.getText().toString())){
					ab = new AlertDialog.Builder(SetMealActivity.this);
					ab.setTitle("신청대기 취소하시겠습니까?");
					ab.setPositiveButton("", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					ab.show();
				}else{
					Intent i = new Intent(SetMealActivity.this,FriendMatching.class);
					i.putExtra("meal","breakfast");
					startActivityForResult(i, GlobalVariables.FROM_SET_SCHEDULE);
				}
				
				return false;
			}
		});
		
		lunch.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(flag != 0) return false;
					flag++;
				if("신청대기".equals(lunch.getText().toString())){
					ab = new AlertDialog.Builder(SetMealActivity.this);
					ab.setTitle("신청대기 취소하시겠습니까?");
					ab.setPositiveButton("", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					ab.show();
				}else{
					
					Intent i = new Intent(SetMealActivity.this,FriendMatching.class);
					i.putExtra("meal","lunch");
					startActivityForResult(i, GlobalVariables.FROM_SET_SCHEDULE);
				
				}
				return false;
			}
		});

		dinner.setOnTouchListener(new OnTouchListener() {
		
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(flag != 0) return false;
					flag++;
				if("신청대기".equals(dinner.getText().toString())){
					ab = new AlertDialog.Builder(SetMealActivity.this);
					ab.setTitle("신청대기 취소하시겠습니까?");
					ab.setPositiveButton("", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					ab.show();
				}else{
					Intent i = new Intent(SetMealActivity.this,FriendMatching.class);
					i.putExtra("meal","dinner");
					startActivityForResult(i, GlobalVariables.FROM_SET_SCHEDULE);
					
				}
				return false;
			}
		});
		
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Partner partnerBreakfast = (Partner)GlobalVariables.getInstance().getData("partnerbreakfast");
				Partner partnerLunch = (Partner)GlobalVariables.getInstance().getData("partnerlunch");
				Partner partnerDinner = (Partner)GlobalVariables.getInstance().getData("partnerdinner");
				Day d = new Day();
				d.setBreakfast(partnerBreakfast != null && !breakfast.getText().toString().equals("") ? partnerBreakfast : new Partner());
				d.setLunch(partnerLunch != null && !lunch.getText().toString().equals("") ? partnerLunch : new Partner());
				d.setDinner(partnerDinner != null && !dinner.getText().toString().equals("") ? partnerDinner : new Partner());
				d.setDow(position);
				GlobalVariables.getInstance().setData("day"+position, d);
				
				Intent i = new Intent();
    			i.putExtra("position", position);
    			setResult(RESULT_OK, i);
				finish();
			}
		});
		
		
		btnInit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!"신청대기".equals(breakfast.getText().toString()))
					breakfast.setText("");
				if(!"신청대기".equals(lunch.getText().toString()))
					lunch.setText("");
				if(!"신청대기".equals(dinner.getText().toString()))
					dinner.setText("");
			}
		});
		
	}
	@Override
	public void onResume(){
		super.onResume();
		flag = 0;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == Activity.RESULT_OK){
			String meal = data.getStringExtra("meal");
			if(meal.equals("breakfast"))
				breakfast.setText(data.getStringExtra("name"));
			else if(meal.equals("lunch"))
				lunch.setText(data.getStringExtra("name"));
			else
				dinner.setText(data.getStringExtra("name"));
		}
		
	}
}
