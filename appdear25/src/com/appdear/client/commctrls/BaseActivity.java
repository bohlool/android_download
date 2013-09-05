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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.appdear.client.AlertDialogView;
import com.appdear.client.MoreFeedBackActivity;
import com.appdear.client.MoreHelpMainActivity;
import com.appdear.client.MoreSettingsActivity;
import com.appdear.client.MoreUserCenterMainActivity;
import com.appdear.client.MoreUserLoginInActivity;
import com.appdear.client.NetworkStateReceiver;
import com.appdear.client.R;
import com.appdear.client.UpdateDialog;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.AppdearService;
import com.appdear.client.service.Constants;
import com.appdear.client.utility.ServiceUtils;

import dalvik.system.VMRuntime;

/**
 * ���ڻ���
 * 
 * @author zqm
 */
public abstract class BaseActivity extends Activity {
	
	public static WeakReference<Bitmap> weakRerference; 
//	protected Toast mScreenHint;
	public static Menu menu;
	
	/**
	 * �Ƿ���Ҫˢ�½���
	 */
	public static boolean isrefreshUI = false;
	
	/**
	 * handler����
	 */
	public static Handler downloadUpdateHandler;

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
	 * �޸��ղ�״̬tag
	 */
	public final static int UPDATE_FAVORITE = 110;
	
	/**
	 * �ո��޸��ղ�״̬tag
	 */
	public final static int JUST_NOW_UPDATE_FAVORITE = 111;
	
	/**
	 * �޸�δ�ղ�״̬tag
	 */
	public final static int UPDATE_UNFAVORITE = 112;
	
	/**
	 * �ص�״̬
	 */
	public final static int CALL_STATUS = 114;
	
	/**
	 * ��ʾˢ�°�ť
	 */
	public final static int SHOW_REFRESH_BUTTON = 113;
	
	/**
	 * list���������ذ�ťˢ��
	 */
	public final static int UPDATE_DOWNLOAD=115;
	
	/**
	 * ����΢���ɹ�
	 */
	public final static int SHARE_WEIBO_SUCESS=116;
	/**
	 * ����΢��ʧ��
	 */
	public final static int SHARE_WEIBO_FAILURE=117;
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
	/**
	 * ���������type
	 */
	public int cateGoryChildType = -1;
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

	private final static float TARGET_HEAP_UTILIZATION = 0.75f;

	/**
	 * menu�Ƿ���سɹ�
	 */
	public boolean isMenuLoad = true;

	public static Vector<BaseActivity> list = new Vector<BaseActivity>();

	public static int num = 0;
	private ExecutorService pool = Executors.newCachedThreadPool();
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
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
						 	AppContext.getInstance().isUpdateStarted=false;
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
						intent1.setClass(BaseActivity.this, UpdateDialog.class);
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
			if (getParent() == null) 
				ServiceUtils.exitSystem(this);
			else
				ServiceUtils.exitSystem(getParent());
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * ����UI�����߳�
	 */
	public void initThread() {
 		if (AppContext.getInstance().isNetState) {
 			new LoadThread().start();
		} else {
			 Toast.makeText(this, "���������������״̬",Toast.LENGTH_SHORT).show();
			list.add(this);
			if (Constants.DEBUG)
				Log.i("BaseActivity", "������������:" + getNum());
			if (isUpdate) {
				//���ݲ�ͬ���������Ҫ���ص����
				pool.execute(new LoadThread());
			//	new LoadThread().start();
			}
		}
	}
	
	/**
	 * ��ʾˢ�°�ť
	 */
	public void showRefreshButton() {
		handler.sendEmptyMessage(SHOW_REFRESH_BUTTON);
	}

	/**
	 * ��ʾ�쳣��ʾ
	 * 
	 * @param e
	 *            �쳣��Ϣ
	 */
	public void showException(Exception e) {
		Message message = new Message();
		message.what = INFO_TAG;
		Bundle bundle = new Bundle();
		String error = e.getMessage();
		bundle.putString(INFO_KEY, error);
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
		message.what = INFO_TAG;
		Bundle bundle = new Bundle();
		bundle.putString(INFO_KEY, error);
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
					//	AsyLoadImageService.getInstance().getImageCache().clear();
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
			case SHOW_REFRESH_BUTTON:
				//��ʾˢ�°�ť
				final Button refreshButton = (Button) findViewById(R.id.refresh_button);
				if (refreshButton != null) {
					refreshButton.setVisibility(View.VISIBLE);
					refreshButton.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							refreshButton.setVisibility(View.GONE);
							new LoadThread().start();
						}
					});
				}
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
//			 Toast.makeText(this, "���ڼ�������....",Toast.LENGTH_SHORT).show();
//		}
	}

	/**
	 * ���½���
	 */
	protected void updateUIEnd() {
//		if (mScreenHint != null)
//			mScreenHint.cancel();
	}

	/**
	 * ����UI���ִ���
	 */
	protected void updateUIErr(String msg) {
//		if (mScreenHint != null)
//			mScreenHint.cancel();
		Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
//		mScreenHint.show();
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

	protected Object getDrawable(int flag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onDestroy() {
		/*if (mReceiver != null) {
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (!AppContext.getInstance().isNetState && NetworkStateReceiver.netStateDialog) {
			NetworkStateReceiver.netStateDialog = false;
			startActivity(new Intent(this,
					AlertDialogView.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("type",
					"open"));
			
		}
		super.onResume();
	}
	
	
}
