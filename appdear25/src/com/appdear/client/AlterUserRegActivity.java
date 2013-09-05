package com.appdear.client;

import com.appdear.client.commctrls.BaseActivity;
import com.appdear.client.commctrls.Common;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.exception.ApiException;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.service.api.ApiNormolResult;
import com.appdear.client.service.api.ApiUserResult;
import com.appdear.client.utility.ServiceUtils;
import com.appdear.client.utility.VaildValue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AlterUserRegActivity extends BaseActivity {

	private EditText user_login_in_01;//ע������༭��
	private EditText user_login_in_02;//��������༭��
//	private TextView findpswsn;//�һ�����
	private TextView image_login_in_button;//��¼�߼���ť
	
	private TextView user_text_login_01;
	private ApiUserResult result=null;
	private CheckBox savepass=null;
	
	private int counts=0;
	private long start;
	private int softid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_user_reg);
		softid=this.getIntent().getIntExtra("favorite",0);
	}
	
	@Override
	public void init() {
		//��¼�߼���ť
		image_login_in_button=(TextView) findViewById(R.id.image_login_in_button);
		//�һ�����
	//	findpswsn=(TextView)findViewById(R.id.findpsws);
		
		user_text_login_01=(TextView) findViewById(R.id.user_text_login_01);
		
		//ע��༭��
		user_login_in_01=(EditText) findViewById(R.id.user_login_in_01);
		user_login_in_02=(EditText) findViewById(R.id.user_login_in_02);
		
		//�´��Զ�����
		savepass=(CheckBox)findViewById(R.id.login_in_checkbox);
		initUi();
	}
	
	@Override
	public void initData() {
		//ע��
		user_text_login_01.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(AlterUserRegActivity.this, MoreUserRegActivity.class);
				startActivity(intent);
				AlterUserRegActivity.this.finish();
			}
		});

		//��¼�߼���ť
		image_login_in_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideInputMode();
				final String userAccount = user_login_in_01.getText().toString().trim();
				final String userPwd = user_login_in_02.getText().toString().trim();
				if(userAccount==null||userPwd==null||"".equals(userAccount)||"".equals(userPwd)){
					Toast.makeText(AlterUserRegActivity.this, "�û��������벻��Ϊ��", Toast.LENGTH_SHORT).show();
//				û�������ʱ�򣬽����Զ��л��������
//					user_login_in_02.requestFocus();
					return;
					}
				if(VaildValue.Vaildlonginname(userAccount)==false){
		           Toast.makeText(AlterUserRegActivity.this, "��������Ϲ�����û���", Toast.LENGTH_SHORT).show();
//							û�������ʱ�򣬽����Զ��л��������
		          user_login_in_02.requestFocus();
		
		         return;
	            }
				 if (!AppContext.getInstance().isNetState) {
						Toast.makeText(AlterUserRegActivity.this, "���������������״̬", Toast.LENGTH_SHORT).show();
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
	
	/** 
	 * ����������������
	 */
	private void hideInputMode(){
		InputMethodManager imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);   
		View view = AlterUserRegActivity.this.getCurrentFocus();   
		    if (view != null){   
		    	//  imm.showSoftInput(view, 0); //��ʾ�����   
		        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);//���������   
		    }  
	}
	
	private Handler handler1=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 50) {
				Toast.makeText(AlterUserRegActivity.this, "�û������������,�����µ�¼��", Toast.LENGTH_SHORT).show();
			} else if (msg.what == 100) {
				Toast.makeText(AlterUserRegActivity.this, "������ϵͳ���ϣ�", Toast.LENGTH_LONG).show();
			}
		}
	};
	

	private void login(String userAccount, String userPwd) {
		try {
			handler.sendEmptyMessage(0);
			result = ApiManager.userlogin(userAccount, userPwd);
			if (result != null) {
				if (result.isok != 1) {
					this.user_login_in_01.setText("");
					this.user_login_in_02.setText("");
					handler1.sendEmptyMessage(50);
				} else {
					//��¼��ǰ�û���½��Ϣ
					String token=result.token;
					String userid=result.userid;
					SharedPreferencesControl.getInstance().putString("sessionid",result.token,com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					SharedPreferencesControl.getInstance().putString("userid",result.userid+"",com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					SharedPreferencesControl.getInstance().putString("nickname",result.nickname,com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					SharedPreferencesControl.getInstance().putString("username",this.user_login_in_01.getText().toString(),com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					
					//��¼�û���¼��Ϣ
					if(this.savepass.isChecked()==true){
						SharedPreferencesControl.getInstance().putBoolean("issave",true,com.appdear.client.commctrls.Common.USERPASSWDXML,this);
						SharedPreferencesControl.getInstance().putString("passwd",this.user_login_in_02.getText().toString(),com.appdear.client.commctrls.Common.USERPASSWDXML,this);
					}else{
						SharedPreferencesControl.getInstance().clear(com.appdear.client.commctrls.Common.USERPASSWDXML, this);
					}
					SharedPreferencesControl.getInstance().putString("username",this.user_login_in_01.getText().toString(),com.appdear.client.commctrls.Common.USERPASSWDXML,this);
					
					result=ApiManager.userprofile(SharedPreferencesControl.getInstance().getString("userid",com.appdear.client.commctrls.Common.USERLOGIN_XML,this),SharedPreferencesControl.getInstance().getString("sessionid",com.appdear.client.commctrls.Common.USERLOGIN_XML,this));
					SharedPreferencesControl.getInstance().putString("account",String.valueOf(result.account),com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					SharedPreferencesControl.getInstance().putString("level",result.level.equals("")?"��ƤС��":result.level,com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					SharedPreferencesControl.getInstance().putString("point",String.valueOf(result.totalpoint),com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					SharedPreferencesControl.getInstance().putString("gender",String.valueOf(result.userinfo.gender),com.appdear.client.commctrls.Common.USERLOGIN_XML,this);
					
					if(softid>0){
						ApiNormolResult result = ApiManager.addfavorite(
								userid, token,softid + "");
						if (result.isok == 1) {
							this.showMessageInfo("�ղسɹ���");
						} else {
							this.showMessageInfo("�ղ�ʧ�ܣ�");
						}
					}
					handler.sendEmptyMessage(END_TAG);
					if(this.getIntent().getStringExtra(Common.USERLOGINFLAG)!=null&&this.getIntent().getStringExtra(Common.USERLOGINFLAG).equals("true")){
						//�û���Ϣ
					} else {
						AlterUserRegActivity.this.finish();
						AppContext.userreg=true;
						
					}
				} 
			} else {
				this.user_login_in_01.setText("");
				this.user_login_in_02.setText("");
				handler1.sendEmptyMessage(100);
			}
		} catch (ApiException e) {
			Log.e("net error:",e.getMessage(), e);
			showException(e);
		}
	}

	private void initUi() {
		if (!(SharedPreferencesControl.getInstance().getString("username",com.appdear.client.commctrls.Common.USERPASSWDXML,AlterUserRegActivity.this)).equals("")) {
			user_login_in_01.setText(SharedPreferencesControl.getInstance().getString("username",com.appdear.client.commctrls.Common.USERPASSWDXML,AlterUserRegActivity.this));
		}
	}

	@Override
	protected void updateUIEnd() {
		// TODO Auto-generated method stub
		
	}	
	
}
	

