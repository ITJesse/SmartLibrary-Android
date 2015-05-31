package com.withelper.slide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.withelper.R;
import com.withelper.util.NetworkService;


@SuppressLint("HandlerLeak")
public class IndexSearchAcitvity extends Activity {
    private ListView listview;
    private SimpleAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_search);	
		ImageView iv = (ImageView)findViewById(R.id.searchbtn);
		//initView();
		  listview = (ListView) this.findViewById(R.id.booklist);
		iv.setOnClickListener(new OnClickListener() {      
			@Override   public void onClick(View arg0) {    
				EditText searchbook = (EditText) findViewById(R.id.searchbook);
				search(searchbook.getText().toString());
			}
			
		});
		Bundle bundle = getIntent().getExtras();
		String searchKey = bundle.getString("searchKey");//读出数据 
		search(searchKey);
		
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	public void search(String searchKey){
		final List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("str",searchKey));
		paramList.add(new BasicNameValuePair("strSearchType", "title"));
		paramList.add(new BasicNameValuePair("match_flag", "any"));
		paramList.add(new BasicNameValuePair("doctype", "ALL"));
		new Thread(new Runnable(){  
            @Override  
            public void run() {
            	Message msg = new Message();
            	Bundle data = new Bundle();
            	String url = "LibraryBookList";
            	String resultData = NetworkService.getPostResult(url, paramList);
				data.putString("result", resultData);
				msg.setData(data);
				searchHandler.sendMessage(msg); 
            }
		}).start();
	}
	Handler searchHandler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String result = data.getString("result");
//	        Toast.makeText(getApplicationContext(), result,
//					Toast.LENGTH_SHORT).show();
	        try {  
	            JSONObject json = new JSONObject(result);
	            String error = json.getString("error");
	            Log.v("debug", error);
	            //Log.v("book",json.toString());
	            if("".equals(error) || error == null || "null".equals(error)){ 
	            	ListShow(json);   //显示ListView
	            }else{
	            	Toast.makeText(getApplicationContext(), "查询失败",
	        				Toast.LENGTH_SHORT).show();
	            }
	        } catch (JSONException ex) {  
	        	ex.printStackTrace();
	        } 
	    }
	};
	void ListShow(JSONObject json){
		JSONArray jsonA;
		try {
			jsonA = json.getJSONArray("book");
			int jsonlength = jsonA.length();
			List<HashMap<String,Object>> bklt = new ArrayList<HashMap<String,Object>>();

			for(int i = 0;i < jsonlength;i++ ){
	    		JSONObject jsontmp = jsonA.getJSONObject(i);
	    		
	    		if(i == 0){
	    			HashMap<String,Object> item = new HashMap<String,Object>();
	    			item.put("title", "书名");
	        		item.put("author", "作者");
	        		item.put("press", "出版社");
	        		bklt.add(item);
	    		}
	    		HashMap<String,Object> item = new HashMap<String,Object>();
	    		item.put("title", jsontmp.getString("title"));
	    		item.put("author", jsontmp.getString("author"));
	    		item.put("press", jsontmp.getString("press"));
	    		
	    		bklt.add(item);
	    		adapter = new SimpleAdapter(this,bklt,R.layout.index_search_booklist_item,
	    	  			new String[]{"title","author","press"},
	    	  			new int[]{R.id.bookname,R.id.author,R.id.press});
	    		
	    		//String bookName = jsontmp.getString("title");
	    		//String author = jsontmp.getString("author");
	    		//String press = jsontmp.getString("press");

	    		//Log.v("book",bookName);
	    		//Log.v("author",author);
	    		//Log.v("press",press);
	    	}
			listview.setAdapter(adapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
	}
	
}
