/**
 * BaseActivity.java
 * created at:2011-5-10����05:21:53
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */
package com.appdear.client.commctrls;

import java.lang.ref.WeakReference;
import java.util.Vector;
import java.util.concurrent.AbstractExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.appdear.client.MoreFeedBackActivity;
import com.appdear.client.MoreHelpMainActivity;
import com.appdear.client.MoreSettingsActivity;
import com.appdear.client.MoreUserCenterMainActivity;
import com.appdear.client.MoreUserLoginInActivity;
import com.appdear.client.R;
import com.appdear.client.UpdateDialog;
import com.appdear.client.commctrls.BaseActivity.LoadThread;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.Constants;
import com.appdear.client.utility.ServiceUtils;
import dalvik.system.VMRuntime;

/**
 * ���ڻ���
 * 
 * @author zqm
 */
public abstract class BaseGroupActivity extends ActivityGroup  {

	public static final String SETTINGS_T = "����";
	public static final String HELP_DOC = "����";
	public static final String FEEDBACK_T = "����";
	public static final String EXIT = "�˳�";
	public static WeakReference<Bitmap> weakRerference; 
//	protected Toast mScreenHint;
	public static Menu menu;
	protected static int width=0;
	protected static int height=0;
	protected MProgress loadingView;
	// MENU ID

	/**
	 * MENU ID--����
	 */
	public static final int SETTINGS_ID = Menu.FIRST;
	
	/**
	 * MENU ID -- �г�����
	 */
	public static final int UPDATE_MARKET_ID = Menu.FIRST + 1;

	/**
	 * MENU ID -- ��Ա
	 */
	public static final int USER_ID = Menu.FIRST + 2;
	
	/**
	 * MENU ID -- ����
	 */
	public static final int HELP_DOC_ID = Menu.FIRST + 3;

	/**
	 * MENU ID -- ����
	 */
	public static final int FEEDBACK_ID = Menu.FIRST + 4;

	/**
	 * MENU ID -- �˳�
	 */
	public static final int EXIT_ID = Menu.FIRST + 5;

	// ����UI��TAG
	/**
	 * ��ʼTag
	 */
	public static final int START_TAG = 0;
	
	/**
	 * 
	 */
	public static final int UPDATE = 3;
	
	/**
	 * ����Tag
	 */
	public static final int END_TAG = 1;

	/**
	 * �쳣Tag
	 */
	public static final int ERR_TAG = 2;

	/**
	 * ��ʾ��Ϣ
	 */
	public static final int INFO_TAG = 4;

	/**
	 * ֮����Ҫ�ػ�ui�Ĳ���
	 */
	public static final int REFRESH_UI = 5;

	/**
	 * 
	 */
	public final static int MNUEHANDLERLONGIN = 6;

	/**
	 * 
	 */
	public final static int MNUEHANDLERLONGOUT = 7;
	
	/**
	 * 
	 */
	public final static int LOADV = 11;
	
	/**
	 * 
	 */
	public final static int LOADG = 12;
	/**
	 * ������Ϣ��key
	 */
	public final String ERR_INFO_KEY = "err";

	/**
	 * ��ʾ��Ϣ��key
	 */
	public final String INFO_KEY = "info";

	/**
	 * �Ƿ���UI������Ҫ����
	 */
	public boolean isUpdate = false;

	/**
	 * ����������type
	 */
	public static int moreChildType = -1;
	public static int userChildType = -1;
	public static int searchResultChildType = -3;

	public int resource;
	/**
	 * ��ǰ��������
	 */
	public static int currentType = 0;

	/**
	 * �Ƿ���ʾ���ڼ�������
	 */
	public   boolean isShowAlert = true;

	/**
	 * 
	 */
	public boolean bgshow = false;

	public IntentFilter filter;

	/**
	 * �Ƿ��������Ի���
	 */
	public static boolean netStateDialog = true;

	/**
	 * �̳߳�
	 */
//	AbstractExecutorService pool=new ThreadPoolExecutor(10,20,15L,TimeUnit.SECONDS,new SynchronousQueue(),new ThreadPoolExecutor.DiscardOldestPolicy());

	

	//protected ExecutorService pool = Executors.newFixedThreadPool(10);
	private ExecutorService pool = Executors.newCachedThreadPool();
	private final static float TARGET_HEAP_UTILIZATION = 0.75f;

	/**
	 * menu�Ƿ���سɹ�
	 */
	public boolean isMenuLoad = true;
	
	public static Vector<BaseGroupActivity> list = new Vector<BaseGroupActivity>();

	public static int num = 0;

	public static synchronized int getNum() {
		return num;
	}

