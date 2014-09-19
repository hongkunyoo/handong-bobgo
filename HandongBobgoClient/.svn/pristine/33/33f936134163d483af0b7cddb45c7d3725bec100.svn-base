package com.example.bobgo;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.util.PreferenceHelper;
import edu.handong.babgo.util.RequestHelper;

public class MainActivity extends Activity {
	int flag = 0;
	boolean DEBUG = false; 
	PreferenceHelper prefHelper;
	AlertDialog.Builder ab;
	Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefHelper = new PreferenceHelper(MainActivity.this);
        context = this.getApplicationContext();
        String phone = prefHelper.getPhone();
        if("".equals(phone)) prefHelper.setPhone(((TelephonyManager)this.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number());
        if(prefHelper.getPhone().contains("+")){
        	String p = prefHelper.getPhone();
        	p = "0"+p.substring(3);
        	prefHelper.setPhone(p);
        }
        
        
        if(DEBUG){
        /////////////////////////////////////
        Intent i = new Intent(MainActivity.this,TestDriverActivity.class);
        startActivity(i);
        /////////////////////////////////////
        
        }else{
	        new AsyncTask<String,String,String>(){
	        	List<Message> list = null;
	        	@Override
	        	protected void onPreExecute(){
	        		
	        	}
				@Override
				protected String doInBackground(String... arg0) {
					// TODO Auto-generated method stub
					if(!GlobalVariables.isConnected(context)) {
						return GlobalVariables.NETWORK_FAIL;
					}
					RequestHelper helper = GlobalVariables.getRequestHelper();
					String id = prefHelper.getPhone();
					if("".equals(id)) return GlobalVariables.FAILED;
					list = helper.getNewUpdates(id);
					if(list == null) return GlobalVariables.FAILED;
					else if(list.size() == 0) return GlobalVariables.EMPTY;
					else return GlobalVariables.SUCCEED;
				}
				@Override
				protected void onPostExecute(String arg){
					Intent i;
					if(arg.equals(GlobalVariables.SUCCEED)){
						i = new Intent(MainActivity.this, MessageListActivity.class);
						GlobalVariables gv = GlobalVariables.getInstance();
						gv.setData("newUpdateList", list);
						i.putExtra(GlobalVariables.RESULT, arg);
				        startActivity(i);
					}
					else if(arg.equals(GlobalVariables.NETWORK_FAIL)){
						showDialog(0);
					}
					else{
						i = new Intent(MainActivity.this, MenuSelect.class);
						i.putExtra(GlobalVariables.RESULT, arg);
				        startActivity(i);
					}
					
				}
	        }.execute("");
        }

    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	flag++;
    	if(flag == 2)
    		finish();
    }

    @Override
	public Dialog onCreateDialog(int id) {
		switch(id){
			
		case 0:
			ab = new AlertDialog.Builder(this);
			ab.setTitle("네트워크 장애");
			ab.setMessage("네트워크 연결을 확인해주세요.");
			ab.setPositiveButton("확인", new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			return ab.create();
		default:
			return null;
		}
	}
}
