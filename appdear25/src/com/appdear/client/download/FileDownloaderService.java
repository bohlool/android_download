package com.appdear.client.download;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.appdear.client.commctrls.Common;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.Constants;
import com.appdear.client.service.MyApplication;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.utility.ServiceUtils;
import com.appdear.client.QianmingDialog;
import com.appdear.client.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**
 * ����service
 * @author zqm
 *
 */
public class FileDownloaderService extends Service implements DownloadListener {

	/**
	 * ����֪ͨ��Ϣ����
	 */
	public static NotificationManager notificationManager;
	
	/**
	 * �����е�����
	 */
	public static int task_num = -1;
	
	/**
	 * ����������֪ͨID
	 */
	public static final int NOTIFICATION_DOWNLOADING_ID = -1000;
	
	/**
	 * �����е�֪ͨ
	 */
	public static Notification notification;
	
	/**
	 * ������ɵ�֪ͨ
	 */
	public static Notification finishNotification;
	
	/**
	 * ����ʧ�ܵ�֪ͨ
	 */
	public static Notification errNotification;
	
	/**
	 * ������ɵ�֪ͨ����
	 */
	private PendingIntent finishIntent;
	
	/**
	 * �����е�֪ͨ����
	 */
	public static PendingIntent contentIntent;
	
	/**
	 * ����ʧ�ܵ�֪ͨ����
	 */
	public static PendingIntent errIntent;
	
	/**
	 * ���֪ͨ�����������ؽ����intent
	 */
	public Intent managerIntent;
	
	/**
	 * �����б���̳߳أ�ÿ��ִ��һ����������
	 */
	private ExecutorService executor = Executors.newFixedThreadPool(1);
	
	/**
	 * ����ִ��ɾ������
	 */
	public static boolean delete_all = false;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onStart(final Intent intent, final int startId) {
		if (intent != null) {
			//��ȡ����������Ϣ
			final SiteInfoBean bean = (SiteInfoBean) intent.getSerializableExtra("download_bean");
			//�ж��Ƿ���ͨ����������������������Ҫ��ϵͳ��ȡӦ�ó���ͼ�꣩
			if (intent.getStringExtra("Dicon").equals("true"))
				bean.dicon = ServiceUtils.getInstallIcon(this, bean.appID);
			
			//����֪ͨ��Ϣ�ļ���
			bean.notification = this;
			
			//�������������������������������Ϣ
			//-1Ϊ�ȴ�����
			bean.state = -1;
			//�������ؽ���ļ���
			if (AppContext.getInstance().downloadlistener != null)
				bean.listener = AppContext.getInstance().downloadlistener;
			//��ȡ����sd�ı���·��
			if (ServiceUtils.getSDCARDImg(Constants.APK_DATA) != null)
				ServiceUtils.getSDCARDImg(Constants.APK_DATA).getPath();
			//����������������б�(�����б����״̬�����Ƿ�ʼ����)
			SiteFileFetch siteFileFetch = new SiteFileFetch(bean);
			
			if(siteFileFetch.fileEquesStop)
			{
				return;
			}
			bean.siteFileFecth = siteFileFetch;
			//��ӵ��̳߳�
			executor.execute(siteFileFetch);
			
			//����DB
			AppContext.getInstance().downloader.downDb.save(bean);
			//��ӵ��������
			AppContext.getInstance().taskList.put(bean.softID,bean);
			
			if(!AppContext.getInstance().taskSoftList.contains(String.valueOf(bean.softID))){
				AppContext.getInstance().taskSoftList.add(String.valueOf(bean.softID));
			}
			//���֪ͨ��Ϣ
			addNotification(bean);
			showNotification(NOTIFICATION_DOWNLOADING_ID, true, notification);
		}
		super.onStart(intent, startId);
	}
	
	private void addNotification(SiteInfoBean bean) {
		if (notificationManager == null)
			notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		if (notification == null) {
			//��ʼ��֪ͨ��Ϣ,����֪ͨ�������ʾ���ع������
			notification = new Notification(R.drawable.app_download_task, 
					"��������", 
					System.currentTimeMillis());
			managerIntent = new Intent();
			managerIntent.putExtra("notificaiton", "true");
			managerIntent.setClass(FileDownloaderService.this, MoreManagerDownloadActivity.class);
			managerIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			
			contentIntent = PendingIntent.getActivity(this, 0, managerIntent, 0);
		}
		
		//����֪ͨ����ʾ����  
		if (task_num == -1) {
			task_num = 0;
			for (SiteInfoBean numbean : AppContext.getInstance().taskList.values()) {
				if (numbean.state != 2) {
					//�ٴα����ǰ����ʱ����Ҫ�ٴθ���֪ͨ����
					numbean.isShowNotification = false;
					task_num ++;
				}
			}
		} else {
			//�жϵ�ǰ������Ҫ�ٴθ���֪ͨ����
			if (bean.isShowNotification) {
				bean.isShowNotification = false;
				task_num ++;
			}
		}
		notification.setLatestEventInfo(this, "��ƤӦ������", task_num + "��������������У� �����鿴", contentIntent);
	}
	
