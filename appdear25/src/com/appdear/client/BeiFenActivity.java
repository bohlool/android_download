package com.appdear.client;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdear.client.ContactOperateActivity.DialogListener;
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

public class BeiFenActivity extends BaseActivity implements
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
	InstalledBackupControler installedControler=new InstalledBackupControler();
	TextView tag1=null;
	LinearLayout back,retore;
	ImageButton btn_return;
	private LinearLayout tab_ll_linear;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		installedControler.setActivity(this,myHandler);
		setContentView(R.layout.beifen);
		ScreenManager.getScreenManager().pushActivity(this);// ��ջ
 		Intent intent=this.getIntent();
 		String tag=intent.getStringExtra("backup");
 		back=(LinearLayout) this.findViewById(R.id.beifen);
 		retore=(LinearLayout) this.findViewById(R.id.huanyuan);
 		tag1=(TextView)this.findViewById(R.id.tv_navigation);
 		if(tag==null||tag.equals("")){
 			retore.setVisibility(View.VISIBLE);
 			back.setVisibility(View.GONE);
 			tag1.setText("�ƻ�ԭ");
 		}else if(tag.equals("true")){
 			back.setVisibility(View.VISIBLE);
 			retore.setVisibility(View.GONE);
 			tag1.setText("�Ʊ���");
 		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		Button Button01 = (Button) findViewById(com.appdear.client.R.id.Button01);
		Button Button02 = (Button) findViewById(com.appdear.client.R.id.Button02);
		Button01.setOnClickListener(this);
		Button02.setOnClickListener(this);
		Button btn_smsback = (Button) findViewById(R.id.btn_smsback);
		Button btn_smsrestore = (Button) findViewById(R.id.btn_smsrestore);
		btn_smsback.setOnClickListener(this);
		btn_smsrestore.setOnClickListener(this);
		installedControler.initView();
		tab_ll_linear = (LinearLayout) findViewById(R.id.ll_navigation);
			tab_ll_linear.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		btn_return = (ImageButton) findViewById(R.id.btn_return);
		   btn_return.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});	
	//	myRunnable = new GetRecommendListRunnble();
	//	new Thread(myRunnable).start();
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.Button01:
			createDialog(BACK_INFO,new DialogListener() {
				@Override
				public void postListener() {
					backupContact();
				}
			});
			break;
		case R.id.Button02:
			createDialog(RESTORE_INFO,new DialogListener() {
				@Override
				public void postListener() {
					recoverContact();
				}
			});
			break;
	
		case R.id.installed_backup:
			if (!AppContext.getInstance().isNetState) {
				showMessageInfo("������󣬲��ܽ��иò���");
				return;
			}
			createDialog(BACK_INFO,new DialogListener() {
				@Override
				public void postListener() {
					installedControler.clickBackup();
				}
			});
			
			break;
		case R.id.installed_restore:
			if (!AppContext.getInstance().isNetState) {
				showMessageInfo("������󣬲��ܽ��иò���");
				return;
			}
			createDialog(RESTORE_INFO,new DialogListener() {
				@Override
				public void postListener() {
					installedControler.clickRestore();
				}
			});
			
			break;
		case R.id.btn_smsback:
			createDialog(BACK_INFO,new DialogListener() {
				@Override
				public void postListener() {
					sms_back();
				}
			});
			break;
		case R.id.btn_smsrestore:
			createDialog(RESTORE_INFO,new DialogListener() {
				@Override
				public void postListener() {
					sms_recover();
				}
			});
			
			break;
		default:
			break;
		}
	}

	private void recoverContact() {
		dialog();
		if (!AppContext.getInstance().isNetState) {
			showMessageInfo("������󣬲��ܽ��иò���");
			return;
		}
//		if (!ServiceUtils.checkLogin(BeiFenActivity.this, true)) {
//			SoftwareMainDetilActivity.isClickFavorite = true;
//			return;
//		} else {
			// ��ProgressDialog��ʾ
			xh_pDialog.show();
			new Thread() {
				@Override
				public void run() {
					try {
						String userid = SharedPreferencesControl
								.getInstance()
								.getString(
										"userid",
										com.appdear.client.commctrls.Common.USERLOGIN_XML,
										BeiFenActivity.this);
						//String imei = ServiceUtils.getIMEI(BeiFenActivity.this);
						ApiNormolResult apiSoftListResult = ApiManager
								.recovercontact(userid);
						
						String contact = apiSoftListResult.contact;
//						contact = JsonUtil.uncompress(contact);
						Log.i("gefy", "o===="+apiSoftListResult.isok);
						if (apiSoftListResult.isok == 2) {
							xh_pDialog.cancel();
							showMessageInfo("�ƶ�û���κα������ݣ����ȱ��ݣ�");
						}else {
							JSONObject contactJson = new JSONObject(contact);
							int i = ContactUtil.handlerContactAdd(
									BeiFenActivity.this,
									contactJson, 0);
							xh_pDialog.cancel();
							if (apiSoftListResult.isok == 1) {
								if(i == 0){
									showMessageInfo("����û�б仯�����軹ԭ!");
									return;
								}
								showMessageInfo("��ϲ��!�ѳɹ���ԭ��¼" + i + "��");
							} else
								showMessageInfo("���ݻ�ԭʧ�ܣ�");
						}
					} catch (Exception e) {
						xh_pDialog.cancel();
						showMessageInfo("���ݻ�ԭʧ�ܣ�");
						e.printStackTrace();
					}
				}
			}.start();
		}
