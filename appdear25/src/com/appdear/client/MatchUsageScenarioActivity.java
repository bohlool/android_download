package com.appdear.client;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.appdear.client.commctrls.ListBaseActivity;
import com.appdear.client.commctrls.ListViewRefresh;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.exception.ApiException;
import com.appdear.client.model.CatalogListInfo;
import com.appdear.client.model.SoftlistInfo;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.Constants;
import com.appdear.client.service.api.ApiCatalogListResult;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.utility.ServiceUtils;
import com.appdear.client.utility.Utils;
import com.appdear.client.R;

/**
 * ��Ͳ--ʹ�ó���
 *
 */
public class MatchUsageScenarioActivity extends ListBaseActivity implements OnItemClickListener{
	private static final int MAX_TEXTVIEW_COUNT = 23;
	TextView[] tvs = new TextView[MAX_TEXTVIEW_COUNT];
	List<String> tvContents = new ArrayList<String>();  //����������textview������
	
	/**
	 * textview�����ݶ�Ӧ������
	 */
	private String[][] tvDataArray = new String[MAX_TEXTVIEW_COUNT][2];
	
	/**
	 * ����б�����Ϣ
	 */
	private ApiCatalogListResult catList = new ApiCatalogListResult();
	 
	SoftlistInfo mSoftCategory = null;
	ArrayList<CatalogListInfo> mSoftCategoryList = null;
	List<CatalogListInfo> dataList =  new ArrayList<CatalogListInfo>() ;
	
	/**
	 * ��ĿID
	 */
	private int id = 104476;
	
	/**
	 * ��ĿKey
	 */
	private final String CAT_KEY = "602";
	
	/**
	 * ���ݿ��ĵ�type������type�Ĳ�ͬ�����벻ͬ�ı�
	 */
	private final String DB_TYPE = "1";
	
	/**
	 * ��ǰҳ
	 */
	private String pageno = "1";
	
	/**
	 * ��ǰҳ����
	 */
	private String count = "23";
	
	/**
	 * ��Ϸ����б�����
	 */
	ApiCatalogListResult result = new ApiCatalogListResult();
	
	private String ctype = "0";
	
	public void onCreate(Bundle b){
		super.onCreate(b);
		isUpdate=true;
		isShowAlert = false;
		setContentView(R.layout.match_usage_scenario);
	}
	
	@Override
	public void init() {
		int id = R.id.tv1;
		TranslateAnimation a=null;
		int[] display=ServiceUtils.getCurrentPhonePixInfo(this);
		int k=0;
		int[] j=new int[2];
		for (int i = 0; i < MAX_TEXTVIEW_COUNT; i++) {
			tvs[i]=(TextView)this.findViewById(id++);
			tvs[i].getLocationOnScreen(j);
				if(k%2==0){
					a=new TranslateAnimation(-display[0],j[0],
		                0, 0);
				}else{
					a=new TranslateAnimation(display[0],j[0],
			                0, 0);
				}
			a.setDuration(1500);
			a.setFillAfter(true);
			tvs[i].startAnimation(a);
			k++;
		}
	}

	@Override
	public void updateUI() {
		for (int i = 0; i < MAX_TEXTVIEW_COUNT; i++) {
			tvs[i].setText(tvDataArray[i][0]);
		}
	}
	
	public void onTextClick(View view) {
		String str = (String) ((TextView)view).getText();
		if (catList==null||catList.catalogList == null)
			return;
		//����
		String categoryTitle = "ʹ�ó���-" + str;
		
		//��ʾ�������
		Intent categorydetail = new Intent(MatchUsageScenarioActivity.this,
				MatchSoftListActivity.class);
		categorydetail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		categorydetail.putExtra("category_title", str);
		categorydetail.putExtra("category_id", Utils.getIdByName(str, tvDataArray));
 		categorydetail.putExtra("category_navigation", categoryTitle);
		categorydetail.putExtra("ctype", ctype);
		startActivity(categorydetail);
	}
	
	/**
	 * ���ݱ�ǩ�����Ҷ�Ӧ��id
	 * @param catName
	 * @return
	 *//*
	private String getIdByName(String catName) {
		if (catName == null) {
			return "-1";
		}
		for (int i = 0; i < MAX_TEXTVIEW_COUNT; i++) {
			if (catName.equals(tvDataArray[i][0])) {
				return tvDataArray[i][1];  //���ض�Ӧ��id
			}
		}
		return "-1";
	}*/
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {

	}
	
	@Override
	public void initData() {
		try {
			// ��ǰ�������汾���ϴα���ķ������汾����һ������ʱsavedLabelVersion��Ϊȡ����ֵ����0��
			int nowServerVersion = AppContext.getInstance().labelversionresponse;

			if (nowServerVersion == Constants.LABELVERSIONFORCLIENT) {
				// 1����� �������汾��=Ԥ�ð汾�ţ����Ԥ������ȡ
				tvDataArray = Constants.USAGESCENARIO_ID_NAME_ARRAY;
				Utils.print("-----�������汾��=Ԥ�ð汾��-->ȡ��������-----");
			} else {
				// 2����������ݿ���߷�����ȡ
				if(!AppContext.getInstance().isNetState){
					tvDataArray = Constants.USAGESCENARIO_ID_NAME_ARRAY;
				}else{
					catList = Utils.getCatListFromServerOrDB(this, pageno, count,
							id, CAT_KEY, DB_TYPE, nowServerVersion, MAX_TEXTVIEW_COUNT);
					
					int dumpCount = catList.catalogList.size() > MAX_TEXTVIEW_COUNT ? MAX_TEXTVIEW_COUNT : catList.catalogList.size();
					if (dumpCount > 0) {
						ctype = catList.catalogList.get(0).ctype;
						for (int i = 0; i < dumpCount; i++) {
							CatalogListInfo info = catList.catalogList.get(i);
							tvDataArray[i][0] = info.catalogname;
							tvDataArray[i][1] = info.catalogid;
							/*if (i == dumpCount - 1)
							ctype = info.ctype;*/
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.initData();
	}

	@Override
	public void refreshDataUI() {
	}

	@Override
	public void doRefreshData() {
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void dataNotifySetChanged() {
	
	}
}