	public static void showNotification(int id, boolean isclear, Notification notification) {
		if (id == NOTIFICATION_DOWNLOADING_ID) {
			if (task_num <= 0) {
				notificationManager.cancel(id);
				return;
			}
		}
		if (isclear) {
			//��������֪ͨ��Ϣ
			if(notification !=null){
				notification.flags |= Notification.FLAG_AUTO_CANCEL;  
			}
		}
		notificationManager.notify(id, notification);
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				final SiteInfoBean bean = (SiteInfoBean)msg.getData().getSerializable("softinfo");
				//��֪ͨˢ�½���״̬
				Intent intent = new Intent(Common.DOWNLOAD_NOTIFY);
				intent.putExtra("softid", bean.softID);
				intent.putExtra("downloadfinsh",1);
				sendBroadcast(intent);
				
				//�Ƿ����Զ���װ
				boolean autoinstall = SharedPreferencesControl.getInstance().getBoolean(
						"autoinstall",com.appdear.client.commctrls.Common.SETTINGS, FileDownloaderService.this);
				notification.setLatestEventInfo(FileDownloaderService.this, "��ƤӦ������", task_num + "��������������У� �����鿴", contentIntent);
				showNotification(NOTIFICATION_DOWNLOADING_ID, true, notification);
				String filepath = bean.sFilePath + "/"
				+ bean.sFileName;
//				String filepath = "/sdcard/TestScreen.apk";
				File file = new File(filepath);
				if (autoinstall) {
					//������װ�Ի���
				 if(ServiceUtils.isInstall(FileDownloaderService.this,file,bean.appID)){
//					if(ServiceUtils.isInstall(FileDownloaderService.this,file,"com.jineefo")){
					ServiceUtils.Install(FileDownloaderService.this, bean.sFilePath+"/"+bean.sFileName, bean.appID, bean.softID);
					new Thread(new Runnable() {				
						@Override
						public void run() {
							//�ϴ����������־
							ApiManager.downloadcomplete(bean.sSiteURL, "2");
						}
					}).start();
				 }else{
					 dojumpqian(bean.softName,bean.appID);
				 }
				} else {
					
					   if(ServiceUtils.isInstall(FileDownloaderService.this,file,bean.appID)){
						   finishIntent = PendingIntent.getActivity(FileDownloaderService.this, 0, 
									new Intent(Intent.ACTION_VIEW)
									.setDataAndType(Uri.parse("file://"+ filepath),
									"application/vnd.android.package-archive"), 0);
					   }else{
						   /**
						    * ���� ж�ؿ�
						    */
						   dojumpqian(bean.softName,bean.appID);
					   }
					
					
					finishNotification = new Notification(R.drawable.app_download_task, 
							"�������", 
							System.currentTimeMillis());
					finishNotification.setLatestEventInfo(FileDownloaderService.this, bean.softName, "������ɣ������װ", finishIntent);
					showNotification(bean.softID, true, finishNotification);
				}
				break;
			case 2:
				notification.setLatestEventInfo(FileDownloaderService.this, "��ƤӦ������", task_num + "��������������У� �����鿴", contentIntent);
				showNotification(NOTIFICATION_DOWNLOADING_ID, true, notification);
				break;
			case 3:
				//����ʧ����ʾ
				String message = msg.getData().getString("msg");
				if (message == null || message.equals("")) {
					message = "����ʧ��";
				}
				if (errIntent == null)
					errIntent = PendingIntent.getActivity(FileDownloaderService.this, 0, managerIntent, 0);
				
				String softname = msg.getData().getString("softname");
				errNotification = new Notification(R.drawable.app_download_task, softname + "����ʧ�ܣ�����鿴", 
						System.currentTimeMillis());
				errNotification.setLatestEventInfo(FileDownloaderService.this, "��ƤӦ������", softname + "����ʧ�ܣ�����鿴", errIntent);
				showNotification(msg.getData().getInt("softid"), true, errNotification);
			}
		};
	};
	public void dojumpqian(String softname,String appid){
		Intent intent=new Intent(this,QianmingDialog.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("softname",softname);
		intent.putExtra("appid", appid);
		this.startActivity(intent);
	}
	@Override
	public void updateProcess(final Object object) {
	}
	
	@Override
	public synchronized void updateProcess(Exception e, final String message, final Object object) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if (delete_all)
					return;
				SiteInfoBean bean = (SiteInfoBean) object;
				MyApplication.getInstance().getSoftMap().remove(bean.softID);
				if (bean.siteFileFecth != null) {
					bean.siteFileFecth.siteStop();
				}
				bean.state = 3;
				Message msg = new Message();
				msg.what = 3;
				msg.getData().putString("softname", bean.softName);
				msg.getData().putInt("softid", bean.softID);
				msg.getData().putString("msg", message);
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	public static void setNotification(Context context) {
		if (notification != null) {
			notification.setLatestEventInfo(context, "��ƤӦ������", task_num + "��������������У� �����鿴", contentIntent);
			showNotification(NOTIFICATION_DOWNLOADING_ID, true, notification);
		}
	}
	
	@Override
	public void updateFinish(final Object object) {
		task_num --;
		if(object==null)return;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if (delete_all)
					return;
				final SiteInfoBean bean = (SiteInfoBean) object;
				MyApplication.getInstance().getSoftMap().put(bean.softID, 2);
				bean.siteFileFecth = null;
				Message msg = new Message();
				msg.what = 1;
				msg.getData().putSerializable("softinfo", bean);
				handler.sendMessage(msg);
				//�ϴ�������ɱ��
				DownloadUtils.uploadTag(bean);
			}
		}).start();
	}
}
