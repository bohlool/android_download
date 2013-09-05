package com.appdear.client;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appdear.client.BeiFenActivity.DialogListener;
import com.appdear.client.commctrls.BaseActivity;
import com.appdear.client.commctrls.InstalledBackupControler;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.exception.ApiException;
import com.appdear.client.model.SoftlistInfo;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.service.api.ApiNormolResult;
import com.appdear.client.service.api.ApiSoftListResult;
import com.appdear.client.utility.ContactUtil;
import com.appdear.client.utility.ScreenManager;
import com.appdear.client.utility.ServiceUtils;
import com.appdear.client.utility.SmsUtil;

public class ContactOperateActivity extends BaseActivity implements
		OnClickListener {
	
	public static final int RECOMMEND_UPDATE = 0;
	public static final int DAREN_DETECT=1;
	public static final int INSTALLED_BACKUP=2;
	public static final int INSTALLED_RESTORE=3;
	public static final int INSTALLED_FAIL=4;
	private static final String BACK_INFO ="���Ƿ���Ҫ���ݣ�\n��¼��Ա�󱸷� ��֧���ڲ�ͬ�ֻ��л�ԭ��"; 
	private static final String RESTORE_INFO ="���Ƿ���Ҫ��ԭ��\n�绹ԭ��Ϣ�뱾��û�з����仯����������ԭ����"; 
	int xh_count = 0;
	// �����������Ի���
	ProgressDialog xh_pDialog;
	TextView detectButton; // ���button
	TextView darenNumView; // �Ѱ�װ����Ӧ��
	/*
	 * �������ˣ��û���װ�����Ϊ10������ʱ �м����ˣ��û���װ���Ϊ10-19ʱ �߼����ˣ��û���װ���Ϊ20-39ʱ
	 * �ռ���ң��û���װ���Ϊ40��������ʱ
	 */
	TextView darenLevelView; // �ȼ� 0 δ��� 1 ���� 2 �м� 3 �߼� 4�ռ����
	TextView darenWarnView; // ��ʾ��Ϣ
	ImageView darenImage;
//	Button darenUpdateButton;
//	GridView recommendListGrid; // �Ƽ�Ӧ��
	int darenInstalled;
	int darenLevel;
	/**
	 * �б�����
	 */
	private ApiSoftListResult result;

	private List<SoftlistInfo> listData = null;
	private int page = 1;
	/**
	 * ��ҳ��
	 */
	protected int PAGE_TOTAL_SIZE = 1;
	/**
	 * ÿҳ��ʾ10��
	 */
	protected int PAGE_SIZE = 8;
	int recommendCount; // �Ƽ�������
	List<String> recommendIconUrls = new ArrayList<String>();
	List<String> recommendNames = new ArrayList<String>();
//	GetRecommendListRunnble myRunnable = null;
	boolean isLoading = false;
	boolean isFirst = true;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contactoperate);
		ScreenManager.getScreenManager().pushActivity(this);// ��ջ
 		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		LinearLayout ll_personal_back = (LinearLayout) findViewById(R.id.ll_personal_back);
		LinearLayout ll_personal_restore = (LinearLayout) findViewById(R.id.ll_personal_restore);
		ll_personal_back.setOnClickListener(this);
		ll_personal_restore.setOnClickListener(this);
		LinearLayout ll_personal_guarantee = (LinearLayout) findViewById(R.id.ll_personal_guarantee);
		LinearLayout ll_personal_store_address = (LinearLayout) findViewById(R.id.ll_personal_store_address);
		ll_personal_guarantee.setOnClickListener(this);
		ll_personal_store_address.setOnClickListener(this);
