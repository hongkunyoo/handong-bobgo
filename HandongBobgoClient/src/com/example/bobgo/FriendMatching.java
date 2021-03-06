package com.example.bobgo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.model.Partner;
import edu.handong.babgo.model.User;
import edu.handong.babgo.util.PhoneBookManager;
import edu.handong.babgo.util.PreferenceHelper;

public class FriendMatching extends Activity {

	String tag = null;
	AutoCompleteTextView searchText;
	ListView listView;
	ArrayAdapter<String> adapter;
	ArrayList<User> userList;
	ArrayList<String> nameList = new ArrayList<String>();
	DoSearchAsyncTask dosearch = new DoSearchAsyncTask();
	ProgressDialog progressDialog;
	AlertDialog.Builder ab;
	Context context;
	PreferenceHelper prefHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_friend_matching);
        searchText = (AutoCompleteTextView)findViewById(R.id.searchText);
        context = this.getApplicationContext();
        prefHelper = new PreferenceHelper(this.getApplicationContext());
        if(prefHelper.getHisnetId().equals("")){
        	showDialog(1);
        }
        searchText.addTextChangedListener(new TextWatcher(){

			//@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				// ignore
			}

			//@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				// ignore
			}

			//@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				// 검색어 받기
				String search = searchText.getText().toString();
				updateList(search);
			}
		});
        //searchText.setText("재용");
        listView = (ListView)findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(FriendMatching.this, android.R.layout.simple_list_item_1, nameList);
        listView.setAdapter(adapter);
        
        dosearch.execute();
        
        //if any friend has Bobgo app, then request him to make appointment with the user
        listView.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        		Intent fromIntent = getIntent();
        		String selectedName = parent.getItemAtPosition(position).toString();
        		User user = null;
        		if(!fromIntent.hasExtra("meal")){
	        		Intent i = new Intent(FriendMatching.this, FriendScheduleView.class);
	        		i.putExtra("name", selectedName);
	        		startActivity(i);
        		}else{
        			String meal = fromIntent.getStringExtra("meal");
        			Intent i = new Intent();
        			i.putExtra("name", selectedName);
        			i.putExtra("meal", meal);
        			setResult(RESULT_OK, i);
        			
        			for(int j = 0 ; j < userList.size() ; j++){
        				if(userList.get(j).getName().equals(selectedName)){
        					user = userList.get(j);
        				}
        			}
        			Partner p = new Partner();
        			p.setFixed(1);
        			p.setMeal(meal);
        			p.setPartnerName(selectedName);
        			p.setPartnerPhone(user.getPhone());
        			p.setState(0);
        			GlobalVariables.getInstance().setData("partner"+meal,p);
        			
        			finish();
        		}
        	}
        });
       
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
			ab.setMessage("계정 설정을 해주세요.");
			ab.setPositiveButton("확인", new android.content.DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
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
				}
			});
			return ab.create();
		default:
			return null;
		}
	}
    private void updateList(String search){
		ArrayList<User> dataList = this.userList;
		search = search.toUpperCase();
		nameList.clear();
		// 검색해서 returnList 구성
		for(int i = 0 ; i < dataList.size() ; i++){
			if(dataList.get(i).getName().toUpperCase().contains(search)){
				nameList.add(dataList.get(i).getName());
			}
		}
		adapter.notifyDataSetChanged();
    }
    
    private class DoSearchAsyncTask extends AsyncTask<String, String, String>{
    	
    	@Override
    	protected void onPreExecute(){
    		showDialog(0);
    	}
    	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			userList = new PhoneBookManager(context).getUsers();
			
			for(int i = 0 ; i < userList.size() ; i++)
				nameList.add(userList.get(i).getName());
			
			return null;
		}
    	
		@Override
		protected void onPostExecute(String arg){
			adapter.notifyDataSetChanged();
			GlobalVariables.getInstance().setData("dataList", userList);
			removeDialog(0);
		}
    	
    }
}