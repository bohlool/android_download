package com.appdear.client.download;

import java.io.File;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.appdear.client.commctrls.BaseGroupActivity;
import com.appdear.client.commctrls.Common;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.Constants;
import com.appdear.client.service.MyApplication;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.utility.ServiceUtils;

/**
 * ���ع�����
 * @author zqm
 *
 */
public class DownloadUtils {
	/**
	 * ������������ж��Ƿ������������
	 * @param bean
	 * @param context
	 * @return
	 */
	public static String[] download(SiteInfoBean bean, Context context) {
		int taskState = checkPlace(bean, context);
		//�������״̬
		if (!ServiceUtils.checkNetState(context)) {
			return new String[]{"���������������״̬", "-1"};
		}
		//�ж��Ƿ���wifi������
		boolean loadwifi = SharedPreferencesControl.getInstance().getBoolean(
				"loadwifi", com.appdear.client.commctrls.Common.SETTINGS, context);
		if (!ServiceUtils.isWiFiActive(context) && loadwifi) {
			return new String[]{"�����ý���Wi-Fi��������,����������������.", "-2"};
		}
		
		if (taskState == -3)
			return new String[]{"����SD�����ڣ�����벻��ȷ", "-3"};
		else if (taskState == -6)
			return new String[]{"SD���ռ�����", "-6"};
		else if (taskState == -7)
			return new String[]{"�ڴ�ռ�����", "-7"};
		else if (taskState == -8)
			return new String[]{"�洢�ռ�����", "-8"};
		
		SiteInfoBean hasbean = null;
		if((hasbean=AppContext.getInstance().taskList.get(bean.softID))!=null){
			//�Ѿ���������������Ϣ
			if (hasbean.state == 0 || hasbean.state == -1) {
				taskState = 0;//���������Ѵ���,��������״̬
				MyApplication.getInstance().getSoftMap().put(bean.softID, 1);
			} else if (hasbean.state == 1) {
				taskState = 1;//���������Ѵ�����ͣ״̬
				MyApplication.getInstance().getSoftMap().put(bean.softID, 1);
			} else if (hasbean.state == 2) {
				/*
				 * ���Ѵ��ڵ������������Ƿ��а�װ�ļ�
				 */
				if (ServiceUtils.isHasFile(hasbean.sFilePath+"/"+bean.sFileName)) {
					//���������Ѵ���,����Ҫ��������
					taskState = 0;
					MyApplication.getInstance().getSoftMap().put(bean.softID, 1);
				} else {
					//��������ڰ�װ�ļ�����������
					taskState = 2;
					hasbean.downloadLength = 0;
				}
			}
			if (taskState != 0) {
				//��ͣ״̬�ָ�����,���ݶ�ʧ�ļ���������,���������ִ��
				addDownloadTask(hasbean, context);
			}
		}
		//��ǰ��������������ֱ������
		if (taskState == -2) {
			addDownloadTask(bean, context);
			MyApplication.getInstance().getSoftMap().put(bean.softID, 1);
		}
		if (taskState == 0)
			return new String[]{bean.softName+ "�����Ѵ��ڣ��鿴�뵽���ع���!", "0"};
		return new String[]{bean.softName+ "�ѿ�ʼ���أ��鿴�뵽���ع���!", "0"};
	}
	
	/**
	 * ����Service�����ԡ�����
	 * @param threadID
	 */
	public static void addDownloadTask(SiteInfoBean bean, Context context) {
		if (Constants.DEBUG) Log.i("info","addDownloadTask");
		//������������Ϊ�ȴ�����״̬
		bean.state = -1;
		//����headerΪ�������û��������־ͳ�ƣ�
		bean.downloadstateHeader = 2;
		
		//����Service
		Intent taskIntent = new Intent();
		taskIntent.setClass(context, FileDownloaderService.class);
		//��ΪDrawble�����ﴫ�ݲ���ȥ�������ﱣ��һ��״ֵ̬
		if (bean.dicon != null) {
			taskIntent.putExtra("Dicon", "true");
			bean.dicon = null;
		} else {
			taskIntent.putExtra("Dicon", "false");
		}
		
		bean.notification = null;
		bean.listener = null;
		//bean.view = null;
		bean.siteFileFecth = null;
		taskIntent.putExtra("download_bean", bean);
		context.startService(taskIntent);
		
		//ͬ��������Ϣ
		AppContext.getInstance().downloader.downDb.update(bean);
	}
	
	/**
	 * �ϴ���������ɵ��ļ�
	 */
	public static void uploadTag(SiteInfoBean bean) {
		ApiManager.downloadcomplete(bean.sSiteURL, "1");
	}
	// -8    ���洢�ռ��������ڴ�ռ����� && sdcard�洢�ռ�����  && SD��������   -7 �ڴ�ռ�����   -6 sdcard�洢�ռ�����    -3  SD��������       -2 Ĭ��ֵ��������
	public static int checkPlace(SiteInfoBean bean, Context context) {
		if (AppContext.getInstance().taskList.size() == 0)
			AppContext.getInstance().downloader.readDB();
		SiteInfoBean hasbean=null;
		
		if((hasbean=AppContext.getInstance().taskList.get(bean.softID))!=null){
			bean=hasbean;
			//sdcard
			if(bean.place==0)
			{
				if (null == ServiceUtils.getSDCardUrl()) {
					return -3;
				}
				//�洢�ռ�����
				if (ServiceUtils.readSdCardAvailableSpace() < bean.fileSize) {
					return -6;
				}
			}else
			{
				if( ServiceUtils.readDeviceAvailableInternalSpace() < bean.fileSize)
				{
					 return -7;
				}
			}
			 return -2; 
		}else  //������
		{
			// ���ж�sdcard �Ƿ���ڲ��ҿ��ÿռ��Ƿ��㹻��
			File f=null;
			if (null !=(f=ServiceUtils.getSDCardUrl())&&ServiceUtils.readSdCardAvailableSpace() > bean.fileSize) {
				 bean.place=0;   //·��
				 bean.sFilePath=f.getPath()+"/apk";
				return -2;
			}else
			{
				// ���ж��ֻ��ڴ���ÿռ��Ƿ��㹻��
				if( ServiceUtils.readDeviceAvailableInternalSpace() > bean.fileSize)
				{
					 bean.place=1;
					 bean.sFilePath=Constants.DATA_APK;
					 return -2;
				}
			}
			return -8;
		}
		
	}
}
