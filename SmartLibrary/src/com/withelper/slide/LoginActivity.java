package com.withelper.slide;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.withelper.R;
import com.withelper.util.NetworkService;

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
		
		t1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
		
		EditText l_name=(EditText)findViewById(R.id.l_studentId);
		EditText l_psw=(EditText)findViewById(R.id.l_password);
//		l_name.setText("cooelf");
		l_name.setText("1203020333");
		l_psw.setText("zyb940708");
		t1.setOnClickListener(this);
		
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
//		case R.id.registerID:
//			JugeRegister();
//			break;
			default:
				break;
		}
	}
	
	void Login(){
		
		EditText l_name=(EditText)findViewById(R.id.l_studentId);
		EditText l_psw=(EditText)findViewById(R.id.l_password);
		String name=l_name.getText().toString();
		String pass=l_psw.getText().toString();
		SharedPreferences sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE); //私有数据
		Editor editor = sharedPreferences.edit();
		editor.putString("name",name );
		editor.putString("age", pass);
		editor.commit();//提交修改

		final List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("userID",name));
		paramList.add(new BasicNameValuePair("password", pass));
		Toast.makeText(getApplicationContext(), "正在登陆",
				Toast.LENGTH_SHORT).show();
		new Thread(new Runnable(){  
            @Override  
            public void run() {
            	Message msg = new Message();
            	Bundle data = new Bundle();
            	String url = "Login";
				str = NetworkService.getPostResult(url, paramList);
				data.putString("result", str);
				msg.setData(data);
				LoginHandler.sendMessage(msg); 
            }
		}).start();
	}

	Handler LoginHandler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String result = data.getString("result");
	        Toast.makeText(getApplicationContext(), result,
					Toast.LENGTH_SHORT).show();
	        try {  
	            JSONObject json = new JSONObject(result);
	            String error = json.getString("error");
	            Log.v("debug", error);
	            if("".equals(error) || error == null || "null".equals(error)){ 
	            	String sessionid = json.getString("sessionid");
	            	Toast.makeText(getApplicationContext(), "登陆成功",
	    					Toast.LENGTH_SHORT).show();
	            	Intent intent = new Intent();
	    			intent.setClass(LoginActivity.this, MenuActivity.class);
	    			startActivity(intent);
	    			LoginActivity.this.finish();
	            }else{
	            	Toast.makeText(getApplicationContext(), "登陆失败",
	        				Toast.LENGTH_SHORT).show();
	            }
	        } catch (JSONException ex) {  
	        	ex.printStackTrace();
	        } 
	    }
	};
	
	//重置账户
	void ResetAccount(){ 
		LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);  
        final View view = inflater.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder myDialog = new AlertDialog.Builder(LoginActivity.this);
        myDialog.setView(view);
	    myDialog.setPositiveButton("确定", new DialogInterface.OnClickListener()  
	    {  
	        @Override  
	        public void onClick(DialogInterface dialog, int which){
	        	EditText studentId =(EditText) view.findViewById(R.id.r_studentId);
	        	EditText name =(EditText) view.findViewById(R.id.r_name);
	    		EditText idcard =(EditText) view.findViewById(R.id.r_idcard);
	    		
	    		studentId.setText("1203020333");
	    		name.setText("朱艺博");
	    		idcard.setText("420106199407088415");
	        	
			    String r_studentId = studentId.getText().toString();      
			    String r_name = name.getText().toString();	
	    		String r_idcard = idcard.getText().toString();
			    
	    		if(r_studentId.length() == 0 || r_name.length() == 0 || r_idcard.length() == 0)
	    		{
	    			Toast.makeText(getApplicationContext(), "信息有误",
	    					Toast.LENGTH_SHORT).show();
	    		}else{
	    			GetPass(r_studentId, r_name, r_idcard);
	    		}
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

	void GetPass(String studentId,String name,String idcard){
		final List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("userID",studentId));
		paramList.add(new BasicNameValuePair("name",name));
		paramList.add(new BasicNameValuePair("idcard",idcard));
		Toast.makeText(getApplicationContext(), "正在查询请稍后",
				Toast.LENGTH_SHORT).show();
		new Thread(new Runnable(){  
            @Override  
            public void run() {
            	String url = "ForgetPass";
            	Message msg = new Message();
            	Bundle data = new Bundle();
				str = NetworkService.getPostResult(url, paramList);
				data.putString("result", str);
				msg.setData(data);
				GetPassHandler.sendMessage(msg); 
            }
		}).start();
	}
	
	Handler GetPassHandler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String result = data.getString("result");
	        Toast.makeText(getApplicationContext(), result,
					Toast.LENGTH_SHORT).show();
	        try {  
	        	JSONObject json = new JSONObject(result);
	            String error = json.getString("error");
	            Log.v("debug", error);
	            if("".equals(error) || error == null || "null".equals(error)){ 
	            	String password = json.getString("password");
		            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
		            builder.setTitle("找回密码");
		            builder.setMessage("你的密码是：" + password);
		            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()  
		    	    {  
		    	        @Override  
		    	        public void onClick(DialogInterface dialog, int which){}  
		    	    });  
		            builder.create().show(); 
	            }else{
	            	Toast.makeText(getApplicationContext(), "信息有误",
	        				Toast.LENGTH_SHORT).show();
	            }
	        } catch (JSONException ex) {  
	            // 异常处理代码  
	        } 
	    }
	};
	

