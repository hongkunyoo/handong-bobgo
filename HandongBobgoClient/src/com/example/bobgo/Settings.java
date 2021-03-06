package com.example.bobgo;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.util.PreferenceHelper;
import edu.handong.babgo.util.RequestHelper;

public class Settings extends Activity {
    ProgressDialog progressDialog;
    AlertDialog.Builder ab;
    Dialog dialog;
    Context context;
    PreferenceHelper preHelper;
    RequestHelper helper = GlobalVariables.getRequestHelper();
    ImageView iv_id;
    ImageView iv_ap;
    EditText et_id;
    EditText et_pw;
    EditText et_pn;
    Button bt_ok;
    Button logout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
        context = this.getApplicationContext();
        preHelper = new PreferenceHelper(Settings.this);
        
	    iv_id = (ImageView)findViewById(R.id.id_setting);
	    iv_ap = (ImageView)findViewById(R.id.appointment_setting);
	    
	    dialog = new Dialog(Settings.this);
	    dialog.setContentView(R.layout.layout_dialog);
	    et_id = (EditText)dialog.findViewById(R.id.IDedit);
		et_pw = (EditText)dialog.findViewById(R.id.PWedit);
		et_pn = (EditText)dialog.findViewById(R.id.PNedit);
		//et_pn.setEnabled(false);
		et_pn.setText(preHelper.getPhone());
		dialog.setTitle("Information Setting");
		
		bt_ok = (Button)dialog.findViewById(R.id.bt_ok);
		logout = (Button)dialog.findViewById(R.id.bt_cancel);
		
		
		bt_ok.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				String id = et_id.getText().toString();
				String pw = et_pw.getText().toString();
				//String pn = preHelper.getPhone();
				String pn = et_pn.getText().toString();
				preHelper.setPhone(pn);
				new SettingsAsyncTask().execute(id,pw,pn,"NEW");

				dialog.dismiss();
			}
		});
		logout.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				String id = preHelper.getHisnetId();
				String pw = preHelper.getPassword();
				String pn = preHelper.getPhone();
				preHelper.setHisnetId("");
				preHelper.setPassword("");
				new SettingsAsyncTask().execute(id,pw,pn,GlobalVariables.LOGOUT);
				dialog.dismiss();
			}
		});
		
		
	    iv_id.setOnClickListener(new View.OnClickListener(){
	    	
    		
	    	public void onClick(View v){
	    		et_id.setText("");
	    		et_pw.setText("");
	    		//et_pn.setText("");
	    		et_id.setHint(preHelper.getHisnetId());
				et_pn.setHint(preHelper.getPhone());
	    		dialog.show();
	    		
	    	}
	    });
	    
	    iv_ap.setOnClickListener(new View.OnClickListener(){
	    	
	    	public void onClick(View v){
		    	Intent i = new Intent(Settings.this, SetScheduleActivity.class);
		    	startActivity(i);
	    	}
	    	
	    });
    
    }
    
    private class SettingsAsyncTask extends AsyncTask<String, String, String>{
    	String innerHisnetId;
    	String innerPassword;
    	String innerPhone;
    	@Override
    	protected void onPreExecute(){
    		showDialog(0);
    	}
    	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(params[3].equals("NEW")){
				innerHisnetId = params[0];
				innerPassword = params[1];
				innerPhone = params[2];
				
				Message m = helper.setAccount(innerPhone, innerHisnetId, innerPassword, 1);
				///////////////////////////////////////////////////////////////////////////
				// Codes for GCM
				///////////////////////////////////////////////////////////////////////////
				GCMRegistrar.checkDevice(Settings.this);
				GCMRegistrar.checkManifest(Settings.this);
				final String regId = GCMRegistrar.getRegistrationId(Settings.this);
				if (regId.equals("")) {
					GCMRegistrar.register(Settings.this, GlobalVariables.SENDER_ID);
				}
				else{
					Message msg = new Message();
					msg.setKey(preHelper.getPhone());
					msg.setMessage(GlobalVariables.REGISTER_ID);
					msg.setMeal(regId);
					helper.sendMessage(preHelper.getPhone(),msg);
				}
				return m.getMessage();
			}else{
				Message sendMessage = new Message();
				sendMessage.setKey(params[2]);
				sendMessage.setMessage(GlobalVariables.LOGOUT);
				Message m = helper.sendMessage(params[2], sendMessage);
				return m.getMessage();
			}
		}
		
		@Override
		protected void onPostExecute(String arg){
			removeDialog(0);
			if(GlobalVariables.SUCCEED.equals(arg)){
				preHelper.setHisnetId(innerHisnetId);
				preHelper.setPassword(innerPassword);
				//preHelper.setPhone(innerPhone);
				showDialog(1);
			}else if(GlobalVariables.DUPLICATED.equals(arg)){
				showDialog(3);
			}else{
				preHelper.setHisnetId("");
				preHelper.setPassword("");
				//preHelper.setPhone("");
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
			ab.setMessage("완료");
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
			ab.setMessage("입력값을 다시 확인해 주세요");
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
			ab.setTitle("설정 실패");
			ab.setMessage("이미 계정 설정하셨습니다.");
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
