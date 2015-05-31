package QRcode;

import java.util.Hashtable;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.withelper.util.ParseMD5;

public class QRcode {
	
	
	 public static Bitmap createQRImage(String tmp)
	    {
	     ParseMD5 p = new ParseMD5();
	     String Sno = p.parseStrToMd5U32(tmp);
		 try
	        {
	        	 int QR_WIDTH = 300 ;
	        	 
	        	 int QR_HEIGHT = 300 ;
	        	//判断URL合法性
	            if (Sno == null || "".equals(Sno) || Sno.length() < 1)
	            {
	                return null;
	            }
	            //Log.v("s","1");
	            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
	            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	            hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.H);
	            
	            //图像数据转换，使用了矩阵转换
	            BitMatrix bitMatrix = new QRCodeWriter().encode(Sno, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
	            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
	            //下面这里按照二维码的算法，逐个生成二维码的图片，
	            //两个for循环是图片横列扫描的结果
	            for (int y = 0; y < QR_HEIGHT; y++)
	            {
	                for (int x = 0; x < QR_WIDTH; x++)
	                {
	                    if (bitMatrix.get(x, y))
	                    {
	                        pixels[y * QR_WIDTH + x] = 0xff000000;
	                    }
	                    else
	                    {
	                        pixels[y * QR_WIDTH + x] = 0xffffffff;
	                    }
	                }
	            }
	            //Log.v("s","2");
	            //生成二维码图片的格式，使用ARGB_8888
	            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
	            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
	            //Log.v("s","3");
	            //显示到一个ImageView上面
	            //sweepIV.setImageBitmap(bitmap);
	            return bitmap;
	        }
	        catch (WriterException e)
	        {
	            e.printStackTrace();
	            return null;
	        }
			
	    }
}