//	void JugeRegister(){
//		 MyDialog.Builder myDialog = new MyDialog.Builder(LoginActivity.this);  
//		    myDialog.setTitle("注册账户");   
//
//		    myDialog.setPositiveButton("确定", new DialogInterface.OnClickListener()  
//		    {  
//		        @Override  
//		        public void onClick(DialogInterface dialog, int which){
//		        	EditText name_edit =(EditText) findViewById(R.id.l_name);
//		    		EditText password_edit =(EditText) findViewById(R.id.l_password);
//		        	
//				    name=name_edit.getText().toString();      
//				    password=password_edit.getText().toString();	
//
//
//		        }  
//		    }
//		    );  
//		    myDialog.setNegativeButton("取消", new DialogInterface.OnClickListener()  
//		    {  
//		        @Override  
//		        public void onClick(DialogInterface dialog, int which){}  
//		    });  
//		    myDialog.create().show();  
//
//	}
//	void Register(){
//		RegisterAsyncTask DeviceRegister = new RegisterAsyncTask(this);
//		DeviceRegister.execute("");	
//	}
//	
//	
//	class RegisterAsyncTask extends AsyncTask<String, Integer, String> {
//		Context myContext;
//		public RegisterAsyncTask(Context context) {
//			myContext = context;
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			try {
//				resultData = InitData();
//				Thread.sleep(1000); 
//			} catch (Exception e) {
//			}
//			return resultData;
//		}
//		@Override
//		protected void onPreExecute() {
//		}
//	   protected String InitData() {
//    
//		   
//			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//			String url = "http://www.withelper.com/API/Android/Login";;
//			paramList.add(new BasicNameValuePair("userID",name));
//			paramList.add(new BasicNameValuePair("password",password));
//			
//			str = NetworkService.getPostResult(url, paramList);
//			Log.i("msg", str);
//			
//			return str;
//		}
//	
//		protected void onPostExecute(String result) {
//
//			try {
//				JSONTokener jsonParser = new JSONTokener(result);
//				JSONObject responseobj = (JSONObject) jsonParser.nextValue(); 
//				if("failed".equals(responseobj.getString("errorMsg")))
//				{
//				}
//				else if("success".equals(responseobj.getString("errorMsg"))){
//
//					SharedPreferences pref = getApplicationContext().getSharedPreferences("userinfo",MODE_PRIVATE);
//					pref.edit().putString("user_name",name).commit();
//				}
//				else {
//				}
//			} catch (Exception e) {
//			}
//		}	
//	}
//
//	
}
