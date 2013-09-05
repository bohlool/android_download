package com.appdear.client;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appdear.client.commctrls.BaseActivity;
import com.appdear.client.utility.ScreenManager;
import com.appdear.client.R;

public class MoreUserFindPwdActivity extends  BaseActivity {
	EditText mEtName = null;
	Button  mBtnConfirm = null;
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.more_user_find_pwd_layout);
		ScreenManager.getScreenManager().pushActivity(this);//��ջ
	}
	
	@Override
	public void init() {
		mEtName = (EditText) findViewById(R.id.user_findPwd_Name);
		mBtnConfirm = (Button) findViewById(R.id.user_findPwd_btnConfirm);
		mBtnConfirm.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(MoreUserFindPwdActivity.this,"�����Ѿ�����������䣬����գ�",Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		ScreenManager.getScreenManager().popActivity(this);//��ջ
		super.onDestroy();
	}
}
