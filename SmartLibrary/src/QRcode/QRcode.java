package QRcode;

import java.util.Hashtable;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRcode {
	
	
	 public static Bitmap createQRImage(String Sno)
	    {
	        try
	        {
	        	 int QR_WIDTH = 300 ;
	        	 
	        	 int QR_HEIGHT = 300 ;
	        	//�ж�URL�Ϸ���
	            if (Sno == null || "".equals(Sno) || Sno.length() < 1)
	            {
	                return null;
	            }
	            Log.v("s","1");
	            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
	            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	            //ͼ������ת����ʹ���˾���ת��
	            BitMatrix bitMatrix = new QRCodeWriter().encode(Sno, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
	            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
	            //�������ﰴ�ն�ά����㷨��������ɶ�ά���ͼƬ��
	            //����forѭ����ͼƬ����ɨ��Ľ��
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
	            Log.v("s","2");
	            //���ɶ�ά��ͼƬ�ĸ�ʽ��ʹ��ARGB_8888
	            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
	            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
	            Log.v("s","3");
	            //��ʾ��һ��ImageView����
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
