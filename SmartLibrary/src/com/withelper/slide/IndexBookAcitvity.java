package com.withelper.slide;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.withelper.R;
import com.withelper.adapter.HotBookInfo;
import com.withelper.adapter.HotBookListAdapter;
import com.withelper.pullrefresh.PullToRefreshView;
import com.withelper.pullrefresh.PullToRefreshView.OnFooterRefreshListener;
import com.withelper.pullrefresh.PullToRefreshView.OnHeaderRefreshListener;
import com.withelper.util.NetworkService;

public class IndexBookAcitvity extends Activity implements OnHeaderRefreshListener,OnFooterRefreshListener {
private ListView lv_hotbooklist;
	
	private  PullToRefreshView  mPullToRefreshView; //下拉刷新控件
	
	private ArrayList<HotBookInfo> hotbooklist;
	
	private String resultData;
	
	private HotBookListAdapter sla;
	
    private int loaddatacount = 30; //每次获取条数

	private boolean mIsStart = false; //true表示刷新，false表示加载
	
	private List<NameValuePair> p_paramList;   //访问参数
	
	private SimpleDateFormat mDateFormat;
	
	View cv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
        setContentView(R.layout.hotbooklist);   
		
		
		mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        mDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
       
        mPullToRefreshView = (PullToRefreshView)findViewById(R.id.pull);
        
        hotbooklist = new ArrayList<HotBookInfo>();
        lv_hotbooklist= (ListView)findViewById(R.id.hotbooklist);
		sla = new HotBookListAdapter(this,0,hotbooklist);
		lv_hotbooklist.setAdapter(sla);
        
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.onHeaderRefreshComplete();

        
        GetHotBookList();
		
	}
	
	protected void GetHotBookList() {

//		String classNum = "";
		
		// 创建HttpParams以用来设置HTTP参数
		p_paramList= new ArrayList<NameValuePair>();
//		p_paramList.add(new BasicNameValuePair("classNum",classNum));

		GetHotBookListAsyncTask myTask = new GetHotBookListAsyncTask(this);
		myTask.execute("");
	}

	class GetHotBookListAsyncTask extends AsyncTask<String, Integer, String> {

		Context myContext;

		public GetHotBookListAsyncTask(Context context) {
			myContext = context;
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				resultData = InitData();
				Thread.sleep(1000); 
			} catch (Exception e) {
				
			}
			return resultData;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			try 
			{
				JSONObject json = new JSONObject(result);
	            String error = json.getString("error");
	            if("".equals(error) || error == null || "null".equals(error))
				{
					JSONArray newlist = json.getJSONArray("hot");
					int length = newlist.length();
					ArrayList<HotBookInfo> hotbookenewlist = new ArrayList<HotBookInfo>();
					if (length > 0) {
						for(int i = 0; i < length; i++){//遍历JSONArray
							JSONObject jo = newlist.getJSONObject(i);
							HotBookInfo pi = new HotBookInfo();
    						pi.setName(jo.getString("name"));
    						pi.setAuthor(jo.getString("author"));
    						pi.setPress(jo.getString("press"));
    						pi.setCallno(jo.getString("callno"));
    						pi.setCollections(jo.getString("collections"));
    						pi.setLend_count(jo.getString("lend_count"));
    						pi.setLend_ratio(jo.getString("lend_ratio"));
    						
    						hotbookenewlist.add(0,pi);
						}
						if(hotbookenewlist.size() > 0)
							hotbooklist.addAll(hotbookenewlist);
						sla.notifyDataSetChanged();
					}else{
						Toast.makeText(getApplicationContext(), "系统错误",
		        				Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "系统错误",
	        				Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
			}
			closeHeaderOrFooter(true);
		}

		// 获取订单列表数据
		protected String InitData() {
			String str ="";
			String url = "LibraryHot";
			str = NetworkService.getPostResult(url, p_paramList);
			Log.i("msg", str);
			return str;
		}
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mIsStart = false;
				GetHotBookList();
			}
		}, 500);
		
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mIsStart = true;
				hotbooklist.clear();
				GetHotBookList();
			}
		},500);
		
	} 
	
	private void closeHeaderOrFooter(boolean flag)
	{
		String updateinfo = "";
		if(flag)
			updateinfo = getLastUpdateTime();
		if (mIsStart)
			mPullToRefreshView.onHeaderRefreshComplete("最后更新时间：" + updateinfo);
		else
			mPullToRefreshView.onFooterRefreshComplete();
	}
	
	private String getLastUpdateTime() {
        return formatDateTime(System.currentTimeMillis());
    }
	
	private String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }	        
        return mDateFormat.format(new Date(time));
    }
	

}