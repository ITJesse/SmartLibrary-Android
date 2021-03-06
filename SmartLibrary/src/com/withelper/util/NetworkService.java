package com.withelper.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class NetworkService {

	private static String TAG = "NetworkService";
	private static String salt = "withelper_itjesse";
	private static String base_url = "http://192.168.1.204:3000/API/Android/";
	
	
	public static void cancel() {
		Log.i(TAG, "cancel!");

	}

	public static String getPostResult(String url){			
		HttpPost post = new HttpPost(url);			

        BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams,10 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);

		HttpClient httpClient = new DefaultHttpClient(httpParams);
		try{

			HttpResponse response = httpClient.execute(post);

			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				String content = EntityUtils.toString(response.getEntity(),"utf-8");

				return URLDecoder.decode(content,"utf-8");
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "{\"error\":401,\"resultMsg\":\"网络超时！\"}";
		} finally {

			httpClient.getConnectionManager().shutdown();
		}
		return "{\"error\":402,\"resultMsg\":\"网络超时！\"}";		
	}

	public static String getPostResult(String url, List<NameValuePair> paramList){
		UrlEncodedFormEntity entity = null;
		String timestamp = String.valueOf(System.currentTimeMillis());
		String sign = salt + timestamp;

	    sign = ParseMD5.parseStrToMd5U32(sign);

		paramList.add(new BasicNameValuePair("timestamp",timestamp));
		paramList.add(new BasicNameValuePair("sign",sign));
		try {
			entity = new UrlEncodedFormEntity(paramList,"utf-8");
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		}			

		HttpPost post = new HttpPost(base_url + url);
		BasicHttpParams httpParams = new BasicHttpParams();			
		HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);
		post.setEntity(entity);

		HttpClient httpClient = new DefaultHttpClient(httpParams);
		try{

			HttpResponse response = httpClient.execute(post);

			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				String content = EntityUtils.toString(response.getEntity(),"utf-8");

				return URLDecoder.decode(content,"utf-8");
				//return timestamp;
			}else{
				return "{\"error\":403,\"resultMsg\":\"网络超时！\"}";
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "{\"error\":404,\"resultMsg\":\"网络超时！\"}";
		} finally {
			//閲婃斁缃戠粶杩炴帴璧勬簮
			httpClient.getConnectionManager().shutdown();
		}	
	}
}
