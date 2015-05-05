package com.withelper.slide;



import com.withelper.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SettingFragment extends Fragment implements OnClickListener{
	private View p;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     p= inflater.inflate(R.layout.settings, container, false);
     TextView t1 = (TextView)p.findViewById(R.id.about);
     //打开关于界面
     t1.setOnClickListener(new View.OnClickListener() {  
    	       
    	     @Override  
    	     public void onClick(View v) {  
    	         // TODO Auto-generated method stub  
    	    	 Intent intent = new Intent(getActivity(),AboutAcitvity.class);
    		      	getActivity().startActivity(intent); 
    	    }  
    	 });  

     Button bt = (Button)p.findViewById(R.id.logout);   
     //注销
     bt.setOnClickListener(new View.OnClickListener() {  
	       
	     @Override  
	     public void onClick(View v) {  
	         // TODO Auto-generated method stub  
	    	 Intent intent1 = new Intent(getActivity(),LoginActivity.class);
				SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login",Context.MODE_PRIVATE); //私有数据
	    		Editor editor = sharedPreferences.edit();
	    		editor.putString("isCheck","N" );
	    		editor.commit();//提交修改
	     	   getActivity().startActivity(intent1); 
	     	   getActivity().finish();
	    }  
	 });  
     return p;
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
   
}
