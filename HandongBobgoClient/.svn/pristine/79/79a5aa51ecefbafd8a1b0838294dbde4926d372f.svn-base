package com.example.bobgo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import edu.handong.babgo.common.GlobalVariables;
import edu.handong.babgo.util.HttpRequestHelper;

public class TestDriverActivity extends Activity {
	Button todays;
	Button getNew;
	WebView webView;
	HttpRequestHelper helper = new HttpRequestHelper();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_driver);
        
        todays = (Button)findViewById(R.id.btn_todays);
        getNew = (Button)findViewById(R.id.btn_getNewUpdates);
        webView = (WebView)findViewById(R.id.webView1);
        
        todays.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strUrl = "http://hgyoo89.appspot.com/test";
				String str = helper.getJsonStringByGet(strUrl);
				Log.e("ERROR",str);
			}
		});
        
        getNew.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        
        
	}
        
}
