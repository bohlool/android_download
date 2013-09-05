package com.appdear.client.service;
 
 
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.appdear.client.MainActivity;
import com.appdear.client.MoreManagerUpdateActivity;
import com.appdear.client.ShowServerInfoDialog;
import com.appdear.client.UpdateDialog;
import com.appdear.client.commctrls.Common;
import com.appdear.client.exception.ApiException;
import com.appdear.client.model.PackageinstalledInfo;
import com.appdear.client.model.Updateinfo;
import com.appdear.client.model.UpdatelistInfo;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.service.api.ApiSoftListResult;
import com.appdear.client.utility.ServiceUtils;
/**
 * ��̨����
 * @author zxy
 */
public class AppdearService extends Service {
	BackgroundHandler backgroundHandler = null;
	NotificationManager mNotificationManager = null;
	public final static int UPDATE_INFO_ID = 0;
	public static ApiSoftListResult updateSoftResult = null;
	private final static int SOFTUPDATE_INFO_CODE = 61;
	private final static int SERVER_INFO_CODE = 60;
	public final  static int CLIENT_UPDATE_INFO = 62;
	public static boolean flag=false;
	private Updateinfo updateinfo = null;
	/**
	 * ������Ϣ
	 */
	public static String updateurl= null;
	public static String  softSize = null;
	public static String softUpdateTip = null;
	public static String  softVersion = null;
	
	int elideupdate=0;
	/**
	 * �Ѱ�װ�����б���ֹ�������˳�zh
	 */
	public Hashtable<String,PackageinstalledInfo> installlists = new Hashtable<String,PackageinstalledInfo>();
	/**
	 * �Ѱ�װ�����б���ֹ�������˳�zh
	 */
	public Hashtable<String,PackageinstalledInfo> installlistssys = new Hashtable<String,PackageinstalledInfo>();
	/**
	 * �����ϴε��������
	 */
	
	int totalCount;
	int totalCountsys;
	@Override
	public void onCreate() {
		//�õ�����
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		super.onCreate();
		if (Constants.DEBUG)Log.i("service", "service create");
		/**
		 * ������ʱ����
		 */
		/*installlists =  ServiceUtils.getInstalledApps(AppdearService.this, false);
		AppContext.getInstance().installlists = installlists;
		totalCount = installlists.size();*/
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		//init handler
		Application app = this.getApplication();
		Looper localLooper = app.getMainLooper();
		//��handler����ѭ��
		backgroundHandler = new BackgroundHandler(localLooper);
		//new Thread(new LooperThread()).start();
		updateinfo = AppContext.getInstance().info;
		doWork();
		super.onStart(intent, startId);
		this.stopSelf();
		if (Constants.DEBUG)Log.i("service", "service onStart");
	}

	/**
	 * ����֪ͨ
	 * @param notification ֪ͨ
	 * @param notificationIntent ���֪ͨ����תintent
	 */
	private void showNotification(Notification notification,Intent notificationIntent,int id,String title,String contextText){
		//�����¼�
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.contentIntent = contentIntent;
		//����֪ͨ
		
		if(notification !=null){
			notification.flags |= Notification.FLAG_AUTO_CANCEL;  
		}
		notification.setLatestEventInfo(this, title, contextText, contentIntent);
		mNotificationManager.notify(id, notification);
	}
	
