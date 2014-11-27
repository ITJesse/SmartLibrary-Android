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

import com.withelper.R;
import com.withelper.adapter.HotBookInfo;
import com.withelper.adapter.HotBookListAdapter;
import com.withelper.pullrefresh.PullToRefreshView;
import com.withelper.pullrefresh.PullToRefreshView.OnFooterRefreshListener;
import com.withelper.pullrefresh.PullToRefreshView.OnHeaderRefreshListener;
import com.withelper.util.NetworkService;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class IndexHotBookAcitvity extends Activity {
	private Handler mHandler;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_book);    

        
    }
    

}