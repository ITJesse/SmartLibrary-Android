package com.withelper.slide;



import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.withelper.R;
import com.withelper.util.NetworkService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class BorrowFragment extends Fragment implements OnClickListener{
	private View p;
	private ListView listview;
    private SimpleAdapter adapter;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {     
    	
    	p=inflater.inflate(R.layout.myborrow, container, false);
//    	Intent intent = new Intent(getActivity(),IndexBorrowActivity.class);
//    	startActivity(intent);
    	listview = (ListView)p.findViewById(R.id.borrowlist);
		SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE); //私有数据
		String sessionid = sharedPreferences.getString("sessionid",null);
		borrowShow(sessionid);
    	return p;
    }

	public void borrowShow(String sessionid){
		final List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("sessionid",sessionid));
		new Thread(new Runnable(){  
            @Override  
            public void run() {
            	Message msg = new Message();
            	Bundle data = new Bundle();
            	String url = "LibraryBorrow";
            	String resultData = NetworkService.getPostResult(url, paramList);
				data.putString("result", resultData);
				msg.setData(data);
				borrowHandler.sendMessage(msg); 
            }
		}).start();
	}
	
    public void goLoginActivity()
    {
    	Intent intent = new Intent(getActivity(), LoginActivity.class);  
    	startActivity(intent);
    	getActivity().finish();
    }
	
	Handler borrowHandler = new Handler(){
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
	            if("-5".equals(error)){
	            	Toast.makeText(BorrowFragment.this.getActivity().getApplicationContext(), "登陆失效，请重新登陆",
	        				Toast.LENGTH_SHORT).show();
	            	goLoginActivity();
	            }
	            else if("".equals(error) || error == null || "null".equals(error)){ 
	            	borrowListShow(json);   //显示ListView
	            }
	            else{
	            	Toast.makeText(BorrowFragment.this.getActivity().getApplicationContext(), "查询失败",
	        				Toast.LENGTH_SHORT).show();
	            }
	        } catch (JSONException ex) {  
	        	ex.printStackTrace();
	        } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
	};
	void borrowListShow(JSONObject json) throws ParseException{
		JSONArray jsonA;
		try {
			jsonA = json.getJSONArray("borrow");
			int jsonlength = jsonA.length();
			List<HashMap<String,Object>> brlt = new ArrayList<HashMap<String,Object>>();
	    	for(int i = 0;i < jsonlength;i++ ){
	    		JSONObject jsontmp = jsonA.getJSONObject(i);
	    		
	    		if(i == 0){
	    			HashMap<String,Object> item = new HashMap<String,Object>();
	    			item.put("title", "书名");
		    		item.put("author", "作者");
		    		item.put("lendTime", "剩余天数");
		    		brlt.add(item);
	    		}
	    		HashMap<String,Object> item = new HashMap<String,Object>();
	    		item.put("title", jsontmp.getString("title"));
	    		item.put("author", jsontmp.getString("author"));
	    		
	    		String timetmp = jsontmp.getString("lendTime");
	    		Log.v("time",timetmp);
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");  
	    		Date old_date = (Date) sdf.parse(timetmp); 
                Log.v("date",old_date.toString());
                Calendar nowDate=Calendar.getInstance();
                Calendar oldDate=Calendar.getInstance();
                nowDate.setTime(new Date(System.currentTimeMillis()));
	    		oldDate.setTime(old_date);
	    		long timeNow=nowDate.getTimeInMillis();		
	    		long timeOld=oldDate.getTimeInMillis();
	    		long day =30 - (timeNow-timeOld)/(1000*60*60*24);
	    		if(day > 0)
	    			item.put("lendTime", day + "天");
	    		else
	    			item.put("lendTime", "超过" + -day + "天");
	    		brlt.add(item);
	    		adapter = new SimpleAdapter(this.getActivity(),brlt,R.layout.borrow_item,
	    	  			new String[]{"title","author","lendTime"},
	    	  			new int[]{R.id.bookname1,R.id.author1,R.id.returntime});
	    		listview.setAdapter(adapter);
	    		}
	    	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
 	}
	
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
    
    

}
