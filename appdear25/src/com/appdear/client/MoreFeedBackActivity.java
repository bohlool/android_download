package com.appdear.client;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appdear.client.commctrls.BaseActivity;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.exception.ApiException;
import com.appdear.client.exception.ServerException;
import com.appdear.client.service.Constants;
import com.appdear.client.service.api.ApiFeedbackRquest;
import com.appdear.client.service.api.ApifeedBackResult;
import com.appdear.client.utility.ScreenManager;
import com.appdear.client.utility.ServiceUtils;
import com.appdear.client.R;

/**
 *  ���� - ������
 * @author zxy
 */
public class MoreFeedBackActivity extends  BaseActivity{

	/**
	 *����
	 */
	String array  [] = {"��������","�Ż�����","��������"};
	/**
	 * �����ؼ�
	 */
	/**
	 * ��������
	 */
	EditText mEt_content = null;
	/**
	 * l����
	 */
	private AutoCompleteTextView text;
	private ImageView button,btn_return;
	TextView feedback_tv_typeText ;
	private TextView more_feedback_submit;
	String selectType = "0";
	Spinner mSpinner = null;
	RelativeLayout title_layout;
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.more_feedback_layout);
		ScreenManager.getScreenManager().pushActivity(this);//��ջ
	}
	
	@Override
	public void init() {
		selectType = "0";
		mEt_content = (EditText)findViewById(R.id.more_feedback_et_content);
		title_layout=(RelativeLayout) findViewById(R.id.top);
		//mEt_content.setFocusable(true);
		more_feedback_submit=(TextView)findViewById(R.id.more_feedback_submit);
	        text=(AutoCompleteTextView)this.findViewById(R.id.auto_complete);
	        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, array);
	        button=(ImageView)this.findViewById(R.id.button);
	        text.setAdapter(adapter);
	        button.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text.showDropDown();
				}    	
	        });
	        title_layout.setOnClickListener(new OnClickListener() {
	    		
	    		@Override
	    		public void onClick(View v) {
	    			// TODO Auto-generated method stub
	    			finish();
	    		}
	    	});
	        btn_return = (ImageView) findViewById(R.id.btn_return);
			btn_return.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					finish();
				}
			});
	        //init
	        more_feedback_submit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(ServiceUtils.checkNetState(MoreFeedBackActivity.this)==false){
					Toast.makeText(MoreFeedBackActivity.this,"���粻���ã��뿪�������ύ����!",Toast.LENGTH_LONG).show();
					return;
				}
				String content  = mEt_content.getText().toString().trim();
				String type = selectType; 
				 
				int resCode = -1;
				ApifeedBackResult result = null;
				//content = "�������Է�����";
				
				String sessionid = SharedPreferencesControl.getInstance().getString("sessionid",com.appdear.client.commctrls.Common.USERLOGIN_XML,MoreFeedBackActivity.this);
				String  userid = SharedPreferencesControl.getInstance().getString("userid",com.appdear.client.commctrls.Common.USERLOGIN_XML,MoreFeedBackActivity.this);
				//if(sessionid!=""||userid!=""){
				if("".equals(content)){
					Toast.makeText(MoreFeedBackActivity.this, "����д��������", Toast.LENGTH_SHORT).show();
					return ;
					//TODO:��֤
				}else{
					//�����ύ
					//TODO:...
					//select typ is :selectType
					//SharedPreferencesControl.getInstance().getString("userid");
					try {
						try {
							result = ApiFeedbackRquest.publishMsg(userid, sessionid, type, content);
						} catch (JSONException e) {
							showException("��������"); 
						}
					} catch (ApiException e) {
						showException(e);
						e.printStackTrace();
					} catch (ServerException e) {
						showException(e);
						e.printStackTrace();
					}
					if(result!=null&&result.resultcode!=null&&result.resultcode!=""){
						resCode = Integer.parseInt(result.resultcode);
						switch(resCode){
						case 100000:
							Toast.makeText(MoreFeedBackActivity.this, "��ƤӦ�����ؼ�Ȩδͨ��", Toast.LENGTH_SHORT).show();
							break;
						case 200000:
							Toast.makeText(MoreFeedBackActivity.this, "���������ʽ����", Toast.LENGTH_SHORT).show();
							break;
						case 300000:
							Toast.makeText(MoreFeedBackActivity.this, "�����ڲ�����", Toast.LENGTH_SHORT).show();
							break;
						case 400000:
							Toast.makeText(MoreFeedBackActivity.this, "���糬ʱ�����������ԡ�", Toast.LENGTH_SHORT).show();
							break;
						case 500000:
							Toast.makeText(MoreFeedBackActivity.this, "�û�token�Ѿ�ʧЧ��", Toast.LENGTH_SHORT).show();
							break;
						case 600000:
							Toast.makeText(MoreFeedBackActivity.this, "����JSON��ʽ����", Toast.LENGTH_SHORT).show();
							break;
						case 700000:
							Toast.makeText(MoreFeedBackActivity.this, "����headerͷ���ݴ���", Toast.LENGTH_SHORT).show();
							break;
						case 000000:
							Toast.makeText(MoreFeedBackActivity.this, "�������ύ��лл���룡 !", Toast.LENGTH_SHORT).show();
							more_feedback_submit.setEnabled(false);
							InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(mEt_content.getWindowToken(), 0);
							 
							MoreFeedBackActivity.this.finish();
							break;
						}
					}
				}
			}
		});
	}
	
	@Override
	public void refreshUI(String tag, String info) {
	}

	@Override
	public void updateUI() {
		button.requestFocus();
	}

	@Override
	public void updateUIStart() {
	}

	@Override
	public void updateUIEnd() {
	}

	
	@Override
	public void refreshUI(Bundle budle) {
		super.refreshUI(budle);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (Constants.DEBUG) Log.i("test","onNewIntent");
		super.onNewIntent(intent);
	}
	
	@Override
	protected void onRestart() {
		if (Constants.DEBUG) Log.i("test","onRestart");
		button.setFocusable(true);
		super.onRestart();
	}

	@Override
	protected void onPause() {
		if (Constants.DEBUG) Log.i("test","onPause");
		button.setFocusable(true);
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (Constants.DEBUG) Log.i("test","onResume");
		button.setFocusable(true);
		super.onResume();
	}

	@Override
	protected void onStop() {
		if (Constants.DEBUG) Log.i("test","onStop");
		button.setFocusable(true);
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		ScreenManager.getScreenManager().popActivity(this);//��ջ
		super.onDestroy();
	}
}
