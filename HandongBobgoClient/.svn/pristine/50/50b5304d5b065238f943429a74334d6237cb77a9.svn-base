package edu.handong.babgo.util;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import edu.handong.babgo.model.User;

public class PhoneBookManager {
	Context context;
	
	public PhoneBookManager(Context context){
		this.context = context;
	}
	
	public ArrayList<User> getUsers(){
		ArrayList<User> list = new ArrayList<User>();
		String preName = "";
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		        String[] projection = new String[] {           
		                ContactsContract.CommonDataKinds.Phone.NUMBER,
		                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
		        };
	
		        String[] selectionArgs = null;
		        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		        Cursor contactCursor = context.getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);
		        if(contactCursor.moveToFirst()){       
		            do{
		            	String userName = contactCursor.getString(1);
		            	String phone = contactCursor.getString(0);
		                User u = new User();
		                u.setName(userName);
		                u.setPhone(phone);
		                if(!preName.equals(userName)){
		                	preName = userName;
		                	list.add(u);
		                }
		            }while(contactCursor.moveToNext());
		        }

		
		return list;
	}
	public ArrayList<User> getUserByName(String search){
		ArrayList<User> list = new ArrayList<User>();
		ArrayList<User> dataList = this.getUsers();
		search = search.toUpperCase();
		
		// 검색해서 returnList 구성
		for(int i = 0 ; i < dataList.size() ; i++){
			if(dataList.get(i).getName().toUpperCase().contains(search)){
				list.add(dataList.get(i));
			}
		}

		return list;
	
	}
	public User getUserByPhone(String phone){
		
		return null;
	}
}