//		Button btn_smsback = (Button) findViewById(R.id.btn_smsback);
//		Button btn_smsrestore = (Button) findViewById(R.id.btn_smsrestore);
//		btn_smsback.setOnClickListener(this);
//		btn_smsrestore.setOnClickListener(this);
		initDaren();
	//	myRunnable = new GetRecommendListRunnble();
	//	new Thread(myRunnable).start();
	}

	public void initDaren() {
		// �ֻ�����View
		// SharedPreferencesControl.getInstance().putString("password",
		// password,com.appdear.client.commctrls.Common.USERLOGIN_163_XML,MyApplication.getInstance());
		detectButton = (TextView) this.findViewById(R.id.detectButton);
		darenNumView = (TextView) this.findViewById(R.id.darenNumView);
		darenLevelView = (TextView) this.findViewById(R.id.darenLevelView);
		darenWarnView = (TextView) this.findViewById(R.id.darenWarnView);
		darenImage = (ImageView) this.findViewById(R.id.darenImage);
//		darenUpdateButton = (Button) this.findViewById(R.id.darenUpdateButton);
//		recommendListGrid = (GridView) this
//				.findViewById(R.id.recommendListGrid);
//		darenUpdateButton.setOnClickListener(this);
		detectButton.setOnClickListener(this);
		darenLevel = SharedPreferencesControl.getInstance().getInt(
				"darenLevel", null, this);
		darenInstalled = SharedPreferencesControl.getInstance().getInt(
				"darenInstalled", null, this);
//		System.out.println("-darenLevel=" + darenLevel + "  darenInstalled="+ darenInstalled);
		initDarenDetect();
	}

	public void initDarenDetect() {
		switch (darenLevel) {
		case 0:
			darenNumView.setVisibility(View.GONE);
 			darenLevelView.setText("����");
			darenWarnView.setText("����δ�����˵ȼ�����������⡱��ť��ʼ��⣡");
			darenImage.setImageResource(R.drawable.dr1);
			break;
		case 1:
			darenNumView.setVisibility(View.VISIBLE);
			darenWarnView.setVisibility(View.VISIBLE);
			darenNumView.setText("�Ѱ�װ�������" + darenInstalled + "��");
			darenLevelView.setText("�ȼ�����������");
			darenImage.setImageResource(R.drawable.dr1);
			darenWarnView
					.setText("������" + (10 - darenInstalled) + "��Ӧ�þͳ�Ϊ�м�������");
			break;
		case 2:
			darenNumView.setVisibility(View.VISIBLE);
			darenWarnView.setVisibility(View.VISIBLE);
			darenImage.setImageResource(R.drawable.dr2);
			darenNumView.setText("�Ѱ�װ�������" + darenInstalled + "��");
			darenWarnView
					.setText("������" + (20 - darenInstalled) + "��Ӧ�þͳ�Ϊ�߼�������");
			darenLevelView.setText("�ȼ����м�����");
			break;
		case 3:
			darenNumView.setVisibility(View.VISIBLE);
			darenWarnView.setVisibility(View.VISIBLE);
			darenImage.setImageResource(R.drawable.dr3);
			darenNumView.setText("�Ѱ�װ�������" + darenInstalled + "��");
			darenWarnView
					.setText("������" + (40 - darenInstalled) + "��Ӧ�þͳ�Ϊ�ռ������");
			darenLevelView.setText("�ȼ����߼�����");
			break;
		case 4:
			darenNumView.setVisibility(View.VISIBLE);
			darenWarnView.setVisibility(View.VISIBLE);
			darenImage.setImageResource(R.drawable.dr4);
			darenNumView.setText("�Ѱ�װ�������" + darenInstalled + "��");
			darenWarnView.setText("���Ѿ����ռ���ң������ʹ�ð�ƤӦ�����ء�");
			darenLevelView.setText("�ȼ����ռ����");
			break;
		}
	}

	 

	public void dialog() {
		if (xh_pDialog == null)
			xh_pDialog = new ProgressDialog(this);
		// ���ý�������񣬷��ΪԲ�Σ���ת��
		xh_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// ����ProgressDialog ����
		//xh_pDialog.setTitle("��ʾ");
		// ����ProgressDialog��ʾ��Ϣ
		xh_pDialog.setMessage("�����У����Ժ�.....");
		// ����ProgressDialog����ͼ��
//		xh_pDialog.setIcon(R.drawable.icon);
		// ����ProgressDialog �Ľ������Ƿ���ȷ false ���ǲ�����Ϊ����ȷ
		xh_pDialog.setIndeterminate(false);
		// ����ProgressDialog �Ƿ���԰��˻ؼ�ȡ��
		xh_pDialog.setCancelable(true);
		// ����ProgressDialog ��һ��Button
//		xh_pDialog.setButton("ȷ��", new Bt1DialogListener());
	}

	public void dialog_daren() {
		if (xh_pDialog == null)
			xh_pDialog = new ProgressDialog(this);
		// ���ý�������񣬷��ΪԲ�Σ���ת��
		xh_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// ����ProgressDialog ����
//		xh_pDialog.setTitle("��ʾ");
		// ����ProgressDialog��ʾ��Ϣ
		xh_pDialog.setMessage("���ڼ�������Ժ�");
		// ����ProgressDialog����ͼ��
		// xh_pDialog.setIcon(R.drawable.icon);
		// ����ProgressDialog �Ľ������Ƿ���ȷ false ���ǲ�����Ϊ����ȷ
		xh_pDialog.setIndeterminate(false);
		xh_pDialog.show();
		// ����ProgressDialog �Ƿ���԰��˻ؼ�ȡ��
		// xh_pDialog.setCancelable(true);
		// ����ProgressDialog ��һ��Button
		// xh_pDialog.setButton("ȷ��", new Bt1DialogListener());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.ll_personal_back:
			Intent intent=new Intent(this,BeiFenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("backup","true");
			this.startActivity(intent);
			break;
		case R.id.ll_personal_restore:
			Intent intent1=new Intent(this,BeiFenActivity.class);
			intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.startActivity(intent1);
			break;
		case R.id.detectButton:
			dialog_daren();
			new Thread() {
				public void run() {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					myHandler.sendEmptyMessage(DAREN_DETECT);
				};
			}.start();
			break;
		case R.id.ll_personal_guarantee:
			Intent i1=new Intent(this,MorePhoneStoreInfoActivity.class);
			i1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.startActivity(i1);
			break;
		case R.id.ll_personal_store_address:
			Intent i=new Intent(this,MorePhoneStoreActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.startActivity(i);
			break;
		default:
			break;
		}
	}

	// xhButton01�ļ�������
	class Bt1DialogListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// �����ȷ������ťȡ���Ի���
			dialog.cancel();
		}
	}
	
	public interface DialogListener{
		void postListener();
		//void negaListener();
	}
	private void createDialog(String message ,final DialogListener dialogListener){
		AlertDialog.Builder builder = new Builder(ContactOperateActivity.this);
		builder.setMessage(message);//��ȷ��Ҫ������
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				dialogListener.postListener();
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//dialogListener.negaListener();
			}
		});
		builder.create().show();
	}
	public Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DAREN_DETECT:  // ���˼��
				xh_pDialog.cancel();
				List<PackageInfo> packs = ContactOperateActivity.this.getPackageManager().getInstalledPackages(0);
			//	System.out.println("packs==="+packs.size());
				int j=0;
				for(int i=0;i<packs.size();i++) {   
			    	PackageInfo p = packs.get(i);
			    	ApplicationInfo appInfo = p.applicationInfo;
			    	if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
			    		//��ϵͳ
			    		//System.out.println("packageName="+appInfo.packageName+"     name="+appInfo.name);
			    		if(!"com.appdear.serviceforpc".equals(appInfo.packageName))
			    		{
			    			j++;
			    		}
			    		
			    	//	System.out.println(" not system :"+appInfo.packageName);
			    		 
			    	}else
			    	{
			    		//ϵͳ
				      //  System.out.println("  system :"+appInfo.packageName);
			    	}
				}
 				darenInstalled =  j;
				if (darenInstalled > 0 && darenInstalled <= 9) {
					darenLevel = 1;
				} else if (darenInstalled > 9 && darenInstalled < 20) {
					darenLevel = 2;
				} else if (darenInstalled >= 20 && darenInstalled < 40) {
					darenLevel = 3;
				} else {
					darenLevel = 4;
				}
				initDarenDetect();

				SharedPreferencesControl.getInstance().putInt("darenLevel",
						darenLevel, null, ContactOperateActivity.this);
				SharedPreferencesControl.getInstance().putInt("darenInstalled",
						darenInstalled, null, ContactOperateActivity.this);
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							ApiManager.daren();
						} catch (ApiException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
				
				break;
		}
	  }
	};
  }
