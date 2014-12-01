package com.withelper.slide;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.withelper.R;

public class IndexSearchAcitvity extends Activity {
    private View parentView;
    private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_search);	
		//initView();	
	}
}
