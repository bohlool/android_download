package com.appdear.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.appdear.client.commctrls.BaseActivity;
import com.appdear.client.commctrls.Common;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.exception.ApiException;
import com.appdear.client.model.BackgroundInfo;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.Constants;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.service.api.ApiSoftListResult;
import com.appdear.client.utility.ScreenManager;
import com.appdear.client.R;


/**
 * ����--����
 * 
 * @author zqm
 *
 */
public class MoreSettingsActivity extends BaseActivity {

	private CheckBox softicon;
	private CheckBox snapshot;
	private CheckBox loadwifi;
	private CheckBox autoinstall;
	private CheckBox deleteApk;
	private CheckBox softUpdateTip;
	public static List<BackgroundInfo> backgroundlist = null;
	public static int totalcountbackground;
	private BaseAdapter adapter;
	private ApiSoftListResult result;
	private EditText hostcontent;
	private Button host_setting_button;
	private RelativeLayout host_settings;
	private LinearLayout host_spinner;
	private boolean  isLoadIcon= false ;
	private File updateDir;
	private  File updateFile;
	private ImageButton backbutton;//���˰���
	RelativeLayout title_layout;
	boolean backbutton_is_show;
	/**
	 * �Ƿ������������
	 */
	private boolean isGetbg = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent =getIntent();
		if(intent!=null){
			String from=intent.getStringExtra("from");
			if(from!=null&&from.equals("moreView")){
				backbutton_is_show=true;
			}else{
				backbutton_is_show=false;
			}
		}
		setContentView(R.layout.more_settings_form_layout);
		
		ScreenManager.getScreenManager().pushActivity(this);//��ջ
	}
	
	@Override
	public void init() {
		title_layout=(RelativeLayout) findViewById(R.id.title_layout);
		softicon = (CheckBox) findViewById(R.id.loadsofticon);
		snapshot = (CheckBox) findViewById(R.id.loadsnapshot);
		loadwifi = (CheckBox) findViewById(R.id.loadwifi);
		autoinstall = (CheckBox) findViewById(R.id.autoinstall);
		deleteApk = (CheckBox) findViewById(R.id.deleteApk);
		softUpdateTip = (CheckBox) findViewById(R.id.softUpdateTip);
		host_settings = (RelativeLayout) findViewById(R.id.host_settings);
		host_spinner = (LinearLayout) findViewById(R.id.host_spinner);
		backbutton=(ImageButton) findViewById(R.id.setting_back);
		
		if(backbutton_is_show)backbutton.setVisibility(View.VISIBLE);
		
		//��ʾ������������
		if (Constants.SETTING_HOST_DEBUG) {
			host_settings.setVisibility(View.VISIBLE);
			host_spinner.setVisibility(View.VISIBLE);
		}
		
		hostcontent = (EditText) findViewById(R.id.hostcontent);
		hostcontent.setText(AppContext.getInstance().api_url);
		host_setting_button = (Button) findViewById(R.id.host_setting_button);
		
		//��ʾ���ͼ��
		softicon.setChecked(SharedPreferencesControl.getInstance().getBoolean(
						"loadsofticon", com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this));
		//��ʾ�����ͼ
		snapshot.setChecked(SharedPreferencesControl.getInstance().getBoolean(
						"loadsnapshot", com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this));
		loadwifi.setChecked(SharedPreferencesControl.getInstance().getBoolean(
						"loadwifi", com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this));
		//�������Զ���װ
		autoinstall.setChecked(SharedPreferencesControl.getInstance().getBoolean(
						"autoinstall", com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this));
		//ɾ����װ�ļ�
		deleteApk.setChecked(SharedPreferencesControl.getInstance().getBoolean(
						"deleteApk", com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this));
		//���������ʾ����
		softUpdateTip.setChecked(SharedPreferencesControl.getInstance().getBoolean(
				"softUpdateTip", com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this));
		 
		//�����ַ����
		host_setting_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppContext.getInstance().api_url = hostcontent.getText().toString();
				SharedPreferencesControl.getInstance().putString(
						"hostsetting", AppContext.getInstance().api_url,
						com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this);
				new AlertDialog.Builder(MoreSettingsActivity.this)
				.setMessage("���óɹ�����������Ƥ!")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						AppContext.getInstance().downloader.downDb.updatePause();
						(MoreSettingsActivity.this).finish();
						System.exit(0);
					}
				}).show();
			}
		});
		
		//���ͼ��
		softicon.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferencesControl.getInstance().putBoolean(
						"loadsofticon",softicon.isChecked(),com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this);		
				Common.ISLOADSOFTICON=softicon.isChecked();
			}
		});
		//�����ͼ
		snapshot.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferencesControl.getInstance().putBoolean(
						"loadsnapshot",snapshot.isChecked(),com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this);	
				Common.LOADSNAPSHOT=snapshot.isChecked();
			}
		});
		
		//ֻ��wifi������
		loadwifi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferencesControl.getInstance().putBoolean(
						"loadwifi",loadwifi.isChecked(),com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this);	
			}
		});
		
		//���غ��Զ���װ
		autoinstall.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferencesControl.getInstance().putBoolean(
						"autoinstall",autoinstall.isChecked(),com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this);
			}
		});
		//��װ��ɺ�ɾ����װ�ļ�
		deleteApk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferencesControl.getInstance().putBoolean(
						"deleteApk",deleteApk.isChecked(),com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this);
			}
		});
		
		
//		���˰���
		title_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//��װ��ɺ�ɾ����װ�ļ�
		softUpdateTip.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferencesControl.getInstance().putBoolean(
						"softUpdateTip",softUpdateTip.isChecked(),com.appdear.client.commctrls.Common.SETTINGS,MoreSettingsActivity.this);
			}
		});
	}
	
	@Override
	public void initData() {
//		int id = SharedPreferencesControl.getInstance().getInt("501", com.appdear.client.commctrls.Common.SECTIONCODEXML, this);
//		try {
//			ApiSoftListResult result = ApiManager.getbackgroundlist(id + "", "1", "20");
//			isGetbg = true;
//			if(result!=null){
//				backgroundlist = result.backgroundlist;
//				totalcountbackground=result.totalcount;
//			}
//		} catch (ApiException e) {
//			if (Constants.DEBUG)Log.e("net error:",e.getMessage(), e);
//			showException(e);
//		}
		super.initData();
	}
	
	@Override
	protected void onDestroy() {
		ScreenManager.getScreenManager().popActivity(this);//��ջ
		super.onDestroy();
	}
}