//	}

	private void backupContact() {
		dialog();
		if (!AppContext.getInstance().isNetState) {
			showMessageInfo("������󣬲��ܽ��иò���");
			return;
		}
		/*if (!ServiceUtils.checkLogin(BeiFenActivity.this, true)) {
			SoftwareMainDetilActivity.isClickFavorite = true;
			return;
		} else {*/
			// ��ProgressDialog��ʾ
			xh_pDialog.show();
			new Thread() {
				@Override
				public void run() {
					try {
   
						String userid = SharedPreferencesControl
								.getInstance()
								.getString(
										"userid",
										com.appdear.client.commctrls.Common.USERLOGIN_XML,
										BeiFenActivity.this);
						String imei = ServiceUtils.getIMEI(BeiFenActivity.this);
						ApiNormolResult apiSoftListResult = ApiManager
								.backupcontact(userid,
										BeiFenActivity.this);
						
						if(apiSoftListResult == null || apiSoftListResult.contactcount.equals("0")){
							sleep(1000);
							xh_pDialog.cancel();
							showMessageInfo("�ֻ���û�����ݣ��޷����ݣ�");
							return;
						}
						xh_pDialog.cancel();
						if (apiSoftListResult == null || apiSoftListResult.isok == 1) 
							showMessageInfo("�Ѿ��ɹ����ݣ��ѱ�����������" + apiSoftListResult.contactcount + "��");
						else
							showMessageInfo("��������ʧ�ܣ�");
					} catch (Exception e) {
						xh_pDialog.cancel();
						showMessageInfo("��������ʧ�ܣ�");
						e.printStackTrace();
					}
				}
			}.start();
		//}
	}

	// xhButton01�ļ�������
	class Bt1DialogListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// �����ȷ������ťȡ���Ի���
			dialog.cancel();
		}
	}
	/**
	 * sms back
	 */
	private void sms_back(){
		dialog();
		if (!AppContext.getInstance().isNetState) {
			showMessageInfo("������󣬲��ܽ��иò���");
			return;
		}
		xh_pDialog.show();
		new Thread() {
			@Override
			public void run() {
				try {

					String userid = SharedPreferencesControl
							.getInstance()
							.getString(
									"userid",
									com.appdear.client.commctrls.Common.USERLOGIN_XML,
									BeiFenActivity.this);
					//String imei = ServiceUtils.getIMEI(BeiFenActivity.this);
					String imsi = ServiceUtils.getSimsi(BeiFenActivity.this);
					ApiNormolResult smsResult = ApiManager
							.backupsms(userid,imsi, BeiFenActivity.this);

					
					if(smsResult == null || smsResult.contactcount.equals("0")){
						sleep(1000);
						xh_pDialog.cancel();
						showMessageInfo("�ֻ���û�����ݣ��޷����ݣ�");
						return;
					}
					xh_pDialog.cancel();
					if (smsResult == null || smsResult.isok == 1){
						showMessageInfo("�Ѿ��ɹ����ݣ��ѱ�����������"
								+ smsResult.contactcount + "��");
					}
					else{
						showMessageInfo("��������ʧ�ܣ�");
					}
				} catch (Exception e) {
					xh_pDialog.cancel();
					showMessageInfo("��������ʧ�ܣ�");
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * sms recover
	 */
	private void sms_recover(){
		dialog();
		if (!AppContext.getInstance().isNetState) {
			showMessageInfo("������󣬲��ܽ��иò���");
			return;
		}xh_pDialog.show();
		new Thread() {
			@Override
			public void run() {
				try {
					String userid = SharedPreferencesControl
							.getInstance()
							.getString(
									"userid",
									com.appdear.client.commctrls.Common.USERLOGIN_XML,
									BeiFenActivity.this);
					//String imei = ServiceUtils.getIMEI(BeiFenActivity.this);
					String imsi = ServiceUtils.getSimsi(BeiFenActivity.this);
					ApiNormolResult smsResult = ApiManager.recoversms(userid,imsi);
					
					String sms = smsResult.sms;
					//System.out.println("recorve"+sms);
//					contact = JsonUtil.uncompress(contact);
					Log.i("gefy", "o===="+smsResult.isok);
					if (smsResult.isok == 2) {
						xh_pDialog.cancel();
						showMessageInfo("�ƶ�û���κα������ݣ����ȱ��ݣ�");
					}else {
						//JSONObject contactJson = new JSONObject(contact);
						//int i = ContactUtil.handlerContactAdd(
						int i = SmsUtil.smsRestroe(BeiFenActivity.this,sms);
						
						xh_pDialog.cancel();
						if(i==0){
							showMessageInfo("����û�б仯�����軹ԭ!");
						}else{
							showMessageInfo("��ϲ��!�ѳɹ���ԭ����"+ i +"��");
						}
						//if (smsResult.isok == 1) {
							
//						} else
//							showMessageInfo("���ݻ�ԭʧ�ܣ�");
					}
				} catch (Exception e) {
					xh_pDialog.cancel();
					showMessageInfo("���ݻ�ԭʧ�ܣ�");
					e.printStackTrace();
				}
			}
		}.start();
	}
	public interface DialogListener{
		void postListener();
		//void negaListener();
	}
	private void createDialog(String message ,final DialogListener dialogListener){
		AlertDialog.Builder builder = new Builder(BeiFenActivity.this);
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
			case  INSTALLED_BACKUP: //��װ�б���
				xh_pDialog.cancel();
				installedControler.handleBackup();
				break;
			case  INSTALLED_RESTORE: //��װ�б���
				xh_pDialog.cancel();
				installedControler.handleRestore();
				break;
			case  INSTALLED_FAIL:
				xh_pDialog.cancel();
				break;
			}
		}
	};
	
}
