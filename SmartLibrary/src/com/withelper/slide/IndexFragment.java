package com.withelper.slide;



import com.withelper.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class IndexFragment extends Fragment implements OnClickListener  {
    private View p;
  
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {     
    	p=inflater.inflate(R.layout.index, container, false);
    	
   	    ImageView t1 = (ImageView) p.findViewById(R.id.index_brief);
	   	ImageView t2 = (ImageView) p.findViewById(R.id.index_act);
	   	ImageView t3 = (ImageView) p.findViewById(R.id.index_navi);
	   	ImageView t4 = (ImageView) p.findViewById(R.id.index_book);
	   	ImageView t5 = (ImageView) p.findViewById(R.id.index_search);
	   	
	   	t1.setOnClickListener(this);
	   	t2.setOnClickListener(this);
	   	t3.setOnClickListener(this);
	   	t4.setOnClickListener(this);
	   	t5.setOnClickListener(this);
    	 //initView();
    	return p; 
    }
    
/*    private void initView(){

 	Intent intent = new Intent(getActivity(),WelcomeActivity.class);
     getActivity().startActivity(intent);
    	
    }
*/
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.index_brief:
			Intent intent1 = new Intent(getActivity(),IndexBriefAcitvity.class);
		     getActivity().startActivity(intent1);
			break;
		case R.id.index_act:
			Intent intent2 = new Intent(getActivity(),IndexActAcitvity.class);
		     getActivity().startActivity(intent2);
			break;
		case R.id.index_navi:
			Intent intent3 = new Intent(getActivity(),IndexNaviAcitvity.class);
		     getActivity().startActivity(intent3);
			break;
		case R.id.index_book:
			Intent intent4 = new Intent(getActivity(),IndexHotBookAcitvity.class);
		     getActivity().startActivity(intent4);
			break;
		case R.id.index_search:
			Intent intent5 = new Intent(getActivity(),IndexSearchAcitvity.class);
		     getActivity().startActivity(intent5);
			break;
			default:
				break;
		}
		
/*		Intent intent = new Intent(getActivity(),WelcomeActivity.class);
	     getActivity().startActivity(intent);*/
	}
   
}