	/**
	 * ���������ʾ
	 */
	private void initSoftUpdateNotification(){
		String msgText = "���������ʾ";
		
		if(null!=updateSoftResult&&updateSoftResult.updatelist!=null){
		//	Log.i("info123",AppContext.getInstance().elideupdatelistpackages.size()+"="+updateSoftResult.updatelist.size());
			int e=0,count=0;
			
			if(AppContext.getInstance().elideupdatelistpackages!=null){
				e=AppContext.getInstance().elideupdatelistpackages.size();
				if(e>0){
					List<UpdatelistInfo> ls=new ArrayList<UpdatelistInfo>();
					for(UpdatelistInfo update:updateSoftResult.updatelist){
						
						for(String str:AppContext.getInstance().elideupdatelistpackages){
							if(update.appid!=null&&update.appid.equals(str)){
								ls.add(update);
								count++;
							}
						}
					}
					//updateSoftResult.updatelist.removeAll(ls);
				}
//				
			}
//			Log.i("info123",updateSoftResult.updatelist.size()+"="+AppContext.getInstance().updatelist.size());
//			if(AppContext.getInstance().exitflag==false){
//				msgText = "����"+(elideupdate=AppContext.getInstance().updatelist.size())+"�������Ҫ����.";
//			}else{
//				int size=AppContext.getInstance().updatelist.size();
//				if(size==0){
					msgText = "����"+(elideupdate=(updateSoftResult.updatelist.size()-count))+"�������Ҫ����.";
//				}else{
//					msgText = "����"+(elideupdate=AppContext.getInstance().updatelist.size())+"�������Ҫ����.";
//				}
//			}
		}
		 
		if(elideupdate==0)return;
		MyApplication.getInstance().elideupdate=elideupdate;
		if(MyApplication.getInstance().mainActivity!=null)
		{
			((MainActivity)MyApplication.getInstance().mainActivity).updateNumView();
		}
		//����notification
		Notification notification = new Notification(com.appdear.client.R.drawable.nofication_logo,msgText,  
				System.currentTimeMillis());
		
		Intent notificationIntent = new Intent(this, MoreManagerUpdateActivity.class);
		notificationIntent.putExtra("notificaiton", "true");
		notificationIntent.addFlags(Notification.FLAG_AUTO_CANCEL);
		
		//����֪ͨ
		//Log.i("info1",updateSoftResult+"="+updateSoftResult.updatelist+"="+updateSoftResult.updatelist.size());
		if(null!=updateSoftResult&&updateSoftResult.updatelist!=null&&updateSoftResult.updatelist.size()>0)
		showNotification(notification,notificationIntent,UPDATE_INFO_ID,msgText,msgText+"�����鿴");
		
		//Log.i("test", "tset, updateSoftResult size ,"+updateSoftResult.updatelist.size());
	}
	
	/**
	 * �г�������ʾ
	 */
	private void initClientUpdateNotification(){
		String msgText = "��ƤӦ�����ط������°汾";
		
		//����notification
		Notification notification = new Notification(com.appdear.client.R.drawable.nofication_logo,msgText,  
				System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, UpdateDialog.class);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		notificationIntent.addFlags(Notification.FLAG_AUTO_CANCEL);
		//����֪ͨ
		showNotification(notification,notificationIntent,CLIENT_UPDATE_INFO,msgText,msgText+".����鿴����");
	}
	
	
	/**
	 *�����߳�  ������ ���������Ϣ��ʾ
	 *
	 */
	private class GetUpdateInfoThread implements Runnable{
		public GetUpdateInfoThread() {
			
		}
		
		@Override
		public void run() {
			//��ʼ��������
			 try {
				 
				 //�������д�����ǰ�ڷ���oncreate������
				
				 installlists =  ServiceUtils.getInstalledApps(AppdearService.this, false,true);
				 AppContext.getInstance().installlists = installlists;
				 totalCount = installlists.size();
				if (installlistssys.size() == 0){
					installlistssys = ServiceUtils.getInstalledAppsSys(AppdearService.this, false);
					AppContext.getInstance().installlistssys = installlistssys;
					totalCountsys = installlistssys.size();
				}
				if(updateSoftResult==null){
//					List list= new ArrayList();
//					list.addAll(AppContext.getInstance().installlists.values());
//					list.addAll(AppContext.getInstance().installlistssys.values());
//					updateSoftResult = ApiManager.updatelist(ServiceUtils.getUpdatelist(list),AppdearService.this);
					//jindan �¸����б��Э��ʵ��
					List list= new ArrayList();
					List list1= new ArrayList();
					list.addAll(AppContext.getInstance().installlistssys.values());
					list1.addAll(AppContext.getInstance().installlists.values());
					updateSoftResult = ApiManager.updatelist2(ServiceUtils.getUpdatelist(list),list1,AppdearService.this);
				}
				backgroundHandler.sendEmptyMessage(SOFTUPDATE_INFO_CODE);
				if (AppContext.getInstance().updatelist != null
						&& AppContext.getInstance().updatelist.size() > 0) {

				} else if (updateSoftResult != null && updateSoftResult.updatelist.size() > 0) {
						// for (java.util.Map.Entry<String, PackageinstalledInfo>
						// pack:AppContext.getInstance().installlists.entrySet()) {
						// if(pack.getValue()==null)continue;
						// String appname =pack.getValue().pname;
						// Log.i("info",appname);
						java.util.Map<String, PackageinstalledInfo> map = AppContext
								.getInstance().installlists;
						java.util.Map<String, PackageinstalledInfo> map1 = AppContext
								.getInstance().installlistssys;
		
						// Log.i("info090", result.updatelist+"=>");
						for (UpdatelistInfo uinfo : updateSoftResult.updatelist) {
		
							String updateappid = uinfo.appid;
							PackageinstalledInfo info = null;
							if (map.containsKey(updateappid)) {
								info = map.get(updateappid);
							} else if (map1.containsKey(updateappid)) {
								info = map1.get(updateappid);
							}
							if (info == null)
								continue;
							info.updateVesrionName = uinfo.versionname;
							info.softID = uinfo.softid;
							info.downloadUrl = uinfo.downloadurl;
							info.softsize = uinfo.softsize;
		//	public String updatedesc;
	   //	public int udlinenum;
							info.updatedesc=uinfo.updatedesc;
							info.udlinenum=uinfo.udlinenum;
							if (AppContext.getInstance().elideupdatelistpackages
									.contains(info.pname)) {
								if (AppContext.getInstance().elideupdatelist.contains(info) == false)
									AppContext.getInstance().elideupdatelist.add(info);
		
							} else {
								AppContext.getInstance().updatelist.add(info);
							}
						 }
				}
				
				
				if (Constants.DEBUG)Log.i("backgroundHandler", "backgroundHandler get update info ...");
			} catch (ApiException e) {
				e.printStackTrace();
			}catch (OutOfMemoryError e) {
			//	AsyLoadImageService.getInstance().getImageCache().clear();
			//	System.gc();
				e.printStackTrace();
				Log.e("load image", "�ڴ������");
			}finally{
				flag=false;
			}
		}
	}
	
