package com.appdear.client;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appdear.client.commctrls.BaseActivity;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.exception.ApiException;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.Constants;
import com.appdear.client.service.SoftFormTags;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.service.api.ApiUserResult;
import com.appdear.client.utility.ServiceUtils;
import com.appdear.client.utility.VaildValue;

/**
 * �û���¼��Ϣ
 * @author jdan
 *
 */
public class MoreUserLoginInActivity extends BaseActivity  {
	
	private EditText user_login_in_01;//ע������༭��
	private EditText user_login_in_02;//��������༭��
	 
	private TextView image_login_in_button;//��¼�߼���ť
	
	private TextView user_text_login_02;
	private ApiUserResult result=null;
	private CheckBox savepass=null;
//	private LinearLayout denglu_layout;
	private DisplayMetrics mDisplayMetrics;
	private int FINDPASS=150;
	private int counts=0;
	private	long start;
	public static boolean iskeyShowFlag;
	 
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.new_more_user_login_resin);
		mDisplayMetrics=new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
//		denglu_layout.setLayoutParams(new  LinearLayout.LayoutParams(mDisplayMetrics.widthPixels,(int) (mDisplayMetrics.heightPixels*2/(float)14))); 
		
	}
	
	@Override
	public void init() {
		//��Ա��¼����Ļ�Աע��
	user_text_login_02=(TextView) findViewById(R.id.user_text_login_02);
//		denglu_layout=(LinearLayout) findViewById(R.id.denglu_layout);
		//��¼�߼���ť
		image_login_in_button=(TextView) findViewById(R.id.image_login_in_button);
	 
		//ע��༭��
		user_login_in_01=(EditText) findViewById(R.id.user_login_in_01);
		user_login_in_02=(EditText) findViewById(R.id.user_login_in_02);
//		 user_login_in_01.setOnClickListener(this);
//		 user_login_in_02.setOnClickListener(this);
		 
		user_login_in_01.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//����̵���״̬
				if (v.getId() == R.id.user_login_in_01||v.getId() == R.id.user_login_in_02) {
					iskeyShowFlag = true;
					
				}
				return false;
			}
		});
		user_login_in_02.setOnTouchListener(new OnTouchListener() {		
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//����̵���״̬
				if (v.getId() == R.id.user_login_in_01||v.getId() == R.id.user_login_in_02) {
					iskeyShowFlag = true;
					
				}
				return false;
			}
		});
		//�´��Զ�����
		savepass=(CheckBox)findViewById(R.id.login_in_checkbox);
		initUi();
	}
	
	@Override
	public void initData() {
		user_text_login_02.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(MoreUserLoginInActivity.this, MoreUserRegActivity.class);
//				startActivity(intent);
				startActivityForResult(intent, 1000);
			}
		});
		 
		//��¼�߼���ť
		image_login_in_button.setOnClickListener(new OnClickListener() {
	
		@Override
		public void onClick(View v) {
					hideInputMode();
					final String userAccount = user_login_in_01.getText().toString().trim();
					final String userPwd = user_login_in_02.getText().toString().trim();
					
					if(userAccount==null||"".equals(userAccount) ){
						Toast.makeText(MoreUserLoginInActivity.this, "�û�������Ϊ��", Toast.LENGTH_SHORT).show();
						user_login_in_01.requestFocus();
						return;
					}
					
					if (userPwd==null||"".equals(userPwd)) {
						Toast.makeText(MoreUserLoginInActivity.this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
						user_login_in_02.requestFocus();
						return;
					}
				if(VaildValue.Vaildlonginname(userAccount)==false){
					Toast.makeText(MoreUserLoginInActivity.this, "��������Ϲ�����û���", Toast.LENGTH_SHORT).show();
		//							û�������ʱ�򣬽����Զ��л��������
					user_login_in_02.requestFocus();
					
					return;
				}
				if (!AppContext.getInstance().isNetState) {
					Toast.makeText(MoreUserLoginInActivity.this, "���������������״̬", Toast.LENGTH_SHORT).show();
					return;
				}	
				new Thread(new Runnable() {
					@Override
					public void run() {
						login(userAccount,userPwd);
					}
				}).start();
			}
		});
		
		 
		
	 
		super.initData();
	}
	
	// ����������������
	private void hideInputMode(){
		InputMethodManager imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);   
		View view = MoreUserLoginInActivity.this.getCurrentFocus();   
		    if (view != null){   
		    	//  imm.showSoftInput(view, 0); //��ʾ�����   
		        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);//���������   
		    }  
	}
	private Handler handler1=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==50){
				Toast.makeText(MoreUserLoginInActivity.this, "�û������������,�����µ�¼��", Toast.LENGTH_SHORT).show();
			
			}else if(msg.what==100){
				Toast.makeText(MoreUserLoginInActivity.this, "������ϵͳ���ϣ�", Toast.LENGTH_LONG).show();
			}else if(msg.what==150){
				alertFindpass();
			}
		}
		
	};
	/**
	 * �һ����뵯��
	 */
	public void alertFindpass(){
		 LayoutInflater factory = LayoutInflater.from(MoreUserLoginInActivity.this);
	        //�õ��Զ���Ի���
	     final View DialogView = factory.inflate(R.layout.alert_user_findpass_layout, null);
	        
	        AlertDialog dialog = new AlertDialog.Builder(MoreUserLoginInActivity.this)
	        .setTitle("��ʾ")
	        .setView(DialogView)
	        .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
	            
	            public void onClick(DialogInterface dialog, int which) {
	            	String imsi=ServiceUtils.getSimsi(MoreUserLoginInActivity.this);
	            	if(imsi==null){
	            		handler1.sendEmptyMessage(110);
	            		return;
	            	}
	            	if(imsi.equals("")){
	            		imsi=SharedPreferencesControl.getInstance().getString("imsi",null,MoreUserLoginInActivity.this);
	            	}
	            	if(imsi.equals("")){
	            		handler1.sendEmptyMessage(170);
	            		return;
	            	}else{
	            		ServiceUtils.SmsSend(MoreUserLoginInActivity.this,imsi,AppContext.FindPasswdsmsMessage);
	            	}
				}
	        }).setNeutralButton("�˳�", null).create();
	        dialog.show();
	        
	    }

	private void login(String userAccount, String userPwd) {
		try {
			handler.sendEmptyMessage(0);
			result=ApiManager.userlogin(userAccount,userPwd);
			if(result!=null){
				if(result.isok != 1){
					this.user_login_in_01.setText("");
					this.user_login_in_02.setText("");
					handler1.sendEmptyMessage(50);
					
				}else{
					//��¼��ǰ�û���½��Ϣ
					
					SharedPreferencesControl.getInstance().putString("sessionid",result.token,com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					SharedPreferencesControl.getInstance().putString("userid",result.userid+"",com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					SharedPreferencesControl.getInstance().putString("nickname",result.nickname,com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					SharedPreferencesControl.getInstance().putString("username",this.user_login_in_01.getText().toString().trim(),com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					//��¼�û���¼��Ϣ
					if(this.savepass.isChecked()==true){
						SharedPreferencesControl.getInstance().putBoolean("issave",true,com.appdear.client.commctrls.Common.USERPASSWDXML,this);
						SharedPreferencesControl.getInstance().putString("passwd",this.user_login_in_02.getText().toString(),com.appdear.client.commctrls.Common.USERPASSWDXML,this);
					}else{
						SharedPreferencesControl.getInstance().clear(com.appdear.client.commctrls.Common.USERPASSWDXML, this);
					}
					SharedPreferencesControl.getInstance().putString("username",this.user_login_in_01.getText().toString().trim(),com.appdear.client.commctrls.Common.USERPASSWDXML,this);
					
					result=ApiManager.userprofile(SharedPreferencesControl.getInstance().getString("userid",com.appdear.client.commctrls.Common.USERLOGIN_XML,this),SharedPreferencesControl.getInstance().getString("sessionid",com.appdear.client.commctrls.Common.USERLOGIN_XML,this));
					SharedPreferencesControl.getInstance().putString("account",String.valueOf(result.account),com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					SharedPreferencesControl.getInstance().putString("level",result.level.equals("")?"��ƤС��":result.level,com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					SharedPreferencesControl.getInstance().putString("point",String.valueOf(result.totalpoint),com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					SharedPreferencesControl.getInstance().putString("gender",String.valueOf(result.userinfo.gender),com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					
					handler.sendEmptyMessage(END_TAG);
					Intent userIntent = new Intent();
					userIntent.setClass(this, MoreUserCenterMainActivity.class);
					startActivity(userIntent);	
					finish();
				}
			} else {
				this.user_login_in_01.setText("");
				this.user_login_in_02.setText("");
				handler1.sendEmptyMessage(100);
			}
		} catch (ApiException e) {
			if (Constants.DEBUG)Log.e("net error:",e.getMessage(), e);
			showException(e);
		}
	}
	
	private void initUi(){
		if(!(SharedPreferencesControl.getInstance().getString("username",com.appdear.client.commctrls.Common.USERPASSWDXML,MoreUserLoginInActivity.this)).equals("")){
			user_login_in_01.setText(SharedPreferencesControl.getInstance().getString("username",com.appdear.client.commctrls.Common.USERPASSWDXML,MoreUserLoginInActivity.this));
		}
		boolean checked= SharedPreferencesControl.getInstance().getBoolean("issave",com.appdear.client.commctrls.Common.USERPASSWDXML,this);
		if(checked==true){
			this.savepass.setChecked(true);
			String passwd=SharedPreferencesControl.getInstance().getString("passwd",com.appdear.client.commctrls.Common.USERPASSWDXML,this);
			user_login_in_02.setText(passwd);
		}
	}
	
	@Override
	protected void onResume() {
		//���������Ϊ����ʾ
		iskeyShowFlag = false;
		super.onResume();
	}
	
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1000) {
			//������ע�������ע��ɹ���رյ�ǰ��¼����
			if (resultCode == 2000)
				finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
	

