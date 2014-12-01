package com.withelper.slide;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.withelper.R;

public class LearningFragment extends Fragment implements OnClickListener{
	private View p;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {     
    	
    	p=inflater.inflate(R.layout.learning, container, false);
    	
    	LinearLayout cllx = (LinearLayout)p. findViewById(R.id.cllx);
    	LinearLayout gcrlx = (LinearLayout)p. findViewById(R.id.gcrlx); 
    	cllx.setOnClickListener(this);
    	gcrlx.setOnClickListener(this);
    	return p;
    }

    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch(v.getId())
		{
		case R.id.cllx:
			//showRequestDialog();
			jump_cllx();
			break;
		case R.id.gcrlx:
			jump_gcrlx();
			break;
			default:
				break;
		}
	}
    
    public void jump_cllx(){
		Intent intent1 = new Intent(getActivity(),cllxAcitvity.class);
	     getActivity().startActivity(intent1);
    }
    
    public void jump_gcrlx(){
		Intent intent1 = new Intent(getActivity(),gcrlxAcitvity.class);
	     getActivity().startActivity(intent1);
    }
}
