package com.example.bobgo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MenuSelect extends Activity {
	ImageView todays;
	ImageView search;
	ImageView random;
	ImageView settings;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_select);
        
        todays = (ImageView)findViewById(R.id.bob_today);
        search = (ImageView)findViewById(R.id.bob_search);
        random = (ImageView)findViewById(R.id.bob_random);
        settings = (ImageView)findViewById(R.id.bob_config);
        
        
        //////////////////////////////////////////////////
        // 오늘의 밥고 확인하는 Activity 이동
        //////////////////////////////////////////////////
        todays.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MenuSelect.this, TodaysActivity.class);
				startActivity(i);
			}
		});
        
        
        
        
        
		//////////////////////////////////////////////////
		// 밥고 검색 Activity 이동
		//////////////////////////////////////////////////
        search.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MenuSelect.this, FriendMatching.class);
				startActivity(i);
			}
		});
        
        
        
        
        
		//////////////////////////////////////////////////
		// 랜던 밥고 Activity 이동
		//////////////////////////////////////////////////
		random.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MenuSelect.this, RandomActivity.class);
				startActivity(i);
			}
		});
        
		
		
        
        
		//////////////////////////////////////////////////
		// 계정, 스케줄 설정 Activity 이동
		//////////////////////////////////////////////////
        settings.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MenuSelect.this, Settings.class);
				startActivity(i);
			}
		});
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_menu_select, menu);
        return true;
    }
    */
}
