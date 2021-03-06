package com.example.bobgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Message;
import edu.handong.babgo.util.RequestHelper;

public class MessageListActivity extends Activity {
	ListView listView;
	ArrayList<HashMap<String,String>> dataList = new ArrayList<HashMap<String,String>>();
	SimpleAdapter adapter;
	List<Message> list;
	Context context;
	ProgressDialog progressDialog;
	AlertDialog.Builder ab;
	Message m;
	int index;
	int flag = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        context = this.getApplicationContext();
        listView = (ListView)findViewById(R.id.messageListView);
        adapter = new SimpleAdapter(this.getApplicationContext(), dataList, R.layout.apply_list_item,
        		new String[]{"text"}, new int[]{R.id.apply});
        listView.setAdapter(adapter);
        
        //listView.setBackgroundColor(Color.BLACK);
        
        Intent i = this.getIntent();
        
        new AsyncTask<String,String,String>(){
        	String[] dowStr = new String[]{"일","월","화","수","목","금","토"};
        	@Override
        	protected void onPreExecute(){
        		
        	}
        	
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				if(params[0].equals(GlobalVariables.SUCCEED)){
					list = (List<Message>)GlobalVariables.getInstance().getData("newUpdateList");
					for(int i = 0 ; i < list.size() ; i++){
						Message m = list.get(i);
						HashMap<String, String> map = new HashMap<String, String>();


						if(m.getState() == GlobalVariables.ASK){
							map.put("text",m.getFrom()+"님께서 "+dowStr[m.getDow()]+"요일("+
									m.getMonth()+"월 "+m.getDate()+"일)"+m.getMeal()+(m.getConfirm() == 1 ? "고정": "")+"약속을 신청하셨습니다.");
							//dataList.add(m.getFrom()+"님께서 "+dowStr[m.getDow()]+"요일("+
							//m.getMonth()+"월 "+m.getDate()+"일"+m.getMeal()+(m.getConfirm() == 1 ? "고정": "")+"약속을 신청하셨습니다.");
						}
						else if(m.getState() == GlobalVariables.ACCEPT){
							map.put("text",m.getFrom()+"님께서 "+dowStr[m.getDow()]+"요일("+
									m.getMonth()+"월 "+m.getDate()+"일)"+m.getMeal()+(m.getConfirm() == 1 ? "고정": "")+"약속을 허락하셨습니다.");
							//dataList.add(m.getFrom()+"님께서 "+dowStr[m.getDow()]+"요일("+
							//m.getMonth()+"월 "+m.getDate()+"일"+m.getMeal()+(m.getConfirm() == 1 ? "고정": "")+"약속을 허락하셨습니다.");
						}else{
							map.put("text",m.getFrom()+"님께서 "+dowStr[m.getDow()]+"요일("+
									m.getMonth()+"월 "+m.getDate()+"일)"+m.getMeal()+(m.getConfirm() == 1 ? "고정": "")+"약속을 거절하셨습니다.");
							//dataList.add(m.getFrom()+"님께서 "+dowStr[m.getDow()]+"요일("+
								//	m.getMonth()+"월 "+m.getDate()+"일"+m.getMeal()+(m.getConfirm() == 1 ? "고정": "")+"약속을 거절하셨습니다.");
						}

						dataList.add(map);
					}
				}
				
				return params[0];
			}
			
			@Override
			protected void onPostExecute(String arg){
				if(arg.equals(GlobalVariables.SUCCEED))
					adapter.notifyDataSetChanged();
			}
        	
        }.execute(i.getStringExtra(GlobalVariables.RESULT));
        
        
        
        
        listView.setOnItemClickListener(new OnItemClickListener(){
        	AlertDialog.Builder db;
//        	RequestHelper helper = GlobalVariables.getRequestHelper();
        	
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				m = list.get(arg2);
				index = arg2;
				db = new AlertDialog.Builder(MessageListActivity.this);
				if(m.getState() == GlobalVariables.ASK){
					
					db.setTitle(m.getFrom()+"님과의 식사를 수락하시겠습니까?");
					db.setMessage(m.getMessage());
					db.setPositiveButton("예", new OnClickListener(){
	
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							new AsyncHelp().execute(GlobalVariables.YES);
						}
						
					});
					
					db.setNegativeButton("아니요", new OnClickListener(){
	
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							new AsyncHelp().execute(GlobalVariables.NO);
						}
						
					});
				}else{
					db.setTitle("결과 확인");
					db.setMessage(m.getState() == GlobalVariables.ACCEPT ? "수락" : "거절");
					db.setPositiveButton("확인", new OnClickListener(){
	
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							new AsyncHelp().execute(""+GlobalVariables.CONFIRM);
						}
						
					});
				}
				db.show();
			}
        });
        
        
	}
	
	
	private class AsyncHelp extends AsyncTask<String, String, String>{
		RequestHelper helper = GlobalVariables.getRequestHelper();
		@Override
		protected void onPreExecute(){
			showDialog(0);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Message retMessage;
			if(GlobalVariables.YES.equals(arg0[0])){
				m.setMessage(GlobalVariables.YES);
				retMessage = helper.acceptMeal(m);
			} else if(GlobalVariables.NO.equals(arg0[0])){
				m.setMessage(GlobalVariables.NO);
				retMessage = helper.refuseMeal(m);
			}else{ // Confrim
				m.setMessage(""+GlobalVariables.CONFIRM);
				retMessage = helper.sendMessage(m.getToId(),m);
			}
			dataList.remove(index);
			if(retMessage == null) return GlobalVariables.FAILED;
			return retMessage.getMessage();
		}
		@Override
		protected void onPostExecute(String arg){
			adapter.notifyDataSetChanged();
			removeDialog(0);
			if(GlobalVariables.SUCCEED.equals(arg))
				showDialog(1);
			else
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
			ab.setTitle("신청 확인");
			ab.setMessage("처리 완료");
			ab.setPositiveButton("확인", new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(dataList.size() == 0){
						Intent i = new Intent(MessageListActivity.this, MenuSelect.class);
						startActivity(i);
					}
				}
			});
			return ab.create();
		case 2:
			ab = new AlertDialog.Builder(this);
			ab.setTitle("신청 확인");
			ab.setMessage("처리 실패");
			ab.setPositiveButton("확인", new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(dataList.size() == 0){
						Intent i = new Intent(MessageListActivity.this, MenuSelect.class);
						startActivity(i);
					}
				}
			});
			return ab.create();
		default:
			return null;
		}
	}
	
	@Override
    public void onResume(){
		super.onResume();
		flag++;
		if(flag == 2)
			finish();
    }
}
