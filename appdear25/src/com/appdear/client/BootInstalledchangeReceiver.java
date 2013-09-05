package com.appdear.client;

import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.download.FileDownloaderService;
import com.appdear.client.download.SiteInfoBean;
import com.appdear.client.exception.ApiException;
import com.appdear.client.model.PackageinstalledInfo;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.MyApplication;
import com.appdear.client.service.UpdateService;

import com.appdear.client.service.Constants;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.service.api.ApiUrl;
import com.appdear.client.utility.AsyLoadImageService;
import com.appdear.client.utility.ChineseConvert;
import com.appdear.client.utility.ServiceUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * ���������ж�غͰ�װ
 * @author zqm
 *
 */
public class BootInstalledchangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {
 		
		/**
		 * zxy 2011 10 11 ��Ӽ���WIFI��������
		 * 
		 */
		if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
			AppContext.getInstance().network=((ConnectivityManager) context.getSystemService(
					Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()==null? 0:((ConnectivityManager) context.getSystemService(
							Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().getType();
		}
		if (ServiceUtils.checkNetState(context)) {
			Intent updateService = new Intent(context,
					UpdateService.class);
			context.startService(updateService);
		}
		if(intent!=null&&intent.getAction()!=null){
			if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")&& MoreManagerInstalledActivity.isupload==false) {
				ServiceUtils.handlerAddApp(intent, context);
			}
		}
 		if (intent == null || intent.getDataString() == null || intent.getDataString().equals(""))
			return;
		 
			String packagename = intent.getDataString().substring(8);
			/**
			 *  jindan-modify start
			 */
			if(intent.getAction().equals("android.intent.action.PACKAGE_ADDED")){
			if (AppContext.getInstance().taskList.size() == 0) {
				AppContext.getInstance().taskList = AppContext.getInstance().downloader.downDb.read();
			}
			for(SiteInfoBean bean : AppContext.getInstance().taskList.values()){
				if (bean.appID.equals(packagename)) {
					if (FileDownloaderService.notificationManager != null)
						FileDownloaderService.notificationManager.cancel(bean.softID);
					//�ж��Ƿ�ɾ��apk�ļ�
					boolean isdelete = SharedPreferencesControl.getInstance().getBoolean(
							"deleteApk",com.appdear.client.commctrls.Common.SETTINGS, context);
					if (isdelete) {
						String filepath = bean.sFilePath
						+ "/" + bean.sFileName;
						ServiceUtils.deleteSDFile(filepath);
						ServiceUtils.deleteSDFile(filepath+".size");
					}
					
					AppContext.getInstance().downloader.downDb.delete(bean.softID);
					AppContext.getInstance().taskList.remove(bean.softID);
					AppContext.getInstance().taskSoftList.remove(String.valueOf(bean.softID));
					break;
				}
			}
			}
			/**
			 * jindan-modify end
			 */
			 boolean isUpdateMainView=true;
			 boolean isdelete = false;
			//���Ҹ����б����Ƿ������������о�ɾ��
			for (int i = 0; i < AppContext.getInstance().updatelist.size(); i ++) {
				PackageinstalledInfo info = AppContext.getInstance().updatelist.get(i);
				if (info != null && info.pname.equals(packagename)) {
					//�����ݴ������б����Ƴ�
					AppContext.getInstance().updatelist.remove(info);
					MyApplication.getInstance().elideupdate--;
					((MainActivity)MyApplication.getInstance().mainActivity).updateNumView();
 					isdelete=true;
					break;
				}
			}
			if(AppContext.getInstance().elideupdatelist!=null&&AppContext.getInstance().elideupdatelist.size()>0){
				for (int i = 0; i < AppContext.getInstance().elideupdatelist.size(); i ++) {
					PackageinstalledInfo info = AppContext.getInstance().elideupdatelist.get(i);
					if (info != null && info.pname.equals(packagename)) {
						//�����ݴ������б����Ƴ�
						AppContext.getInstance().elideupdatelist.remove(info);
						ServiceUtils.removeOneElidePackages(context, info.pname);
						isUpdateMainView=false;
						break;
					}
				}
			}
			if(isUpdateMainView&&isdelete)
			{
				MyApplication.getInstance().elideupdate--;
				((MainActivity)MyApplication.getInstance().mainActivity).updateNumView();
			}
		//�Ѱ�װ�б����仯�ϴ��Ѱ�װ�б�
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					if (AppContext.getInstance().spreurl == null 
//							|| AppContext.getInstance().spreurl.equals("")) {
//						AppContext.getInstance().spreurl = SharedPreferencesControl.getInstance().getString(
//								"spreurl", com.appdear.client.commctrls.Common.SETTINGS, context);
//					}
//					if (Constants.DEBUG)
//						Log.i("BootInstalledchangeReceiver", "action=" + intent.getAction());
//					//����Ƿ������������д��ڣ����ھͱ��ɾ��״̬
//				/*	if (intent == null || intent.getDataString() == null || intent.getDataString().equals(""))
//					return;
//					String packagename = intent.getDataString().substring(8);
//					if (AppContext.getInstance().taskList.size() == 0) {
//						AppContext.getInstance().taskList = AppContext.getInstance().downloader.downDb.read();
//					}
//					for(SiteInfoBean bean : AppContext.getInstance().taskList.values()){
//						if (bean.appID.equals(packagename)) {
//							if (FileDownloaderService.notificationManager != null)
//								FileDownloaderService.notificationManager.cancel(bean.softID);
//							//�ж��Ƿ�ɾ��apk�ļ�
//							boolean isdelete = SharedPreferencesControl.getInstance().getBoolean(
//									"deleteApk",com.appdear.client.commctrls.Common.SETTINGS, context);
//							if (isdelete) {
//								String filepath = bean.sFilePath
//								+ "/" + bean.sFileName;
//								ServiceUtils.deleteSDFile(filepath);
//								ServiceUtils.deleteSDFile(filepath+".size");
//							}
//							
//							AppContext.getInstance().downloader.downDb.delete(bean.softID);
//							AppContext.getInstance().taskList.remove(bean.softID);
//							break;
//						}
//					}
//					//���Ҹ����б����Ƿ������������о�ɾ��
//					for (int i = 0; i < AppContext.getInstance().updatelist.size(); i ++) {
//						PackageinstalledInfo info = AppContext.getInstance().updatelist.get(i);
//						if (info != null && info.pname.equals(packagename)) {
//							//�����ݴ������б����Ƴ�
//							AppContext.getInstance().updatelist.remove(info);
//							break;
//						}
//					}
//					if(AppContext.getInstance().elideupdatelist!=null&&AppContext.getInstance().elideupdatelist.size()>0){
//						for (int i = 0; i < AppContext.getInstance().elideupdatelist.size(); i ++) {
//							PackageinstalledInfo info = AppContext.getInstance().elideupdatelist.get(i);
//							if (info != null && info.pname.equals(packagename)) {
//								//�����ݴ������б����Ƴ�
//								AppContext.getInstance().elideupdatelist.remove(info);
//								ServiceUtils.removeOneElidePackages(context, info.pname);
//								break;
//							}
//						}
//					}*/
//					if (AppContext.getInstance().spreurl.equals("") 
//							|| AppContext.getInstance().spreurl == null) {
//						return;
//					}
//					if (AppContext.getInstance().isUploadInstall) {
//						AppContext.getInstance().installlists = 
//							ServiceUtils.getInstalledApps(context, false);
//						ApiManager.installlist(AppContext.getInstance().installlists, 
//								AppContext.getInstance().spreurl + ApiUrl.installlist);
//					} else {
//						AppContext.getInstance().isUploadInstall = true;
//					}
//				} catch (ApiException e) {
//					if (Constants.DEBUG)
//						Log.i("uploadInstall", "��װ�б�仯�ϴ�ʧ��");
//				} catch(Exception e) {
//					if (Constants.DEBUG)
//						Log.i("uploadInstall", "��װ�б�仯�ϴ�ʧ��");
//				}
//			}
//		}).start();
	}
}
