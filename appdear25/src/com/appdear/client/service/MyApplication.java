/**
 * MyApplication.java
 * created at:2011-5-11����04:44:54
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client.service;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.appdear.client.commctrls.Common;
import com.appdear.client.commctrls.ListBaseActivity;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.utility.ServiceUtils;

import dalvik.system.VMRuntime;
  
/** 
 * ȫ��ʵ��
 * @author zqm 
 */
public class MyApplication extends Application {

	public static Map<String, View> mView = new HashMap<String, View>();
	public static void setInstance(MyApplication instance) {
		MyApplication.instance = instance;
	}
	public Activity  mainActivity=null;
	public ListBaseActivity  searchResultActivity=null;
	/**
	 * ȫ�ֿ�������Ӧ����ʾ״̬�ı�������
	 */
	 Map<Integer, Integer> softMap=new Hashtable<Integer, Integer>();
	 public String username163;
	 public String password163;
	 /**
	  * �û������װ��ť ��¼Ӧ��appid ��Ӧ�� softid��Ŀ����ͨ���㲥���������ص���װappid��ʹ˼��϶Աȣ��������˵���������ǰ�Ƥ���صģ���ȡ����Ӧ��softid
	  */
	 Map<String, Integer> AppMap=new HashMap<String, Integer>();
	 /**
	  * �û����ж�ذ�ťʱ�� ��¼Ӧ��appid��Ŀ����ͨ���㲥���������ص�ж��Ӧ�õ�appid��ʹ˼��϶Աȣ��������˵���������ǰ�Ƥж�صġ�
	  */
	 public  Map<String, String> appUninstalledMap=new HashMap<String, String>();
     //�����Ƴ���Ƥʱ��
	 public  long  exitTime=-1;
	 // androidϵͳ�汾��
	 public  int androidLevel;
	 // �������ͬ���Ƽ����˵���ǰ��ʾ���ļ���
	 public  List<Integer>    detailSoftidList= new ArrayList<Integer>();
	 // �����б���ʾ����  
	 public  int elideupdate;
	 
	 //����ר�� �ϴ���������
	 public String  modelCompany="";
	 public String  modelCompanyPhone="";
	 // �Ƿ���ع�����ʱ��
	 public boolean isLoadBuyTime=false;
	 
	/**
	 * ȫ��ʵ��
	 */
	private static MyApplication instance;
    
	public static MyApplication getInstance() {
		if(instance==null){
			if (Constants.DEBUG)
				Log.i("instance :", "instance");
		}else{
			 
		}
		return instance;
	}

	public Map<String, Integer> getAppMap() {
		return AppMap;
	}

	public void setAppMap(Map<String, Integer> appMap) {
		AppMap = appMap;
	}
	//public List<String> urlDetailList=new ArrayList<String>();
	//ȫ��ͼƬ����
	private Map<String, SoftReference<Bitmap>> imageCache ;
	//private Map<String,Boolean>  imageFalgCache;//recycled
	public static String serial="";
	@Override
	public void onCreate() {
		super.onCreate();
		if (Constants.DEBUG)
			Log.i("onCreate", "onCreate");
		instance = this;
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
		//SharedPreferencesControl.getInstance().getString("username",com.appdear.client.commctrls.Common.USERPASSWDXML,AlterUserRegActivity.this)
		AppContext.getInstance().IMEI=ServiceUtils.getIMEI(this);
		username163=SharedPreferencesControl.getInstance().getString("username",com.appdear.client.commctrls.Common.USERLOGIN_163_XML,this);
		password163=SharedPreferencesControl.getInstance().getString("password",com.appdear.client.commctrls.Common.USERLOGIN_163_XML,this);

		//����û���ǰ��¼��Ϣ
		SharedPreferencesControl.getInstance().clear(Common.USERLOGIN_XML,this);
		androidLevel = Build.VERSION.SDK_INT;	
		 Class<?> c=null;
			try {
				c = Class.forName("android.os.SystemProperties");
				 Method get=c.getMethod("get", String.class);
				
					serial = (String)get.invoke(c,"ro.serialno");
				
		     } catch (Exception e) {
				// TODO Auto-generated catch block
				
			 }   
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		//����û���¼��Ϣ
		
		super.onTerminate();
	}
	public Map<Integer, Integer> getSoftMap() {
		return softMap;
	}

	public void setSoftMap(Map<Integer, Integer> softMap) {
		this.softMap = softMap;
	}
	public Map<String, SoftReference<Bitmap>> getImageCache() {
		return imageCache;
	}
  /*  public boolean isRecycled(String url)
    {
    	if(imageFalgCache.containsKey(url))
    	{
    		return  true;
    	}else
    	{
    		return false;
    	}
      
    }*/
   /* public  void setRecycledFlag(String url,boolean flag)
    {
    	imageFalgCache.put(url,flag);
    }*/
	public Bitmap  getBitmapByUrl(String imageUrl)
	{
	 	VMRuntime.getRuntime().setMinimumHeapSize(10*1024*1024);
	/*	Collection<SoftReference<Bitmap>> collection=imageCache.values();
		int i=0;
		int count=0;
		for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
			
			SoftReference<Bitmap> softReference = (SoftReference<Bitmap>) iterator.next();
			if( softReference.get()==null)
			{
				count++;
			}
			 i++;
		} 
		System.out.println("i======"+i+";    count======"+count);*/
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
			Bitmap bitmap=softReference.get();
			if (bitmap != null) {
		 		//System.out.println("-----------softReference.get()---------------");
				return bitmap;
			} 
		}
		
		return null;
	}
	public void  setBitmapByUrl(String imageUrl,Bitmap bitmap)
	{
		imageCache.put(imageUrl, new SoftReference<Bitmap>(bitmap));
	}
}

 