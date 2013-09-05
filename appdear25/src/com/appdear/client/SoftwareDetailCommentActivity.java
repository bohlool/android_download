/**
 * CommentActivity.java
 * created at:2011-5-20����04:56:49
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.appdear.client.Adapter.CommentListAdapter;
import com.appdear.client.commctrls.ListBaseActivity;
import com.appdear.client.commctrls.ListViewRefresh;
import com.appdear.client.commctrls.MProgress;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.exception.ApiException;
import com.appdear.client.model.SoftlistInfo;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.Constants;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.service.api.ApiNormolResult;
import com.appdear.client.service.api.ApiSoftListResult;
import com.appdear.client.utility.AsyLoadImageService;
import com.appdear.client.R;

/** 
 * ����������--����
 * 
 * @author zqm
 */
public class SoftwareDetailCommentActivity extends ListBaseActivity {
	
 
	
	/**
	 * �б�����
	 */
	private ApiSoftListResult result;
	
	/**
	 * �ύ����
	 */
	private Button button_submit;
	
	/**
	 * �༭����
	 */
	private EditText editText;
	
	/**
	 * ����
	 */
	private RatingBar bar;
	/**
	 * userid
	 */
	
	private String userid = "0";
	// ��������ʧ��
	private boolean isNetProblem =false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		flag=false;
		setContentView(R.layout.software_detail_comment_info);
	}
	private void initGif(){
		if(height==0&&width==0){
			height = this.getWindowManager().getDefaultDisplay().getHeight();
			width=this.getWindowManager().getDefaultDisplay().getWidth();
		}
		LayoutParams params = new LayoutParams(width,
				height*3/4);
		loadingView=new MProgress(this,true);
		this.addContentView(loadingView, params);
		if (!AppContext.getInstance().isNetState) {
			handler1.sendEmptyMessage(LOADG);
			showRefreshButton();
			return;
		}
	}
	@Override
	public void init() {
		initGif();
		listView = (ListViewRefresh) findViewById(R.id.comment_list);
		listView.setFadingEdgeLength(0);//ȡ������ɫ��ʾ
		/*listView.setDivider(getResources().getDrawable(R.drawable.listspiner));
		listView.setDividerHeight(2);*/
		button_submit = (Button) findViewById(R.id.button_submit);
		editText = (EditText) findViewById(R.id.input_comment);
		bar = (RatingBar) findViewById(R.id.star_level);
		button_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				View view = SoftwareDetailCommentActivity.this.getCurrentFocus();
				if (view != null) {
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					inputMethodManager
							.hideSoftInputFromWindow(view.getWindowToken(), 0);
				}
				if(!AppContext.getInstance().isNetState){
					showMessageInfo("���粻���ã��뿪�������ύ����!");
					return;
				}
				if (result != null && result.softList.size() > 0) {
					for (SoftlistInfo info : result.softList) {
						if (info.commentimei.equals(AppContext.getInstance().IMEI)) {
							showMessageInfo("���Ѿ����۹��������ظ�����!");
							return;
						}
					}
				}
				if("".equals(editText.getText().toString().trim())){
					showMessageInfo("�������ݲ���Ϊ��!");
					return;
				}
				if(editText.getText().toString().trim().length()>70){
					showMessageInfo("�����������ɳ���70��!");
					return;
				}if(bar.getRating() <= 0){
					showMessageInfo("�������������֣�");
					return;
				}
				button_submit.setClickable(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							 
							showMessageInfo("�����ύ,���Ժ�...");
							String token = "";
							userid = SharedPreferencesControl.getInstance().getString("userid", com.appdear.client.commctrls.Common.USERLOGIN_XML,SoftwareDetailCommentActivity.this)+"";
							token = SharedPreferencesControl.getInstance().getString("sessionid", com.appdear.client.commctrls.Common.USERLOGIN_XML,SoftwareDetailCommentActivity.this);
							//��ʵ����tokenδ��¼Ϊnull,useridδ��¼Ϊ��0��
							ApiNormolResult result = ApiManager.commentcommit((userid.equals("") || userid == null)?"0":userid, 
									(token.equals("") || token == null)?"0":token,
									getIntent().getStringExtra("softid"), editText.getText().toString(),
									bar.getProgress()*10/2+"");
							String alertinfo = "";
							if (result.isok == 1) {
								alertinfo = "�ύ�ɹ�";
								refreshUI("commitcomment", "1");
							} else if (result.isok == 2) {
								alertinfo = "�����дʣ����������ѱ����Σ�";
							} else if (result.isok == 3) {
								alertinfo = "���Ѿ����۹��������ظ����ۣ�";
							} else if (result.isok == 4) {
								alertinfo = "�����غ������ۣ�";
							} else {
								alertinfo = "����ʧ�ܣ�";
							}
							showMessageInfo(alertinfo);		
							refreshUI("commitcomment", "fail");
						} catch (ApiException e) {
							refreshUI("commitcomment", "fail");
							if (Constants.DEBUG)Log.e("net error:",e.getMessage(), e);
							showException(e);
						}catch (OutOfMemoryError e) {
							 AsyLoadImageService.getInstance().getImageCache().clear();
							System.gc();
							e.printStackTrace();
							Log.e("load image", "�ڴ������");
						}
					}
				}).start();
			}
		});	
	}

	@Override
	public void refreshUI(Bundle budle) {
		if (budle.getString("commitcomment").equals("1")) {
			//���ύ�ɹ���������ӵ������б���
			SoftlistInfo info = new SoftlistInfo();
			info.commentid = "0";
			//��ȡϵͳʱ��
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			info.text = editText.getText().toString();
			info.time = format.format(new Date());
			String name = SharedPreferencesControl.getInstance().getString("nickname", com.appdear.client.commctrls.Common.USERLOGIN_XML,SoftwareDetailCommentActivity.this)+"";
			info.username = (name.equals("") || name == null)? "��Ƥ����" : name;
			info.userid = (userid.equals("") || userid == null)?"0":userid;
			info.softgrade = bar.getProgress()*10/2;
			if (result == null)
				result = new ApiSoftListResult();
			if (result.softList.size() == 0)
				result.softList.add(info);
			else
				result.softList.add(0, info);
			//�����
			editText.setText("");
			bar.setProgress(0);
			button_submit.setClickable(true);
			listView.setAdapter(adapter); // ���޴���䣬���ڿ���������½������۲���ˢ�½���
		}else if (budle.getString("commitcomment").equals("fail")) {
			button_submit.setClickable(true);
		}
		super.refreshUI(budle);
	}
	
	@Override
	public void initData() {
		try {
 			result = ApiManager.commentlist(getIntent().getStringExtra("softid"), "1", "1", "6");
			if (result == null) 
				return;
			adapter = new CommentListAdapter(this, result.softList, false);
			adapter = (CommentListAdapter) adapter;
			adapter.notifyDataSetChanged();
		} catch (ApiException e) {
			if (Constants.DEBUG)Log.e("net error:",e.getMessage(), e);
			isNetProblem=true;
			showException(e);
		}finally{
			handler1.sendEmptyMessage(LOADG);
		}
		super.initData();
	}
	
	@Override
	public void updateUI() {
		if(listView==null)return;
		listView.setAdapter(adapter);

		//���������Ϊ�գ���û�����ۣ�����ʾ��ʾ��Ϣ
		String content;
		if(isNetProblem )
		{
			  content = "�������⣬��������ʧ�ܣ�";
			  isNetProblem=false;
		}else
		{
		       content = "Ŀǰ��û�������ۣ����������ɳ��Ŷ~";
		}
		listView.setEmptyView(inflateEmptyView(this, content)); // ���ÿ�Adapter����µ�view
	}

	/**
	 * ���һ������Ϊcontent��TextView
	 * @param context
	 * @param content  TextView����ʾ����
	 * @return
	 */
	private View inflateEmptyView(Context context, String content) {
		TextView emptyView = new TextView(context);
		emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		emptyView.setText(content);
		emptyView.setVisibility(View.GONE);
		emptyView.setGravity(Gravity.CENTER_HORIZONTAL);
		
		ViewGroup group = (ViewGroup) listView.getParent();
		group.setBackgroundColor(Color.TRANSPARENT); //���ñ���͸��
		group.addView(emptyView);
		
		return emptyView;
	}

	@Override
	public void refreshDataUI() {
		
	}

	@Override
	public void doRefreshData() {
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		result=null;
		super.onDestroy();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		if(false){
			super.onSaveInstanceState(outState);
		}
	}
	
}

 