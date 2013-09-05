/**
 * Appcontext.java
 * created at:2011-5-11����04:36:36
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

import com.appdear.client.download.DownloadListener;
import com.appdear.client.download.FileDownloader;
import com.appdear.client.download.SiteInfoBean;
import com.appdear.client.model.InitModel;
import com.appdear.client.model.PackageinstalledInfo;
import com.appdear.client.model.Updateinfo;
import com.appdear.client.model.UserInfo;
import com.appdear.client.service.api.ApiUserResult;
import com.appdear.client.utility.ServiceUtils;

/** 
 * ��ǰ��¼�������Ϣ
 * @author zqm
 */
public class AppContext {
	private static AppContext instance = null;
	public Context appContext = MyApplication.getInstance();
	/**
	 * ���浱ǰ��¼���û�
	 */
	private AppContext(){
		
	}
	public boolean isFirstInit=false;
	public UserInfo mCurrentUser;
	
	/**
	 * ���������ʵ�ַ
	 */
//	public String api_url = "http://192.168.100.103:10010/ap/android/";
//	public String api_url = "http://172.16.16.72:10010/ap/android/";
	//public String api_url = "http://android.appdear.com/ap_proxy1/androidtest/";
//	public String api_url = "http://tsa.appdear.com/ap/android/";
	public String api_url = "http://sa.appdear.com/ap/android/";
//	public String api_url = "http://172.16.16.82:10010/ap/android/";
//	public String api_url = "http://ntsa.appdear.com/ap/android/";
//	public String api_url = "http://a.appdear.com/ap/android/";
	public String spreurl = "";
	public String dpreurl = "";
	public  boolean restart=false;
	public boolean exitflag=true;
	
	public InitModel initModel=null;
	/**
	 * ��Դ���ص�ַ
	 */
	//public String res_url = "http://content.appdear.com/";
	
	/**
	 * �������ҳ�������url��WapЭ�飩
	 */
	public String wap_url = "http://m.appdear.com/";
	
	/**
	 * header--IMEI
	 */
	public String IMEI = ServiceUtils.getIMEI(appContext);
	
	/**
	 * header--IMEI
	 */
	public String MAC = "";

	/**
	 * ���ع���
	 */
	public FileDownloader downloader = new FileDownloader(appContext);

	/**
	 * �̶߳���
	 */
	public Hashtable<Integer,SiteInfoBean> taskList = new Hashtable<Integer,SiteInfoBean>();
	/**
	 * �̶߳���
	 */
	public List<String> taskSoftList = new ArrayList<String>();
	
	/**
	 * ���ؽ��ȼ���
	 */
	public DownloadListener downloadlistener = null;
	
	/**
	 * ���½��ȼ���
	 */
	public DownloadListener updatDownloadListener = null;
	
	/**
	 * �Ѱ�װ�����б�
	 */
	public Hashtable<String,PackageinstalledInfo> installlists = new Hashtable<String,PackageinstalledInfo>();
	
	/**
	 * ϵͳ�����װ�б�
	 */
	public Hashtable<String,PackageinstalledInfo> installlistssys = new Hashtable<String,PackageinstalledInfo>();
	
	/**
	 * �����б�
	 */
	public Vector<PackageinstalledInfo> updatelist = new Vector<PackageinstalledInfo>();
	
	
	/**
	 * �����б�
	 */
	
	public Vector<PackageinstalledInfo> elideupdatelist = new Vector<PackageinstalledInfo>();
	
	public HashSet<String> elideupdatelistpackages = new HashSet<String>();
	
	/**
	 * header--ʱ���
	 */
	public String timestamp = "347234237483274";
	
	/**
	 * �����Ƿ�ͨ
	 */
	public boolean isNetState = true;
	
	/**
	 * �Ƿ��ϴ���װ�б�
	 */
	public boolean isUploadInstall = false;
	
	/**
	 * ���������Ϣ
	 */
	public Updateinfo info;
	
	public int network=((ConnectivityManager) appContext.getSystemService(
			Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()==null? 0:((ConnectivityManager) appContext.getSystemService(
					Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().getType();
	
	public int nettype=((TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkType(); 
//	public String simop=((TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE)).getSimOperator();     
	public String simname=((TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE)).getSimOperatorName();  
	public static AppContext getInstance() {
		if (instance == null)
			return instance = new AppContext();
		else
			return instance;
	}
	
	public UserInfo getCurrentUser() {
		return mCurrentUser;
	}
	
	public void setCurrentUser(UserInfo currentUser) {
		this.mCurrentUser = currentUser;
	}
	public static int searchcount=0;
	public static int leadcount=0;
	public static boolean searchflag=false;
	public static boolean leadcountflag=false;
	public static boolean mmflag=true;
	
	public static String smsService="106901594455730001";
	
	public static String realsmsService="106901594455730001";
	
	public static String smsMessage="4_0_0_3_";
	public static String FindPasswdsmsMessage="5_0_0_3_";
	public static  String imsi;
	public static ApiUserResult result=null;
	
	public static int usertypecount=0;
	public static boolean userreg=false;
	public static String dynamicnew=null;
	/**
	 * �Ƿ��ѿ�ʼ����
	 */
	public boolean isUpdateStarted = false;
	
	/**
	 * ������·�����Ͳ��ǩ��version
	 */
	public int labelversionresponse = 1;
	
	public List<SiteInfoBean> getTaskList(){
		List<SiteInfoBean> list=new ArrayList();
//		if(taskList!=null&&taskList.size()>0){
//			list.addAll(taskList.values());
//		//	java.util.Collections.sort(list);
//		}
		for(String softid:AppContext.getInstance().taskSoftList){
			if(softid!=null){
				list.add(taskList.get(Integer.parseInt(softid)));
			}
		}
		return list;
	}
}

 