	public static synchronized void addNum() {
		num++;
	}
	public static synchronized void subNum() {
		num--;
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION); 
		init();
		initThread();
	}

	public abstract void init();

	@Override
	protected void onStart() {
	/*	filter = new IntentFilter();
		filter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, filter);*/
		super.onStart();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (Constants.DEBUG)
			if (Constants.DEBUG)
				Log.i("keyEvent", "event:" + event.getAction() + ",keyCode"
						+ keyCode);
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, SETTINGS_ID, 0, "����").setIcon(R.drawable.sz);
		menu.add(0, UPDATE_MARKET_ID, 1, "�汾����").setIcon(R.drawable.scsj);
		menu.add(0, HELP_DOC_ID, 2, "����").setIcon(R.drawable.help);
		menu.add(0, USER_ID, 1, "��Ա").setIcon(R.drawable.menu_user);
		menu.add(0, FEEDBACK_ID, 3, "����").setIcon(R.drawable.fk);
		menu.add(0, EXIT_ID, 4, "�˳�").setIcon(R.drawable.exit);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item == null)
			return false;
		if (Constants.DEBUG)
			Log.i("onOptionsItemSelected", "event" + item.getItemId());
		switch (item.getItemId()) {
		case SETTINGS_ID:
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(this, MoreSettingsActivity.class);
			startActivity(intent);
			break;
		case UPDATE_MARKET_ID:
			//��Ƥ����
			if (AppContext.getInstance().info == null) {
				showMessageInfo("��ƤӦ�������������°汾�������");
			} else {
				if (AppContext.getInstance().isUpdateStarted) {
					 if (null==ServiceUtils.getSDCardUrl()){
							Toast.makeText(this,"����SD�������ڣ����߲��벻��ȷ��",Toast.LENGTH_LONG).show();
					 }
					 else {
						 showMessageInfo("�����ѿ�ʼ");
					 }
				} else {
					final String updateurl = AppContext.getInstance().info.updateurl;
					if (updateurl != null && !updateurl.trim().equals("")) {
						if (Constants.DEBUG)
							Log.i("jineefo", updateurl);
						Intent intent1 = new Intent();
						intent1.setClass(BaseGroupActivity.this, UpdateDialog.class);
						intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent1.putExtra("info", AppContext.getInstance().info);
						startActivity(intent1);
					} else {
						showMessageInfo("��ƤӦ�������������°汾�������");
					}	
				}
			}
			break;
		case USER_ID:
			if (!ServiceUtils.checkLogin(this, false)) {
				//�û�δ��¼����ʾ��¼����
				intent = new Intent(this,
						MoreUserLoginInActivity.class).addFlags(
						Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra(
						Common.USERLOGINFLAG, "true");
				startActivity(intent);
			} else {
				//�û��ѵ�¼,��ʾ��Ա������
				intent = new Intent(this,
						MoreUserCenterMainActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
			break;
		case HELP_DOC_ID:
			intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(this, MoreHelpMainActivity.class);
			startActivity(intent);
			break;
		case FEEDBACK_ID:
			intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(this, MoreFeedBackActivity.class);
			startActivity(intent);
			break;
		case EXIT_ID:
			ServiceUtils.exitSystem(this);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * ����UI�����߳�
	 */
	public void initThread() {
 		if (AppContext.getInstance().isNetState) {
		//	pool.execute(new LoadThread());
 			new LoadThread().start();
		} else {
			 Toast.makeText(this, "���������������״̬",Toast.LENGTH_SHORT).show();
			list.add(this);
			if (Constants.DEBUG)
				Log.i("BaseActivity", "������������:" + getNum());
			if (isUpdate) {
				//���ݲ�ͬ���������Ҫ���ص����
				pool.execute(new LoadThread());
				//new LoadThread().start();
			}
		}
	}

	/**
	 * ��ʾ�쳣��ʾ
	 * 
	 * @param e
	 *            �쳣��Ϣ
	 */
	public void showException(Exception e) {
		Message message = new Message();
		message.what = 2;
		Bundle bundle = new Bundle();
		String error = e.getMessage();
		bundle.putString(ERR_INFO_KEY, error);
		message.setData(bundle);
		handler.sendMessage(message);
	}

	/**
	 * ��ʾ�쳣��Ϣ
	 * 
	 * @param error
	 *            �쳣��ʾ��Ϣ
	 */
	public void showException(String error) {
		Message message = new Message();
		message.what = 2;
		Bundle bundle = new Bundle();
		bundle.putString(ERR_INFO_KEY, error);
		message.setData(bundle);
		handler.sendMessage(message);
	}

	public void showMessageInfo(String info) {
		Message message = new Message();
		message.what = INFO_TAG;
		Bundle bundle = new Bundle();
		bundle.putString(INFO_KEY, info);
		message.setData(bundle);
		handler.sendMessage(message);
	}

	public void refreshUI(String tag, String info) {
		Message message = new Message();
		message.what = REFRESH_UI;
		Bundle bundle = new Bundle();
		bundle.putString(tag, info);
		message.setData(bundle);
		handler.sendMessage(message);
	}

	public class LoadThread extends  Thread {
		@Override
		public void run() {
			handler.sendEmptyMessage(START_TAG);
			initData();
			handler.sendEmptyMessage(UPDATE);
			handler.sendEmptyMessage(END_TAG);
		}
	}

	/**
	 * ����UI
	 * 
	 * @author
	 */
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case START_TAG:
				updateUIStart();
				break;
			case UPDATE:
				try {
					updateUI();
				} catch (OutOfMemoryError e) {
						//AsyLoadImageService.getInstance().getImageCache().clear();
 						e.printStackTrace();
						Log.e("load image", "�ڴ������");
				}
			case END_TAG:
				updateUIEnd();
				break;
			case ERR_TAG:
				updateUIErr(msg.getData().getString(ERR_INFO_KEY));
				break;
			case INFO_TAG:
				updateInfo(msg.getData().getString(INFO_KEY));
				break;
			case REFRESH_UI:
				refreshUI(msg.getData());
				break;
			case LOADV:
				if(loadingView!=null)loadingView.setVisibility(View.VISIBLE);
				break;
			case LOADG:
				if(loadingView!=null)loadingView.setVisibility(View.GONE);
				break;
			}
		}
	};

	/**
	 * ���س�ʼ������
	 */
	public void initData() {

	}

	/**
	 * ����UI
	 */
	public void updateUI() {
	}

	/**
	 * ��ʼ����
	 */
	protected void updateUIStart() {
//		if (isShowAlert) {
//			if (mScreenHint != null)
//				mScreenHint.cancel();
//			mScreenHint = Toast.makeText(this, "���ڼ�������....",Toast.LENGTH_SHORT);
//			mScreenHint.show();
//		}
	}

	/**
	 * ���½���
	 */
	protected void updateUIEnd() {
		
	}

	/**
	 * ����UI���ִ���
	 */
	protected void updateUIErr(String msg) {
		if (isShowAlert) {
//			if (mScreenHint != null)
//				mScreenHint.cancel();
			 Toast.makeText(this, "���ش���" + msg,Toast.LENGTH_SHORT).show();
//		mScreenHint.show();
		}
	}

	/**
	 * ������ʾ��Ϣ
	 * 
	 * @param msg
	 */
	public void updateInfo(String msg) {
//		if (mScreenHint != null)
//			mScreenHint.cancel();
		 Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
//		mScreenHint.show();
	}

	/**
	 * ˢ�½���
	 */
	public void refreshUI(Bundle budle) {
	}



	/*protected BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Constants.DEBUG)
				Log.i("BaseActivity", "action=" + action);
			if (action
					.equals(android.net.ConnectivityManager.CONNECTIVITY_ACTION)) {
				// ����������ӷ���
				boolean checkState = ServiceUtils.checkNetState(context);
				if (AppContext.getInstance().isNetState != checkState)
					netStateDialog = true;
				AppContext.getInstance().isNetState = checkState;
				if (AppContext.getInstance().isNetState) {
					netStateDialog = false;
					startActivity(new Intent(BaseGroupActivity.this,
							AlertDialogView.class).putExtra("type", "close"));
				}

				Log.e("ConnectivityReceiver",
						"isState:" + AppContext.getInstance().isNetState);
				if (!AppContext.getInstance().isNetState && netStateDialog) {
					netStateDialog = false;
					startActivity(new Intent(BaseGroupActivity.this,
							AlertDialogView.class).setFlags(
							Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("type",
							"open"));
				} else {
					startActivity(new Intent(BaseGroupActivity.this,
							AlertDialogView.class).putExtra("type", "close"));
				}

				// ��ͣ��ǰ�����������ص��߳�
				if (!AppContext.getInstance().isNetState)
					AppContext.getInstance().downloader.downDb.updatePause();

				if (AppContext.getInstance().isNetState) {
					for (int i = list.size() - 1; i >= 0; i--) {
						list.get(i).initThread();
						Log.i("refresh", "refresh:" + i);
					}
					list.clear();
				}
			}
			this.clearAbortBroadcast();
		}
	};*/

	

	protected Object getDrawable(int flag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onDestroy() {
	/*	if (mReceiver != null) {
			unregisterReceiver(mReceiver);
		}*/
		subNum();
		Log.i("num",getNum()+"=onDestroy");

//		if (mScreenHint != null)
//			mScreenHint.cancel();
		System.gc();
		super.onDestroy();
	}

	@Override
	public void overridePendingTransition(int enterAnim, int exitAnim) {
		super.overridePendingTransition(0, 0);
	}
	
}
