package com.withelper.slide;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.withelper.R;
import com.withelper.util.NetworkService;

public class LearningFragment extends Fragment implements OnClickListener {
	private View p;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		p = inflater.inflate(R.layout.learn, container, false);
		// Intent intent = new
		// Intent(this.getActivity(),IndexLearnActivity.class);
		// startActivity(intent);
		refresh();
		Button btn = (Button) p.findViewById(R.id.refresh);
		// 绑定匿名的监听器，并执行您所要在点击按钮后执行的逻辑代码
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refresh();
			}
		});
		return p;
	}

	public void refresh() {
		SharedPreferences sharedPreferences = this.getActivity()
				.getSharedPreferences("login", Context.MODE_PRIVATE); // 私有数据
		// Editor editor = sharedPreferences.edit();
		String sessionid = sharedPreferences.getString("sessionid", null);
		Log.v("sessionid", sessionid);
		final List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("sessionid", sessionid));
		Toast.makeText(
				LearningFragment.this.getActivity().getApplicationContext(),
				"正在刷新", Toast.LENGTH_SHORT).show();
		new Thread(new Runnable() {
			@Override
			public void run() {

				Message msg = new Message();
				Bundle data = new Bundle();
				String url = "StudyRoomSeat";
				String resultData = NetworkService
						.getPostResult(url, paramList);
				data.putString("result", resultData);
				msg.setData(data);
				learnHandler.sendMessage(msg);
			}
		}).start();

	}

	Handler learnHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String result = data.getString("result");
			try {
				JSONObject json = new JSONObject(result);
				String error = json.getString("error");
				Log.v("debug", error);
				if ("".equals(error) || error == null || "null".equals(error)) {
					TextView emptySeat = (TextView) p
							.findViewById(R.id.emptyseat);
					TextView yourSeat = (TextView) p
							.findViewById(R.id.yourseat);
					Toast.makeText(
							LearningFragment.this.getActivity()
									.getApplicationContext(), "刷新成功",
							Toast.LENGTH_SHORT).show();
					emptySeat.setText(json.getString("emptySeat"));
					String yourseat = json.getString("yourSeat");
					if (yourseat.equals("null")) {
						yourSeat.setText("您还未预约座位");
					} else {
						yourSeat.setText(json.getString("yourSeat"));
					}
				}
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
		}
	};

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
