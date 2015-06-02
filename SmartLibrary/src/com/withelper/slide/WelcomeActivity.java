package com.withelper.slide;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.withelper.R;
import com.withelper.util.NetworkService;


public class WelcomeActivity extends Activity{
	
	private Handler mHandler;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);    
        initView();
    }
    
    Handler checkSessionIdHandler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String result = data.getString("result");

	        try {  
	        	JSONObject json = new JSONObject(result);
	            String error = json.getString("error");
	            Log.v("debug", error);
	            //Log.v("book",json.toString());
	            if("".equals(error) || error == null || "null".equals(error)){ 
	            	goMenuActivity();
	            }
	            else if("-5".equals(error)){
	            	goLoginActivity();
	            }
	            else{
	            	Toast.makeText(getApplicationContext(), "网络异常",
	        				Toast.LENGTH_SHORT).show();
	            	finish();
	            }
	        } catch (JSONException ex) {  
	        	ex.printStackTrace();
	        } 
	    }
	};

    
    public void initView()
    {
    	mHandler = new Handler();
    	mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
//		    	Log.v("debug", "123");
				SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE); //私有数据
				String check = sharedPreferences.getString("isCheck", null);
				if(check != null){
					if(check.equals("Y")){
		            	String sessionid = sharedPreferences.getString("sessionid",null);
		            	checkSessionId(sessionid);
					}else{
						goLoginActivity();
					}
				}else{
					goLoginActivity();
				}
			}
    	}, 1000);
    }
    
    public void checkSessionId(final String sessionid){
		final List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("sessionid", sessionid));
		new Thread(new Runnable(){  
            @Override  
            public void run() {
            	Message msg = new Message();
            	Bundle data = new Bundle();
            	String url = "CheckSessionId";
            	final List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            	paramList.add(new BasicNameValuePair("sessionid", sessionid));
            	String resultData = NetworkService.getPostResult(url, paramList);
				data.putString("result", resultData);
				msg.setData(data);
				checkSessionIdHandler.sendMessage(msg); 
            }
		}).start();
	}
    
    public void goLoginActivity()
    {
    	Intent intent = new Intent();
    	intent.setClass(this,LoginActivity.class);
    	startActivity(intent);
    	finish();
    }
    
    public void goMenuActivity()
    {
    	Intent intent = new Intent();
    	intent.setClass(this,MenuActivity.class);
    	startActivity(intent);
    	finish();
    }
}