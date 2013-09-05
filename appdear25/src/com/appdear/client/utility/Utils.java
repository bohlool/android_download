/**
 * Utils.java
 * created at:2011-5-11����05:55:31
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client.utility;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.appdear.client.SoftWareDetailInfoActivity;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.db.LabellistDB;
import com.appdear.client.model.CatalogListInfo;
import com.appdear.client.service.Constants;
import com.appdear.client.service.api.ApiCatalogListResult;
import com.appdear.client.service.api.ApiManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

/**
 * ������
 * 
 * @author zqm
 */
public class Utils  {
	
	/** *//**
	    * ���ֽ�����ת����16�����ַ���
	    * @param bArray
	    * @return
	    */
	public static final String bytesToHexString(byte[] bArray,int length) {
	    StringBuffer sb = new StringBuffer(bArray.length);
	    String sTemp;
	    for (int i = 0; i < length; i++) {
	     sTemp = Integer.toHexString(0xFF & bArray[i]);
	     if (sTemp.length() < 2)
	      sb.append(0);
	     sb.append(sTemp.toUpperCase());
	    }
	    return sb.toString();
	}
	
	public static String byte2HexString(byte[] b,int length) {
        char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7',
                      '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] newChar = new char[length * 2];
        for(int i = 0; i <length; i++) {
            newChar[2 * i] = hex[(b[i] & 0xf0) >> 4];
            newChar[2 * i + 1] = hex[b[i] & 0xf];
        }
        return new String(newChar);
	}
	  
	public static String byte2HexString(byte[] b) {
        char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7',
                      '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] newChar = new char[b.length * 2];
        for(int i = 0; i <b.length; i++) {
            newChar[2 * i] = hex[(b[i] & 0xf0) >> 4];
            newChar[2 * i + 1] = hex[b[i] & 0xf];
        }
        return new String(newChar);
    }
	
	/*
	 * ��ȡ��ǰʱ��
	 */
	public static String nowTime() {
	    Calendar c = Calendar.getInstance();
	    c.setTimeInMillis(new Date().getTime());
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return dateFormat.format(c.getTime());
	}
	
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest messagedigest = null;
	static{
	   try{
	    messagedigest = MessageDigest.getInstance("MD5");
	   }catch(NoSuchAlgorithmException nsaex){
	    System.err.println(Utils.class.getName()+"��ʼ��ʧ�ܣ�MessageDigest��֧��MD5Util");
	    nsaex.printStackTrace();
	   }
	}

	/**
	* ��������G����ļ�
	* @param file
	* @return
	* @throws IOException
	*/
	public static String getFileMD5String(String  filedir) {
		return new File(filedir).length()+filedir;
	}
	
