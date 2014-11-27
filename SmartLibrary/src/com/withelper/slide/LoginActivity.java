package com.withelper.slide;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.withelper.R;
import com.withelper.util.MyDialog;
import com.withelper.util.NetworkService;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.content.ComponentName;
import android.content.Context;  
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;

public class LoginActivity extends Activity implements OnClickListener{

	//private String ipAddress = "192.168.1.238";
	private Button mBtnRegister;
	private String resultData;
	String name="";
	String password="";
	String phone="";
	String str ="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);	
		initView();	
	}
	
	public void initView()
	{
		TextView t1 = (TextView) findViewById(R.id.forget_Psw);
		TextView t2 = (TextView) findViewById(R.id.registerID);
		
		t1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
		t2.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
		
		EditText l_name=(EditText)findViewById(R.id.l_name);
		EditText l_psw=(EditText)findViewById(R.id.l_password);
		l_name.setText("cooelf");
		t1.setOnClickListener(this);
		t2.setOnClickListener(this);
		
		mBtnRegister = (Button) findViewById(R.id.login_btn);
		mBtnRegister.setOnClickListener(this);
		
	}
	



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch(v.getId())
		{
		case R.id.login_btn:
			//showRequestDialog();
			Login();
			break;
		case R.id.forget_Psw:
			ResetAccount();
			break;
		case R.id.registerID:
			JugeRegister();
			break;
			default:
				break;
		}
	}
	
	void Login(){
		
		EditText l_name=(EditText)findViewById(R.id.l_name);
		EditText l_psw=(EditText)findViewById(R.id.l_password);
		String name=l_name.getText().toString();
		String pass=l_psw.getText().toString();
		SharedPreferences sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE); //私有数据
		Editor editor = sharedPreferences.edit();
		String t1=l_name.getText().toString();
		String t2=l_psw.getText().toString();
		editor.putString("name",t1 );
		editor.putString("age", t2);
		editor.commit();//提交修改
if(name.equals("cooelf")&&pass.equals("123")){
	Intent intent = new Intent();
	intent.setClass(this, MenuActivity.class);
	startActivity(intent);
	this.finish();
}else{
	Toast.makeText(getApplicationContext(), "密码错误",
		     Toast.LENGTH_SHORT).show();
}
    	
	}
	//重置账户
	void ResetAccount(){
		 MyDialog.Builder myDialog = new MyDialog.Builder(LoginActivity.this);  
		    myDialog.setTitle("重置账户"); 
		   
		    myDialog.setPositiveButton("确定", new DialogInterface.OnClickListener()  
		    {  
		        @Override  
		        public void onClick(DialogInterface dialog, int which){
		        	EditText phonenum=(EditText) findViewById(R.id.r_phonenum);
		        	EditText name_edit =(EditText) findViewById(R.id.l_name);
		    		EditText password_edit =(EditText) findViewById(R.id.l_password);
		        	
				    String reset_name=name_edit.getText().toString();      
				    String reset_password=password_edit.getText().toString();	
		    		String reset_phone=phonenum.getText().toString();
				    
		        	ResetID(reset_name,reset_password,reset_phone);
		        	

		        }  
		    }
		    );  
		    myDialog.setNegativeButton("取消", new DialogInterface.OnClickListener()  
		    {  
		        @Override  
		        public void onClick(DialogInterface dialog, int which){}  
		    });  
		    myDialog.create().show();  

	}

	void ResetID(String reset_name,String reset_psw,String reset_phone){
		
	}
	

	void JugeRegister(){
		 MyDialog.Builder myDialog = new MyDialog.Builder(LoginActivity.this);  
		    myDialog.setTitle("注册账户");   

		    myDialog.setPositiveButton("确定", new DialogInterface.OnClickListener()  
		    {  
		        @Override  
		        public void onClick(DialogInterface dialog, int which){
		        	EditText name_edit =(EditText) findViewById(R.id.l_name);
		    		EditText password_edit =(EditText) findViewById(R.id.l_password);
		        	
				    name=name_edit.getText().toString();      
				    password=password_edit.getText().toString();	


		        }  
		    }
		    );  
		    myDialog.setNegativeButton("取消", new DialogInterface.OnClickListener()  
		    {  
		        @Override  
		        public void onClick(DialogInterface dialog, int which){}  
		    });  
		    myDialog.create().show();  

	}
	void Register(){
		RegisterAsyncTask DeviceRegister = new RegisterAsyncTask(this);
		DeviceRegister.execute("");	
	}
	
	
	class RegisterAsyncTask extends AsyncTask<String, Integer, String> {
		Context myContext;
		public RegisterAsyncTask(Context context) {
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
		}
	   protected String InitData() {
    
		   
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			String url = "http://www.withelper.com/API/Android/Login";;
			paramList.add(new BasicNameValuePair("userID",name));
			paramList.add(new BasicNameValuePair("password",password));
			
			str = NetworkService.getPostResult(url, paramList);
			Log.i("msg", str);
			
			return str;
		}
	
		protected void onPostExecute(String result) {

			try {
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject responseobj = (JSONObject) jsonParser.nextValue(); 
				if("failed".equals(responseobj.getString("errorMsg")))
				{
				}
				else if("success".equals(responseobj.getString("errorMsg"))){

					SharedPreferences pref = getApplicationContext().getSharedPreferences("userinfo",MODE_PRIVATE);
					pref.edit().putString("user_name",name).commit();
				}
				else {
				}
			} catch (Exception e) {
			}
	}	
	}

	
}
