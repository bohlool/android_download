package com.appdear.client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appdear.client.commctrls.BaseGroupActivity;
import com.appdear.client.commctrls.PagerCallback;
import com.appdear.client.commctrls.PagerContoler;
 
import com.appdear.client.service.Constants;
import com.appdear.client.utility.ScreenManager;
 
/**
 * ���� - ����
 * @author zxy
 *
 */
public class MoreHelpMainActivity extends BaseGroupActivity implements  PagerCallback{

 
	LayoutInflater  mLayoutInflater ;
	private View view2;
	
	PagerContoler pagerContoler=new PagerContoler(this);
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.more_helper_layout);
		 
		
	 
		 ScreenManager.getScreenManager().pushActivity(this);//��ջ
	}
	
	@Override
	public void init() {
		String[]  strs=new String[]{"����", "��������", "����"};
		
		pagerContoler.initImageView(this,0);
		pagerContoler.initTextView(strs, this);
		
		mLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//����
		LinearLayout aipibiView = (LinearLayout) mLayoutInflater.inflate(R.layout.more_help_layout, null);	 
		 
	    view2 = mLayoutInflater.inflate(R.layout.xinyonghu_view_init, null);
	
		//����
		RelativeLayout caozuoView = (RelativeLayout)this.mLayoutInflater.inflate(R.layout.menu_about, null);
		TextView about = (TextView) caozuoView.findViewById(R.id.about_version);
		about.setText("�汾��V" + Constants.VERSION);
		
		pagerContoler.initViewPager(this,aipibiView,view2,caozuoView,false,true,false);
	}
     
	@Override
	protected void onNewIntent(Intent intent) {
		 
		super.onNewIntent(intent);
	}
	
	@Override
	protected void onDestroy() {
		ScreenManager.getScreenManager().popActivity(this);//��ջ
		super.onDestroy();
	}
 

	@Override
	public View viewSecend() {
		return view2;
	}

	@Override
	public View viewThird() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View viewFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	 

}
