package com.appdear.client;

import com.appdear.client.commctrls.BaseActivity;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.download.FileDownloaderService;
import com.appdear.client.download.SiteInfoBean;
import com.appdear.client.exception.ApiException;
import com.appdear.client.model.PackageinstalledInfo;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.AppdearService;
import com.appdear.client.service.MyApplication;
import com.appdear.client.service.UpdateService;

import com.appdear.client.service.Constants;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.service.api.ApiUrl;
import com.appdear.client.utility.AsyLoadImageService;
import com.appdear.client.utility.ChineseConvert;
import com.appdear.client.utility.ServiceUtils;

import android.app.NotificationManager;
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
public class NetworkStateReceiver extends BroadcastReceiver {
	public static boolean netStateDialog = true;
	@Override
	public void onReceive(final Context context, final Intent intent) {
		// ����������ӷ���
		boolean checkState = ServiceUtils.checkNetState(context);
		if (AppContext.getInstance().isNetState != checkState)
			netStateDialog = true;
		AppContext.getInstance().isNetState = checkState;
		if (AppContext.getInstance().isNetState) {
		}else{
			((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(AppdearService.UPDATE_INFO_ID);
		}
	
		// ��ͣ��ǰ�����������ص��߳�
		/*if (!AppContext.getInstance().isNetState)
			AppContext.getInstance().downloader.downDb.updatePause();*/
		this.clearAbortBroadcast();
	}
}
