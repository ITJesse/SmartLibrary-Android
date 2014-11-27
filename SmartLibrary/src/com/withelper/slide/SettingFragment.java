package com.withelper.slide;



import com.withelper.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SettingFragment extends Fragment implements OnClickListener{
	private View p;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     p= inflater.inflate(R.layout.settings, container, false);
     TextView t1 = (TextView)p.findViewById(R.id.about);
     t1.setOnClickListener(this);
     return p;
    }
    @Override
	public void onClick(View v){
    	Intent intent1 = new Intent(getActivity(),AboutAcitvity.class);
	     getActivity().startActivity(intent1);
    }
}