	/**
	 * ������Ϣ
	 *
	 */
	private class GetServerInfoThread   implements Runnable {
		public GetServerInfoThread() {
		
		}

		@Override
		public void run() {
			//����֪ͨ
			backgroundHandler.sendEmptyMessage(SERVER_INFO_CODE);
			
			if (Constants.DEBUG)Log.i("backgroundHandler", "backgroundHandler get server msg ...");
		}
	}
	
	/**
	 * ��Ƥ�г����¼��
	 */
	private class GetAppdearClientInfo implements Runnable{
		@Override
		public void run() {
			if (null != updateinfo) {
				//������ʾ
				if (Constants.DEBUG)Log.i("update", "update , save update info . ");
				Common.isNeedUpdateClient = true;
				if(updateinfo.updateurl!=null&&!updateinfo.updateurl.trim().equals("")){
					updateurl=updateinfo.updateurl;
					softSize = updateinfo.softSize;
					softUpdateTip = updateinfo.softUpdateTip;
					softVersion = updateinfo.softVersion ;
				}
				backgroundHandler.sendEmptyMessage(CLIENT_UPDATE_INFO);
			}
		}
	}
	
	/**
	 * ��Ϣ������
	 */
	private class  BackgroundHandler   extends Handler{
		public BackgroundHandler(Looper looper){
			super(looper);
		}
		
		@Override
		public void handleMessage(Message msg) {
          //������Ϣ
      	  switch(msg.what){
      	  case SOFTUPDATE_INFO_CODE:
      		  //�������֪ͨ
      		  initSoftUpdateNotification();
      		  break;
      	  case SERVER_INFO_CODE:
      		 //��������Ϣ
      		Intent i = new Intent(AppdearService.this,
      				ShowServerInfoDialog.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
  			//BackgroundService.this.startActivity(i);
  			break;
  		  //��Ƥ�ͻ��˸���
      	  case CLIENT_UPDATE_INFO:
      		if(AppContext.getInstance().info==null)return;
      		final String updateurl = AppContext.getInstance().info.updateurl;
      		if (updateurl != null && !updateurl.trim().equals("")) {
				if (Constants.DEBUG)
					Log.i("jineefo", updateurl);
				initClientUpdateNotification();
//				Intent i1 = new Intent(BackgroundService.this,
//	      				ShowServerInfoDialog.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//	      				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//	  			BackgroundService.this.startActivity(i1);
      		}
      		break;
      	  }
        }
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	
	public void doWork(){
		//���������ʾ����
//		boolean softupdateTag = SharedPreferencesControl.getInstance().getBoolean(
//				"softUpdateTip", com.appdear.client.commctrls.Common.SETTINGS,AppdearService.this);
		/**
		 * �������
		 */
	//	if(softupdateTag){
			if(flag==false){
				flag=true;
				new Thread(new GetUpdateInfoThread()).start();
			}
	//		if(Constants.DEBUG)Log.i("softupdateTag","BackgroundService  softupdateTag:"+softupdateTag);
	//	}
//		/**
//		 * �г�����
//		 */
		if (null != updateinfo) {
			new Thread(new GetAppdearClientInfo()).start();
		}
		/**
		 * ������Ϣ
		 */
//	new Thread(new GetServerInfoThread()).start();
		
	}
	
 
	
}
