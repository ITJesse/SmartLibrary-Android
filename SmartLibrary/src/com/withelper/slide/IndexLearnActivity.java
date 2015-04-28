package com.withelper.slide;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.withelper.R;
import com.withelper.util.NetworkService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IndexLearnActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.learn);	
		   
		
		
		 Button btn = (Button)findViewById(R.id.refresh);
		   //绑定匿名的监听器，并执行您所要在点击按钮后执行的逻辑代码
		     btn.setOnClickListener(new View.OnClickListener() {
             public void onClick(View arg0) {
            	 
            	 SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE); //私有数据
	        		//Editor editor = sharedPreferences.edit();
	        		String sessionid = sharedPreferences.getString("sessionid",null);
	        		Log.v("sessionid", sessionid);
            	 final List<NameValuePair> paramList = new ArrayList<NameValuePair>();
         		paramList.add(new BasicNameValuePair("sessionid",sessionid));
         		Toast.makeText(getApplicationContext(), "正在刷新",
         				Toast.LENGTH_SHORT).show();
         		new Thread(new Runnable(){  
                     @Override  
                     public void run() {
                     	
                    	 Message msg = new Message();
                     	Bundle data = new Bundle();
                     	String url = "StudyRoomSeat";
                     	String resultData = NetworkService.getPostResult(url, paramList);
         				data.putString("result", resultData);
         				msg.setData(data);
         				learnHandler.sendMessage(msg); 
                     }
         		}).start();
         		
		 }
		});

	}
	Handler learnHandler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String result = data.getString("result");
	        try {  
	            JSONObject json = new JSONObject(result);
	            String error = json.getString("error");
	            Log.v("debug", error);
	            if("".equals(error) || error == null || "null".equals(error)){ 
	            	TextView emptySeat = (TextView) findViewById(R.id.emptyseat);
	            	TextView yourSeat = (TextView) findViewById(R.id.yourseat);
	            	emptySeat.setText(json.getString("emptySeat"));
	            	yourSeat.setText(json.getString("yourSeat"));
	            }
	        } catch (JSONException ex) {  
	        	ex.printStackTrace();
	        } 
	    }
	};
}
