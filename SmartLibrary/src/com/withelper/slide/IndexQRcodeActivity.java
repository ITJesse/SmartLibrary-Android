package com.withelper.slide;

import com.withelper.R;

import QRcode.QRcode;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class IndexQRcodeActivity extends Activity{
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_qrcode);	
		initView();	
	}
	
	public void initView()
	{

		SharedPreferences preferences = getSharedPreferences("index_qrcode", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = preferences.edit();
		String Sno = (String)preferences.getString("Sno", null);
		ImageView QR = (ImageView) findViewById(R.id.index_QRcode);
		//Log.v("Sno",Sno);
		QR.setImageBitmap(QRcode.createQRImage(Sno));
		
	}
}
