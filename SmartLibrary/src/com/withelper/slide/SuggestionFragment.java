package com.withelper.slide;



import com.withelper.R;

import QRcode.QRcode;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class SuggestionFragment extends Fragment implements OnClickListener{
	private View p;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	p=inflater.inflate(R.layout.index_qrcode, container, false);
//    	Intent intent = new Intent(this.getActivity(),IndexQRcodeActivity.class);
//    	startActivity(intent);
    	ImageView QR = (ImageView) p.findViewById(R.id.index_QRcode);
		SharedPreferences preferences = this.getActivity().getSharedPreferences("index_qrcode",Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		String Sno = (String)preferences.getString("Sno", null);
		
		//Log.v("Sno",Sno);
		QR.setImageBitmap(QRcode.createQRImage(Sno));
		//Toast.makeText(this.getActivity().getApplicationContext(), "ss",Toast.LENGTH_SHORT).show();
    	return p;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	
}
