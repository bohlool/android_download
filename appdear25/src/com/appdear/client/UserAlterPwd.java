package com.appdear.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appdear.client.commctrls.BaseActivity;
import com.appdear.client.commctrls.Common;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.commctrls.UesrInfoLayout;
import com.appdear.client.exception.ApiException;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.service.api.ApiNormolResult;
import com.appdear.client.utility.ScreenManager;
import com.appdear.client.utility.VaildValue;

/**
 * �޸�����
 * @author zqm
 *
 */
public class UserAlterPwd extends BaseActivity {

	private EditText oldpwd = null;
	private EditText newpwd = null;
	private EditText confirmpwd = null;
	private RelativeLayout alterpwdButton = null;
	private boolean isOpenKeyBoard = false;
	private int num = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alter_pwd);
		ScreenManager.getScreenManager().pushActivity(this);//��ջ
	}
	
	
	@Override
	public void init() {
		oldpwd = (EditText) findViewById(R.id.oldpwd);
		oldpwd.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (arg1) {
					num ++;
					if (num == 2)
						isOpenKeyBoard = true;
				}
			}
		});
		newpwd = (EditText) findViewById(R.id.newpwd);
		newpwd.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (arg1) {
					isOpenKeyBoard = true;
				}
			}
		});
		confirmpwd = (EditText) findViewById(R.id.confirmpwd);
		confirmpwd.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (arg1) {
					isOpenKeyBoard = true;
				}
			}
		});
		alterpwdButton = (RelativeLayout) findViewById(R.id.click_button_update);
		alterpwdButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!AppContext.getInstance().isNetState) {
					Toast.makeText(UserAlterPwd.this, "���������������״̬", Toast.LENGTH_SHORT).show();
					return;
				}
				showMessageInfo("������֤...");
				if (oldpwd.getText().toString().equals("")) {
					showMessageInfo("�����������!");
					return;
				}
				
				if (!VaildValue.VaildPasswd(oldpwd.getText().toString())) {
					showMessageInfo("������������6-16���ַ�����ĸ!");
					return;
				}
				
				if (newpwd.getText().toString().equals("")) {
					showMessageInfo("������������!");
					return;
				}
				if (confirmpwd.getText().toString().equals("")) {
					showMessageInfo("������ȷ������!");
					return;
				}
				
				if (!confirmpwd.getText().toString().equals(newpwd.getText().toString())) {
					showMessageInfo("��������ȷ���������벻һ��!");
					return;
				}
				
				if (!VaildValue.VaildPasswd(confirmpwd.getText().toString())) {
					showMessageInfo("������������6-16���ַ�����ĸ!!");
					return;
				}
				
				showMessageInfo("�����޸�...");
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String oldpwdtext = oldpwd.getText().toString();
						String newpwdtext = newpwd.getText().toString();
						String userid = SharedPreferencesControl.getInstance().getString("userid",com.appdear.client.commctrls.Common.USERLOGIN_XML, UserAlterPwd.this);
						String token = SharedPreferencesControl.getInstance().getString("sessionid", Common.USERLOGIN_XML, UserAlterPwd.this);
						try {
							ApiNormolResult result = ApiManager.updatepasswd(userid, oldpwdtext, newpwdtext, token);
							if (result.isok == 1) {
								showMessageInfo("�����޸ĳɹ����´ε�¼��Ч��");
								return;
							} else {
								showMessageInfo("�޸�����ʧ�ܣ����������");
								return;
							}
						} catch (ApiException e) {
							showMessageInfo("�޸�����ʧ�ܣ����������");
						}
					}
				}).start();
			}
		});
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		UesrInfoLayout userlayout = (UesrInfoLayout) findViewById(R.id.userinfo_top);
		Message message = userlayout.handler.obtainMessage();
		message.what=2;
		userlayout.handler.sendMessage(message);
		handler.sendEmptyMessage(UPDATE);
		if (oldpwd != null)
			oldpwd.setText("");
		if (newpwd != null)
			newpwd.setText("");
		if (confirmpwd != null)
			confirmpwd.setText("");
		super.onNewIntent(intent);
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && isOpenKeyBoard
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);   
			View view = getCurrentFocus();
		    if (view != null) {
		    	imm.hideSoftInputFromWindow(view.getWindowToken(), 0);//���������  
		    }
		    isOpenKeyBoard = false;
		    return true;
		}
		return super.dispatchKeyEvent(event);
	}
	
	@Override
	protected void onDestroy() {
		ScreenManager.getScreenManager().popActivity(this);//��ջ
		super.onDestroy();
	}
}
