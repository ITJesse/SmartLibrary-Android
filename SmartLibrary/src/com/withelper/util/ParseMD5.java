 /**  
 *@Description: ���ַ���ת��ΪMD5
 */ 
package com.withelper.util;  

import android.annotation.SuppressLint;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
  
@SuppressLint("DefaultLocale") public class ParseMD5 {

	/**
	 * @param str
	 * @return
	 * @Date: 2013-9-6  
	 * @Author: lulei  
	 * @Description:  32λСдMD5
	 */
	public static String parseStrToMd5L32(String str){
		String reStr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(str.getBytes());
			StringBuffer stringBuffer = new StringBuffer();
			for (byte b : bytes){
				int bt = b&0xff;
				if (bt < 16){
					stringBuffer.append(0);
				} 
				stringBuffer.append(Integer.toHexString(bt));
			}
			reStr = stringBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return reStr;
	}
	
	/**
	 * @param str
	 * @return
	 * @Date: 2013-9-6  
	 * @Author: lulei  
	 * @Description: 32λ��дMD5
	 */
	public static String parseStrToMd5U32(String str){
		String reStr = parseStrToMd5L32(str);
		if (reStr != null){
			reStr = reStr.toUpperCase();
		}
		return reStr;
	}
	
	/**
	 * @param str
	 * @return
	 * @Date: 2013-9-6  
	 * @Author: lulei  
	 * @Description: 16λСдMD5
	 */
	public static String parseStrToMd5U16(String str){
		String reStr = parseStrToMd5L32(str);
		if (reStr != null){
			reStr = reStr.toUpperCase().substring(8, 24);
		}
		return reStr;
	}
	
	/**
	 * @param str
	 * @return
	 * @Date: 2013-9-6  
	 * @Author: lulei  
	 * @Description: 16λ��дMD5
	 */
	public static String parseStrToMd5L16(String str){
		String reStr = parseStrToMd5L32(str);
		if (reStr != null){
			reStr = reStr.substring(8, 24);
		}
		return reStr;
	}
}