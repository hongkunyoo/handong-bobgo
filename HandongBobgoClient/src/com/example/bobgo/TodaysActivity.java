package com.example.bobgo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Day;
import edu.handong.babgo.util.PreferenceHelper;
import edu.handong.babgo.util.RequestHelper;

public class TodaysActivity extends Activity {
	Button btnBreakfast;
	Button btnLunch;
	Button btnDinner;
	RequestHelper helper;
	PreferenceHelper prefHelper;
	ProgressDialog progressDialog;
	AlertDialog.Builder ab;
	Day today;
	Context context;
	TextView tvDate;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todays);
		btnBreakfast = (Button)findViewById(R.id.btn_breakfast);
		btnLunch = (Button)findViewById(R.id.btn_lunch);
		btnDinner = (Button)findViewById(R.id.btn_dinner);
		context = this.getApplicationContext();
		helper = GlobalVariables.getRequestHelper();
		prefHelper = new PreferenceHelper(this);
		tvDate = (TextView)findViewById(R.id.tv_date);
		SimpleDateFormat sdf = new SimpleDateFormat("MM'월' dd'일' E"+"요일", Locale.KOREA);
		Date todayDate = new Date();
		
		tvDate.setText(sdf.format(todayDate));		
		
		btnBreakfast.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder adb = new AlertDialog.Builder(TodaysActivity.this);
				adb.setTitle("전화 걸기");
				adb.setMessage(today.getBreakfast().getPartnerName()+"님에게 전화를 거시겠습니까?");
				adb.setPositiveButton("예", new android.content.DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+today.getBreakfast().getPartnerPhone()));
						startActivity(intent);
					}
				});
				
				adb.setNegativeButton("아니요", new android.content.DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
				
				adb.create().show();
			}
		});
		
		
		
		btnLunch.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder adb = new AlertDialog.Builder(TodaysActivity.this);
				adb.setTitle("전화 걸기");
				adb.setMessage(today.getLunch().getPartnerName()+"님에게 전화를 거시겠습니까?");
				adb.setPositiveButton("예", new android.content.DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+today.getLunch().getPartnerPhone()));
						startActivity(intent);
					}
				});
				
				adb.setNegativeButton("아니요", new android.content.DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
				
				adb.create().show();
			}
		});
		
		
		
		btnDinner.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder adb = new AlertDialog.Builder(TodaysActivity.this);
				adb.setTitle("전화 걸기");
				adb.setMessage(today.getDinner().getPartnerName()+"님에게 전화를 거시겠습니까?");
				adb.setPositiveButton("예", new android.content.DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+today.getDinner().getPartnerPhone()));
						startActivity(intent);
					}
				});
				
				adb.setNegativeButton("아니요", new android.content.DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
				
				adb.create().show();
			}
		});
		
		new GetTodaysAsyncTask().execute("");
	}
	
	private class GetTodaysAsyncTask  extends AsyncTask<String, String, String>{
		@Override
		protected void onPreExecute(){
			showDialog(0);
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(prefHelper.getHisnetId().equals(""))
				return GlobalVariables.FAILED;
			else{
				today = helper.getTodaysBabgo(prefHelper.getPhone());
				if(today != null) return GlobalVariables.SUCCEED;
				else return GlobalVariables.FAILED;
			}
		}
		@Override
		protected void onPostExecute(String arg){
			removeDialog(0);
			if(GlobalVariables.SUCCEED.equals(arg)){
				btnBreakfast.setText(today.getBreakfast().getPartnerName());
				btnLunch.setText(today.getLunch().getPartnerName());
				btnDinner.setText(today.getDinner().getPartnerName());
			}else{
				btnBreakfast.setText("unknown");
				btnLunch.setText("unknown");
				btnDinner.setText("unknown");
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
			ab.setTitle("조회 성공");
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
			ab.setTitle("조회 실패");
			ab.setMessage("인터넷을 확인해주세요.");
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
