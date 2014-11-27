package com.withelper.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
			return "{\"error\":405,\"resultMsg\":\"网络超时！\"}";
		} finally {

			httpClient.getConnectionManager().shutdown();
		}
		return "{\"error\":405,\"resultMsg\":\"网络超时！\"}";		
	}

	public static String getPostResult(String url, List<NameValuePair> paramList){
		UrlEncodedFormEntity entity = null;
		String timestamp = String.valueOf(System.currentTimeMillis());
		String sign = salt + timestamp;
		byte[] hash;
	    try {
	    	hash = MessageDigest.getInstance("MD5").digest(sign.getBytes());;
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Huh, MD5 should be supported?", e);
	    }
	    StringBuilder hex = new StringBuilder(hash.length * 2);
	    for (byte b : hash) {
	        if ((b & 0xFF) < 0x10) hex.append("0");
	        hex.append(Integer.toHexString(b & 0xFF));
	    }
	    sign = hex.toString();
		paramList.add(new BasicNameValuePair("timestamp",timestamp));
		paramList.add(new BasicNameValuePair("sign",sign));
		try {
			entity = new UrlEncodedFormEntity(paramList,"utf-8");
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		}			

		HttpPost post = new HttpPost(url);
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
			}				
		}catch(Exception e) {
			e.printStackTrace();
			return "{\"error\":405,\"resultMsg\":\"网络超时！\"}";
		} finally {
			//閲婃斁缃戠粶杩炴帴璧勬簮
			httpClient.getConnectionManager().shutdown();
		}
		return "{\"error\":405,\"resultMsg\":\"网络超时！\"}";		
	}
}