	/**
	 * MD5�㷨
	 */
	public final static String MD5encode(String s) {
        char hexDigits[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};
        try {
          byte[] strTemp = s.getBytes();
          MessageDigest mdTemp = MessageDigest.getInstance("MD5");
          mdTemp.update(strTemp);
          byte[] md = mdTemp.digest();
          int j = md.length;
          char str[] = new char[j * 2];
          int k = 0;
          for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
          }
          return new String(str);
        }
        catch (Exception e) {
          return null;
        }
	}
	
	public final static String MD5encode(byte[] obj){
		String str1="";
	    try {
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	            md5.update(obj);
	        byte[] byArray=md5.digest();
	        
	        StringBuilder sb=new StringBuilder(str1);
	        for (byte element: byArray )
	        {
	         sb.append(String.valueOf(element));
	        }
	        str1=sb.toString();
	       
	    } catch (NoSuchAlgorithmException e){
	        e.printStackTrace();
	    }
		return str1;
	}
	
	/**
     * ����md5�ַ���
     * @param value
     * @return
     */
 	public static String generateMD5(String value) {
 		try {
 			MessageDigest md = MessageDigest.getInstance("MD5");
 			byte[] bytes;
 			try {
 				bytes = value.getBytes("UTF-8");
 			} catch (UnsupportedEncodingException e1) {
 				bytes = value.getBytes();
 			}
 			StringBuilder result = new StringBuilder();
 			for (byte b : md.digest(bytes)) {
 				result.append(Integer.toHexString((b & 0xf0) >>> 4));
 				result.append(Integer.toHexString(b & 0x0f));
 			}
 			return result.toString();
 		} catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
 		}
 	}
 	
 	/**
	 * ��ȡ�����������String
	 * 
	 * @param content  ԭ����
	 * @param highlightString ��Ҫ������ʾ������
	 * @param color  ָ������ɫ
	 * @return  ����������String
	 */
	public static SpannableString getSpannableString(String content,
			String highlightString, int color) {
		SpannableString ss = new SpannableString(content);
		if ("".equals(highlightString) || highlightString == null) {
			return ss;
		}

		//���ؼ��ʰ�[�ո� �� , . ��]�ֿ��ɶ���ؼ���
		String[] StringParts = highlightString.split("[\\s,��.��]"); 
		for (String part : StringParts) {
			if ("".equals(part)) {
				continue;
			}
			
			int startIndex = 0;
			int retIndex = -1;  //ÿ��ѭ�����ص�����
			List<Integer> startIndexes = new ArrayList<Integer>();

			//content����ܻ���ֶ����Ҫ������ʾ�Ĺؼ���part��ѭ����ȡÿ���ؼ��ʵ����ַ���retIndex
			while (-1 != (retIndex = content.toLowerCase().indexOf(part.toLowerCase(), startIndex))) {
				startIndexes.add(retIndex); //������ֹؼ��ʣ���add����ֵ�λ��retIndex
				startIndex = part.length() + retIndex;
				if (startIndex > content.length()) {
					break; //����Ѿ���⵽���������ѭ��
				}
			}

			//�����йؼ������ø���
			for (Integer index : startIndexes) {
				ForegroundColorSpan fcs = new ForegroundColorSpan(color);
				ss.setSpan(fcs, index, index + part.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		
		return ss;
	}
	/**
	 * ����򵥻�ȡͼƬ
	 */
	
	public static Bitmap getImage(String Url) throws Exception {

		try {

			 URL url = new URL(Url);

			String responseCode = url.openConnection().getHeaderField(0);

			if (responseCode.indexOf("200") < 0)

				throw new Exception("ͼƬ�ļ������ڻ�·�����󣬴�����룺" + responseCode);


			return BitmapFactory.decodeStream(url.openStream());

		} catch (IOException e) {

			// TODO Auto-generated catch block

			throw new Exception(e.getMessage());

		}

	}


	/**
	 * ��ȡ�ضϵ��ַ������磺��˵������ķè  ---> ��˵������...��
	 * @param content  ԭʼ�ַ���
	 * @param length   ��ȡ�ĳ���
	 * @param endTag   ���ʡ�Ե������ݵ��ַ����磺...��
	 * @return         �ضϵ��ַ���
	 */
	public static String getOmitString(String content, int length, String endTag) {
		if (content != null && content.length() > length) {
			return content.substring(0, length - endTag.length()) + endTag;
		}else {
			return content;
		}
	}
	
	/**
	 * �������ű༭����
	 * @param context
	 * @param smsContent Ĭ�϶�������
	 */
	public static void gotoSmsEditPage(Context context, String smsContent) {
		try{
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.putExtra("sms_body", smsContent);
			intent.setType("vnd.android-dir/mms-sms");
			context.startActivity(intent);
		}catch (Exception e) {
			Toast.makeText(context, "����ʹ�ö��ŷ���", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * ���Ĭ�϶�������(�Ƽ����氮ƤӦ���������"Ӧ����ǰ6����"+���ӵ�ַ)
	 * @param softName
	 * @param detailUrl
	 * @return
	 */
	public static String getDefaultSmsContent(String softName, String detailUrl) {
		String SMS_CONTENT_A = "�Ƽ����氮ƤӦ��������ġ�";
		
		String omitSoftName = Utils.getOmitString(softName, 15, "..."); //��ȡ�������ǰ12���ַ�+...
		String completeSmsContent = SMS_CONTENT_A + omitSoftName + "����" + detailUrl;
		
		return completeSmsContent;
	}
   
 	public static void sleep(int nSecond) {
 		try {
			Thread.sleep(nSecond);
 		} catch(Exception e) {
 			e.printStackTrace ();
 		}
 	}
 	
	public static void log(String sMsg) {
		System.err.println(sMsg);
	}
	
	public static void log(int sMsg) {
		System.err.println(sMsg);
	}
	
	/**
	 * ���������Ϣ
	 * @param log
	 */
	public static void print(String log){
		if (Constants.DEBUG) {
			System.out.println(log);
		}
	}
	
	/**
	 * ��Ͳģ��--�����ݿ���������ȡ����list���ݣ������洦����ƣ�
	 * @param contex   �㶮��
	 * @param pageno   ��ǰҳ
	 * @param count    ��ǰҳ����
	 * @param catList  ��ȡ�������ݽ������list
	 * @param id       ��ĿID
	 * @param nowServerVersion  �������·��ĵ�ǰ�汾
	 * @param MAX_VIEW_COUNT  ��ȡ���ݵ��������
	 */
	public static ApiCatalogListResult getCatListFromServerOrDB(Context contex, String pageno, 
			String count, int id, String CAT_KEY, 
			String dbType, int nowServerVersion, int MAX_VIEW_COUNT) {
		
		ApiCatalogListResult catList = new ApiCatalogListResult();
		try {
			catList = new ApiCatalogListResult();
			
			//��ȡxml�ļ��ﱣ�����һ�α�ǩ�汾��
			int savedLabelVersion = SharedPreferencesControl.getInstance().getInt("labelversion", com.appdear.client.commctrls.Common.SETTINGS, contex);
			
			// �жϴ����ݿ⣨���棩���Ƿ�����ȡ����
			LabellistDB labelDB = new LabellistDB(contex);
			if (nowServerVersion == savedLabelVersion) {
				// ��� �������汾�� = �ϴα���汾�ţ���������ݿ�
				print("-----�ϴα���汾��=�������汾��-->��ѯ���棨���ݿ⣩�Ƿ�������-----");
				List<CatalogListInfo> list = labelDB.getLabellist(dbType);
				if (list != null && list.size() > 0) {
					// ������ݿ��У�������ݿ�ȡ
					print("-----���ݿ���-->ȡ���棨���ݿ⣩����-----");
					catList.catalogList = list;
				} else {
					// ���ݿ�û�У���ӷ�����ȡ�����������ݿ�
					print("-----���ݿ�û�л�������-->�ӷ�����ȡ-----");
					// ȡ����
					id = SharedPreferencesControl.getInstance().getInt(CAT_KEY,
							com.appdear.client.commctrls.Common.SECTIONCODEXML, contex);
					catList = ApiManager.labellist(id + "", pageno, count, "1");

					// ������
					print("���������ݴ������ݿ�");
					if (catList != null && catList.catalogList.size() > 0) {
						int saveCount = catList.catalogList.size() > MAX_VIEW_COUNT ? MAX_VIEW_COUNT
								: catList.catalogList.size();
						for (int i = 0; i < saveCount; i++) {
							CatalogListInfo info = catList.catalogList.get(i);
							labelDB.insertLabellist(info, dbType);
						}
					}

					// �˴����ø����ϴα���汾��
				}
			} else {
				// ���ѡ����� �������汾�� > �ϴα���汾�ţ���ֱ�Ӵӷ�����ȡ���Ƚ���ǰ���ݿ������������д�����ݿ⣬��������һ�α���汾��
				print("-----�������汾��  > �ϴα���汾�ţ�ֱ�Ӵӷ�����ȡ����-----");
				// ȡ����
				id = SharedPreferencesControl.getInstance().getInt(CAT_KEY,
						com.appdear.client.commctrls.Common.SECTIONCODEXML,
						contex);
				catList = ApiManager.labellist(id + "", pageno, count, "1");

				// ������
				if (catList != null && catList.catalogList.size() > 0) {
					// ������
					labelDB.deleteAll(dbType);

					// ������
					print("���������ݴ������ݿ�");
					for (CatalogListInfo info : catList.catalogList) {
						labelDB.insertLabellist(info, dbType);
					}
				}

				// ���������·��İ汾�ű�����xml�ļ���labelversion��
				print("��汾����xml�ļ�--labelversion = " + nowServerVersion);
				SharedPreferencesControl.getInstance().putInt("labelversion", nowServerVersion,
						com.appdear.client.commctrls.Common.SETTINGS, contex);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return catList;
	}
	
	/**
	 * --��Ͳģ��--
	 * �Ӷ�ά����dataArray��ȡ��name�������һά����names��
	 * @param dataArray  ��ά���飨Դ���ݣ�
	 * @param names  һλ���飨Ŀ�����ݣ�
	 * @return -1ʧ�ܣ�0�ɹ�
	 */
	public static  int getNameArray(String[][] dataArray, String[] names) {
		if (dataArray == null || names == null || names.length > dataArray.length) {
			return -1;
		}
		try {
			for (int i = 0; i < names.length; i++) {
				names[i] = dataArray[i][0]; //ȡ��ÿһ��name�����һλ����names��
			}
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * ���ݱ�ǩ�����Ҷ�Ӧ��id
	 * @param catName     ���ݴ�key��Ԫ�����в��Ҷ�Ӧ��id
	 * @param tvDataArray Դ����
	 * @return
	 */
	public static String getIdByName(String catName, String[][] tvDataArray) {
		if (catName == null) {
			return "-1";
		}
		for (int i = 0; i < tvDataArray.length; i++) {
			if (catName.equals(tvDataArray[i][0])) {
				return tvDataArray[i][1];  //���ض�Ӧ��id
			}
		}
		return "-1";
	}
}