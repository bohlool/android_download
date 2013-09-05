/**
 * SoftwareDetilActivity.java
 * created at:2011-5-20����04:14:45
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */
package com.appdear.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appdear.client.commctrls.AsynLoadImageView;
import com.appdear.client.commctrls.BaseActivity;
import com.appdear.client.commctrls.BaseGroupActivity;
import com.appdear.client.commctrls.Common;
import com.appdear.client.commctrls.LoginWeiboThread;
import com.appdear.client.commctrls.MProgress;
import com.appdear.client.commctrls.PagerCallback;
import com.appdear.client.commctrls.PagerContolerDetail;
import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.commctrls.TabCallBack;
import com.appdear.client.commctrls.TabbarNewDetail;
import com.appdear.client.db.SoftWareInfoDB;
import com.appdear.client.download.DownloadUtils;
import com.appdear.client.download.FileDownloaderService;
import com.appdear.client.download.SiteInfoBean;
import com.appdear.client.exception.ApiException;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.Constants;
import com.appdear.client.service.MyApplication;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.service.api.ApiNormolResult;
import com.appdear.client.service.api.ApiSoftListResult;
import com.appdear.client.utility.AsyLoadImageService;
import com.appdear.client.utility.BitmapTemp;
import com.appdear.client.utility.ImageCache;
import com.appdear.client.utility.ServiceUtils;
import com.appdear.client.utility.Utils;


/**
 * �������
 * @author zqm
 */
public class SoftwareMainDetilActivity extends BaseGroupActivity implements
		 OnClickListener,PagerCallback {
	public static String appname = null;
	ProgressDialog mpDialog ;

	/**
	 * ���������Ϣ����
	 */
	private ApiSoftListResult softlistResult;

	/**
	 * �������
	 */
	private Intent softinfoIntent;
   // private  boolean isDel=false;
	/**
	 * �������
	 */
	private View softwareDetailView;

	/**
	 * ����
	 */
	private View commentView;

	/**
	 * ������Ϣ
	 */
	private View authorInfoView;

	/**
	 * activity����
	 */
	private LocalActivityManager manager;

	/**
	 * �Ƿ������ղ�
	 */
	public static boolean isClickFavorite = false;
	public static boolean isClickFavoriteUpdate = false;

	/**
	 * tab
	 */
	private TabbarNewDetail tab;

	/**
	 * ���������Ϣ
	 */
	private LinearLayout softinfoLayout;
	/**
	 * DB�洢
	 */
	private SoftWareInfoDB dbinfo;

	/**
	 * ���ذ�ť
	 */
	private Button downloadLayout;
	
	ProgressDialog progress;
	/**
	 * ���ذ�ť��״̬ 0 -- ����, 1 -- �����У�2 ��װ 3--�Ѱ�װ   4--����  5  ��ͣ
	 */
	private int downloadstate = 0;
	public ProgressBar progressBar;
	public RelativeLayout progressLayout;
 	private TextView processTextView;
	public IntentFilter filter;
	
	
	
  	
	/**
	 * ���id
	 */
	public int softid;
	
	public String downloadurl=null;
	
	public String softicon;
	
	public RelativeLayout button_layout;
	
	static ImageCache imageCache = AsyLoadImageService.getInstance()
			.getImageCache();
	public boolean ad = false;
	
	 private LayoutInflater  mLayoutInflater ;
	 
	 public   PagerContolerDetail pagerContoler ;
	 
	 private View view2;
	 private View view3;
	 DisplayMetrics  metrics; 
	 float ds;
	 int height0;
	 private String shareContent;
	 
	 private Button click_button_favorites;
	 private Button click_button_share;
	 
	 
	 private Button click_download_left;
	 private Button click_download_cancel;
	 
	 /**
	  * �����е���Ϣ
	  */
	 private SiteInfoBean siteInfoBean;
	 
	 private String cataid;
	 
	 private Integer downloadcount=null;
	 
	 /**
	 * �̳߳�
	 */
/*	static AbstractExecutorService pool = new ThreadPoolExecutor(3, 10, 15L,
			TimeUnit.SECONDS, new SynchronousQueue(),
			new ThreadPoolExecutor.DiscardOldestPolicy());
*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.softwaredetail);
		isShowAlert = false;
		metrics = ServiceUtils.getMetrics(this.getWindowManager());
	    ds=metrics.density;
	    height0=metrics.heightPixels;
	}
	
	/**
	 * �������غ�δ���ذ�ť��ʾ״̬
	 * @param isDownloading
	 */
	private void setButton_visible(boolean isDownloading) {
		if (isDownloading) {
			//������
			click_download_left.setVisibility(View.VISIBLE);
			click_download_cancel.setVisibility(View.VISIBLE);
			
			click_button_favorites.setVisibility(View.GONE);
			click_button_share.setVisibility(View.GONE);
		} else {
			//δ����״̬
			click_download_left.setVisibility(View.GONE);
			click_download_cancel.setVisibility(View.GONE);
			
			click_button_favorites.setVisibility(View.VISIBLE);
			click_button_share.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void init() {
		softid = getIntent().getIntExtra("softid", 0);
		ad = getIntent().getBooleanExtra("ad", false);
		cataid= getIntent().getStringExtra("cataid");
		downloadcount=getIntent().getIntExtra("downloadcount",-1);
		MyApplication.getInstance().detailSoftidList.add(new Integer(softid));	
		
		downloadurl=getIntent().getStringExtra("downloadurl");
		softicon=getIntent().getStringExtra("softicon");
		dbinfo = new SoftWareInfoDB(this);
		downloadLayout=(Button)this.findViewById(R.id.download_layout_bottom);
		downloadLayout.getPaint().setFakeBoldText(true); //�������ء���Ϊ����
		button_layout = (RelativeLayout) findViewById(R.id.bottom_button);
		progressBar =(ProgressBar)this.findViewById(R.id.progressBar);
		//puassView=(ImageView)this.findViewById(R.id.puassView);
		
		processTextView=(TextView)this.findViewById(R.id.processTextView);
		progressLayout=(RelativeLayout)this.findViewById(R.id.progressLayout);
		
		downloadstate = 0;
		downloadLayout.setOnClickListener(this);
		if (Constants.DEBUG)
			Log.i("timeTest", "timeTest:enter into soft details activity :"
					+ System.currentTimeMillis());
		mLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view2= mLayoutInflater.inflate(R.layout.pagerview_init, null);
		view3= mLayoutInflater.inflate(R.layout.pagerview_init, null);
		initGif();
		if (softicon!=null&&softicon.length()>0) {
			setImg(softicon);
		}
		//������ͣ\������ȡ��
		click_download_cancel = (Button) findViewById(R.id.click_download_cancel);
		click_download_left = (Button) findViewById(R.id.click_download_left);
		
		//����
		click_button_favorites = (Button) findViewById(R.id.click_button_favorite_);
		click_button_share = (Button) findViewById(R.id.click_button_share_);
		
		click_download_cancel.setOnClickListener(this);
		click_download_left.setOnClickListener(this);
		click_button_favorites.setOnClickListener(this);
		click_button_share.setOnClickListener(this);
	}
	
	private void setImg(String icon){
		AsynLoadImageView img = (AsynLoadImageView) findViewById(R.id.imageView);
		img.setDefaultImage(R.drawable.soft_lsit_icon_default);
		img.setImageResource(R.drawable.soft_lsit_icon_default);
		boolean isloadsofticon = SharedPreferencesControl.getInstance()
				.getBoolean("loadsofticon",
						com.appdear.client.commctrls.Common.SETTINGS,
						AppContext.getInstance().appContext);
		if (isloadsofticon) {
			if (img != null){
				img.setImageUrl(icon, true);
			}
		}
	}
	
	TabCallBack callback=new TabCallBack(){
		@Override
		public void callback(int position) {
		//	Log.i("info909", "moremanager======callback="+position);
			if(position == 0)
			{
			   if (softinfoIntent == null) {
					softinfoIntent = new Intent(SoftwareMainDetilActivity.this,
							SoftWareDetailInfoActivity.class).putExtra("info",
							softlistResult);
					softinfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					softwareDetailView = manager.startActivity("softinfo",
							softinfoIntent).getDecorView();
				}
			}else if (position == 1) {
				if (commentView == null) {
					int softid = 0;
					String appid = "";
					if (softlistResult != null && softlistResult.detailinfo != null) {
						softid = softlistResult.detailinfo.softid;
						appid = softlistResult.detailinfo.appid;
					}
					commentView = manager.startActivity(
							"comment",
							new Intent(SoftwareMainDetilActivity.this,
									SoftwareDetailCommentActivity.class).putExtra(
									"softid", softid + "").putExtra("appid",
											appid)).getDecorView();
				}
		 	}else if(position == 2)
		 	{
		 		if (authorInfoView == null) {
					// 2011 - 09 - 16 ������Ϣ����Ϊͬ������Ƽ�
					// TODO:�ȴ��������˷������id
					String catid = "0";
					if (softlistResult != null) {
						if (softlistResult.detailinfo != null
								&& !softlistResult.detailinfo.equals("")) {
							catid = softlistResult.detailinfo.catid + "";
						}
					}
					int softid = 0;
					if (softlistResult != null && softlistResult.detailinfo != null) {
						softid = softlistResult.detailinfo.softid;
					}
					authorInfoView = manager.startActivity(
							"anthor",
							new Intent(SoftwareMainDetilActivity.this,
									SoftwareDetailAuthorInfoActivity.class).putExtra(
									"category_id", catid).putExtra("softid",
											softid)).getDecorView();
				}
		 	}
		}
	};
	private void initGif() {
		if(height==0&&width==0) {
			height = this.getWindowManager().getDefaultDisplay().getHeight();
			width=this.getWindowManager().getDefaultDisplay().getWidth();
		}
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		loadingView=new MProgress(this,true);
		this.addContentView(loadingView, params);
		if (!AppContext.getInstance().isNetState) {
			handler.sendEmptyMessage(LOADG);
			return;
		}
	}

	@Override
	public void initData() {
	 
		// ��ʾ����
		
		try {
			// �������
			handler.sendEmptyMessage(LOADV);
			if(dbinfo==null)return;
			softlistResult = dbinfo.read(softid);
			if (softlistResult == null) {
				String adTag = null;
				String source="";
				if (ad) {
					adTag = "ad";
					source=Constants.flagLog[3][0];
				}else{
					adTag="";
					if(MainActivity.bottomLogFlag>=0&&MainActivity.topLogFlag>=0){
						source=Constants.flagLog[MainActivity.bottomLogFlag][MainActivity.topLogFlag];
						if(MainActivity.bottomLogFlag==0&&MainActivity.topLogFlag==2){
							source+=MyApplication.getInstance().modelCompany;
						}
					}
				}
				softlistResult = ApiManager.resultsoftinfo(softid+"", adTag,source,cataid);
				if (softlistResult != null){
					if(dbinfo!=null)dbinfo.save(softlistResult);
					if(downloadcount==-1){
						downloadcount=softlistResult.detailinfo.download;
					}
				}
				
			}
			if(dbinfo!=null&&dbinfo.dbHelper!=null)dbinfo.dbHelper.close();
			
			if (softlistResult == null || softlistResult.detailinfo == null) {
				showMessageInfo("��ȡ��ϸ��Ϣʧ��");
				finish();
				return;
			}
			
//			if (ServiceUtils.checkLogin(SoftwareMainDetilActivity.this, false)) {
//				String userid = SharedPreferencesControl.getInstance()
//						.getString("userid", Common.USERLOGIN_XML, this);
//				String token = SharedPreferencesControl.getInstance()
//						.getString("sessionid", Common.USERLOGIN_XML, this);
//				if (softlistResult == null || softlistResult.detailinfo == null)
//					return;
//				int softid = softlistResult.detailinfo.softid;
//				ApiNormolResult result = ApiManager.isfavoritelist(userid, token, softid+ "");
//				if (result.isfavorited == 1) {
//					refreshUI("isfavorite", "1");
//				}
//			}
		} catch (Exception e) {
			handler.sendEmptyMessage(LOADG);
			showException(e);
			if (Constants.DEBUG)
				Log.e("net error:", e.getMessage(), e);
		}
		super.initData();
	}
	
//	public void refreshUI(Bundle budle) {
//		if (budle.get("isfavorite").equals("1"))
//		{
//			click_button_favorites.setText("���ղ�");
//			click_button_favorites.setClickable(false);
//		}
//		super.refreshUI(budle);
//	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appdear.client.commctrls.BaseActivity#updateUI()
	 */
	@Override
	public void updateUI() {
		if(softicon==null|| "".equals(softicon))
		{
			 if(softlistResult!=null&&softlistResult.detailinfo!=null&&softlistResult.detailinfo.softicon!=null)
			 {
				 setImg(softlistResult.detailinfo.softicon);
			 }
			
		}
		
		initTitle();
		initTab();
		/*if (SoftwareMainDetilActivity.isClickFavoriteUpdate) {
			SoftwareMainDetilActivity.isClickFavoriteUpdate = false;
			click_button_favorites.setText("���ղ�");
		}*/
		//setDownloadButtonText();
		if(mpDialog!=null){
			mpDialog.dismiss();
		}
		handler.sendEmptyMessage(LOADG);
		flag=true;
		if (Constants.DEBUG)
			Log.i("��������", "���������ʾʱ��:" + System.currentTimeMillis());
	}
	
	/**
	 * ���¸�����
	 */
	private void initTitle() {
		if (softlistResult == null || softlistResult.detailinfo == null)
			return;
		//�п�
		if (downloadurl == null || "".equals(downloadurl)) {
			downloadurl = softlistResult.detailinfo.downloadurl;
			if(softicon==null||softicon.equals("")){
				setImg(softlistResult.detailinfo.softicon);
			}
		}
		
		TextView title = (TextView) findViewById(R.id.title_s);
		if (softlistResult != null && softlistResult.detailinfo != null){
			title.setText(softlistResult.detailinfo.softname);
		}
		
		RatingBar bar = (RatingBar) findViewById(R.id.star_s);
		bar.setProgress(softlistResult.detailinfo.softgrade*2/10);
		
		TextView size = (TextView) findViewById(R.id.size_s);
		if (softlistResult != null && softlistResult.detailinfo != null)
			size.setText(ServiceUtils
							.returnSpace(softlistResult.detailinfo.softsize));
		else
			size.setText(0);
	}
	
	/**
	 * �������ذ�ť������
	 */
	private void setDownloadButtonText() {
		 if(softid==0)return;
		int state = 0;
		// 0--���أ�1--������ 2--��װ 3 /hashcode --�Ѱ�װ  4 ����
		if (MyApplication.getInstance().getSoftMap().get( softid) != null) {
			state = MyApplication.getInstance().getSoftMap().get( softid);
		}
		siteInfoBean =AppContext.getInstance().taskList.get(softid);
  		switch (state) {
	 
		case 0:
			//����
			downloadstate = 0;
			Drawable icon = getBaseContext().getResources().getDrawable(R.drawable.detail_download);
			downloadLayout.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
			downloadLayout.setText("����");
			downloadLayout.setClickable(true);
			progressLayout.setVisibility(View.GONE);		
			downloadLayout.setVisibility(View.VISIBLE);
			break;
		case 1:
			//������
			downloadstate = 1;
			progressLayout.setVisibility(View.VISIBLE);		
			downloadLayout.setVisibility(View.GONE);
			
			setButton_visible(true);
			
			//puassView.setBackgroundResource(R.drawable.pause);
			//puassView.setOnClickListener(this);
			// siteInfoBean =AppContext.getInstance().taskList.get(softid);
			 
			if(siteInfoBean!=null)
			{
				//���ý�������߰���Ϊ��ͣ
				icon = getBaseContext().getResources().getDrawable(R.drawable.detail_pause);
				click_download_left.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
				
				float process=siteInfoBean.getProgress1();
				progressBar.setProgress((int)process) ;
				//puassView.setBackgroundResource(R.drawable.pause_detail);
				processTextView.setText(process+"%");
			}
			
			break;
		case 2:
			//��װ
			icon = getBaseContext().getResources().getDrawable(R.drawable.detail_install);
			downloadLayout.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
			downloadLayout.setText("��װ");
			downloadLayout.setClickable(true);
			downloadstate = 2;
			break;
		case 4:
			icon = getBaseContext().getResources().getDrawable(R.drawable.detail_update);
			downloadLayout.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
			downloadLayout.setText("����");
			downloadLayout.setClickable(true);
			downloadstate = 4;
			break;
		case 5:
			//������
			downloadstate = 5;
			progressLayout.setVisibility(View.VISIBLE);		
			downloadLayout.setVisibility(View.GONE);
			
			icon = getBaseContext().getResources().getDrawable(R.drawable.detail_continue);
			click_download_left.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
			
			setButton_visible(true);
			
			//puassView.setBackgroundResource(R.drawable.conti_detail);
			//puassView.setOnClickListener(this);
			//SiteInfoBean siteInfoBean =AppContext.getInstance().taskList.get(softid);
			if(siteInfoBean!=null)
			{
				float process=siteInfoBean.getProgress1();
				progressBar.setProgress((int)process) ;
				processTextView.setText(process+"%");
			}
			break;
		default:
			//4��Ĭ�϶����Ѱ�װ
			downloadstate = 3;
			downloadLayout.setText("��");
			downloadLayout.setClickable(true);
			icon = getBaseContext().getResources().getDrawable(R.drawable.detail_open);
			downloadLayout.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
		  break;
		}
	}
	
	@Override
	protected void onResume() {
		
		//System.out.println( "---onResume()-------"+getIntent().getIntExtra("softid", 0) );
		setDownloadButtonText();
		// Log.i("info","onResume="+flag);
		/*if (isClickFavorite) {
			if (ServiceUtils.checkLogin(SoftwareMainDetilActivity.this, false)) {
				showMessageInfo("�ղ���...");
				// �Ƿ��ѵ���ղذ�ť
				isClickFavorite = false;
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							// �ղ�
							String userid = SharedPreferencesControl
									.getInstance()
									.getString(
											"userid",
											com.appdear.client.commctrls.Common.USERLOGIN_XML,
											SoftwareMainDetilActivity.this);
							String token = SharedPreferencesControl
									.getInstance()
									.getString(
											"sessionid",
											com.appdear.client.commctrls.Common.USERLOGIN_XML,
											SoftwareMainDetilActivity.this);
							if(softlistResult!=null&&softlistResult.detailinfo!=null){
								ApiNormolResult result = ApiManager.addfavorite(
										userid, token, softlistResult.detailinfo.softid + "");
								if (result.isok == 1) {
									showMessageInfo("�ղسɹ�");
									refreshUI("isfavorite", "1");
								}
							}else{
								showMessageInfo("�ղ�ʧ��");
								refreshUI("isfavorite", "0");
							}
						} catch (ApiException e) {
							if (Constants.DEBUG)
								Log.e("net error:", e.getMessage(), e);
							showException(e);
						}
					}
				}).start();
			}
		}*/
		super.onResume();
	}

	/**
	 * ��ʼ��Tab��Ϣ
	 */
	private void initTab() {
		manager = getLocalActivityManager();
		tab = (TabbarNewDetail) findViewById(0x1234);
		if (tab == null) {
			if (Constants.DEBUG)
				Log.i("toptabbar", "toptabbar is  null");
		} else {
			// ����Tab����
			tab.setTitle(new String[] { "�������", "����", "ͬ���Ƽ�" });

			if (softwareDetailView == null) {
				softinfoIntent = new Intent(SoftwareMainDetilActivity.this,
						SoftWareDetailInfoActivity.class).putExtra("info",
						softlistResult);
				softinfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				if(downloadcount!=null&&downloadcount>=0){
					softinfoIntent.putExtra("downloadcount", downloadcount);
				}
				softwareDetailView = manager.startActivity("softinfo",
						softinfoIntent).getDecorView();
			}
			
			pagerContoler = new PagerContolerDetail(this);
			pagerContoler.setOnTouch(dip2px(105),3*height0/7);
			pagerContoler.setTabCallback(callback);
			pagerContoler.initViewPager(tab,softwareDetailView,view2,view3,false,true,true);
//			if(loadingView!=null){
//				loadingView.setVisibility(View.GONE);
//			}
		}
	}

	

	@Override
	public void onClick(View v) {
     		 
		if (v.getId()==R.id.download_layout_bottom) 
		{
		 
			if (softlistResult == null || softlistResult.detailinfo == null) {
				showMessageInfo("δ��ȡ����Ч���ݣ����Ժ�����!");
				return;
			}
			//  �жϵ�ǰ����button��״̬
			switch (downloadstate) {
			case 4:
			case 0:
				//isDel=false;
				if(downloadstate==0)
				{
					progressBar.setProgress(0) ;
	 				processTextView.setText(0+"%");
				}
				Drawable icon = getBaseContext().getResources().getDrawable(R.drawable.detail_pause);
				click_download_left.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);				
				if (!AppContext.getInstance().isNetState) {
					Toast.makeText(this, "���������������״̬", Toast.LENGTH_SHORT).show();
					return;
				}
				if (ad) {
					downloadurl+="&ad=1";
				}
				try {
					SiteInfoBean downloadbean = new SiteInfoBean(
							downloadurl,
							ServiceUtils.getSDCARDImg(Constants.APK_DATA) == null ? ""
									: ServiceUtils.getSDCARDImg(
											Constants.APK_DATA).getPath(),
							ServiceUtils.getApkname(downloadurl), softlistResult.detailinfo.softname, 
							softlistResult.detailinfo.softicon,
							softlistResult.detailinfo.version,
							softlistResult.detailinfo.softid, 
							softlistResult.detailinfo.appid,
							softlistResult.detailinfo.softsize, 0, 1, null, null, 
							BaseActivity.downloadUpdateHandler,Constants.SOFTPARAM);
					String[] msg = DownloadUtils.download(downloadbean,
							SoftwareMainDetilActivity.this);
					 
					if(msg!=null && msg[1].equals("0"))
					{ 
						progressLayout.setVisibility(View.VISIBLE);
						downloadLayout.setVisibility(View.GONE);		
						downloadstate=1;
					
						setButton_visible(true);
					} 
				    showMessageInfo(msg[0]);

				} catch (Exception e) {
				}
				break;
			case 2:
				// ��װ
				File file = ServiceUtils.getSDCARDImg(Constants.APK_DATA);
				String path = "";

				if (file != null && ServiceUtils.isHasFile(file.getPath() +"/" + ServiceUtils.getApkname(downloadurl))) {
					//ȡ��Դ�ļ�
					path = file.getPath() +"/" + ServiceUtils.getApkname(downloadurl);
				} else {
					//ȡ�ڴ�
					path = Constants.DATA_APK + "/" + ServiceUtils.getApkname(downloadurl);
				}
				if (ServiceUtils.isHasFile(path)) {
					ServiceUtils.Install(this, path, softlistResult.detailinfo.appid, softlistResult.detailinfo.softid, handler);
					new Thread(new Runnable() {
							
							@Override
							public void run() {
								ApiManager.downloadcomplete(downloadurl, "2","detail");
							}
					}).start();
				} else {
					Toast mScreenHint = Toast.makeText(this, "��װ�ļ�������",Toast.LENGTH_SHORT);
					mScreenHint.show();
				}
				break;
			case 3:
				try
				{
					Intent intent = this.getPackageManager().getLaunchIntentForPackage(softlistResult.detailinfo.appid);
					this.startActivity(intent);
				}catch (Exception e) {
					Toast mScreenHint = Toast.makeText(this, "��Ӧ�ò���ֱ�Ӵ�",Toast.LENGTH_SHORT);
					mScreenHint.show();
				}
				
				break;
			}
		} else if (v.getId() == R.id.click_download_left) {
			//����\��ͣ
 			 SiteInfoBean siteInfoBean =AppContext.getInstance().taskList.get( softid);
			switch (downloadstate) {
			//�����ʾ��ͣ
			case 1:
				//private ImageView puassView;
				//private TextView processTextView;
				downloadstate=5;
				Drawable icon = getBaseContext().getResources().getDrawable(R.drawable.detail_continue);
				click_download_left.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
				
		        MyApplication.getInstance().getSoftMap().put( softid, 5);
		        if(siteInfoBean!=null)
		        AppContext.getInstance().downloader.pauseTask(siteInfoBean);
				
				break;
			//�����ʾ����
			case 5:
				icon = getBaseContext().getResources().getDrawable(R.drawable.detail_pause);
				click_download_left.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
				
				downloadstate=1;
				MyApplication.getInstance().getSoftMap().put(softid, 1);	
				if(siteInfoBean!=null)
				DownloadUtils.addDownloadTask(siteInfoBean,this);
				
				break;
			}
		} else if (v.getId() == R.id.click_download_cancel) {
			//ɾ��
			 SiteInfoBean siteInfoBean =AppContext.getInstance().taskList.get(softid);
			if (softid != 0) {
				if (siteInfoBean != null&&siteInfoBean.siteFileFecth!=null)
					siteInfoBean.siteFileFecth.siteStop();			
				AppContext.getInstance().downloader.downDb.delete(softid);				
				MyApplication.getInstance().getSoftMap().remove(softid);
				if (siteInfoBean == null)
					siteInfoBean = AppContext.getInstance().taskList.get(softid);
				AppContext.getInstance().taskList.remove(softid);
				AppContext.getInstance().taskSoftList.remove(String.valueOf(softid));
				if (FileDownloaderService.notificationManager != null)
					FileDownloaderService.notificationManager.cancel(softid);
				FileDownloaderService.task_num--;
				FileDownloaderService.setNotification(this);
				 ServiceUtils.deleteSDFile(siteInfoBean.sFilePath + "/" + siteInfoBean.sFileName);
				 ServiceUtils.deleteSDFile(siteInfoBean.sFilePath + "/" + siteInfoBean.sFileName + ".size");
				progressBar.setProgress(0) ;
 				processTextView.setText(0+"%");
 				progressLayout.setVisibility(View.GONE);		
				downloadLayout.setVisibility(View.VISIBLE);
				downloadstate = 0;
				setButton_visible(false);
				//isDel=true;
			}
		} else if (v.getId() == R.id.click_button_favorite_) {
			//�ղ�
			if (!AppContext.getInstance().isNetState) {
				showMessageInfo("������󣬲��ܽ��иò���");
				return;
			}
			if (!ServiceUtils.checkLogin(SoftwareMainDetilActivity.this,
					true)) {
				SoftwareMainDetilActivity.isClickFavorite = true;
				return;
			} else {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							// �ղ�
							String userid = SharedPreferencesControl
									.getInstance()
									.getString(
											"userid",
											com.appdear.client.commctrls.Common.USERLOGIN_XML,
											SoftwareMainDetilActivity.this);
							String token = SharedPreferencesControl
									.getInstance()
									.getString(
											"sessionid",
											com.appdear.client.commctrls.Common.USERLOGIN_XML,
											SoftwareMainDetilActivity.this);
							int softid=softlistResult.detailinfo.softid;
							ApiNormolResult result = ApiManager
									.addfavorite(userid, token, softid+ "");
							if (result.isok == 1) {
								showMessageInfo("�ղسɹ�");
								refreshUI("isfavorite", "1");
							}
							
							
						} catch (ApiException e) {
							if (Constants.DEBUG)
								Log.e("net error:", e.getMessage(), e);
							showException(e);
						}
					}
				}).start();
			}
		} else if (v.getId() == R.id.click_button_share_) {
			final selectShareOnClickListener choiceListener = new selectShareOnClickListener();
			 
			 List<String>  data=new ArrayList<String> ();
			 data.add("��������΢��");data.add("���ŷ��������");
			 ShareItemAdapter adapter=new ShareItemAdapter(SoftwareMainDetilActivity.this,data);
          	new AlertDialog.Builder(SoftwareMainDetilActivity.this).setTitle("����").setAdapter(adapter, choiceListener).show();
           //.setNegativeButton("ȡ��", null)
			//���ŷ���
			//if(result!=null)
			//smsShare(result.detailinfo);
		}
	}
	
	private   class ShareItemAdapter extends BaseAdapter {
		private LayoutInflater _inflater;
		private List<String> data;
		public ShareItemAdapter(Context context,List<String>  data) {
			_inflater = LayoutInflater.from(context);
			this.data=data;
		}

		public int getCount() {
			return data.size();
		}

		public Object getItem(int position) {
			return data.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {				 
			convertView = _inflater.inflate(R.layout.alert_user_share_select, null);				 
			TextView title = (TextView) convertView.findViewById(R.id.title);
			ImageView icon = (ImageView) convertView.findViewById(R.id.imageView);
			title.setText(data.get(position));
			if(position==0)
			{
			//	title.setText("1111");
				icon.setImageResource(R.drawable.share_163_icon);
			}else
			{
				//title.setText("1111");
				icon.setImageResource(R.drawable.share_sms_icon);
			}
			 
			return convertView;
		}
	}
	
	private class selectShareOnClickListener implements DialogInterface.OnClickListener {
        private int which = 0;
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
            	this.which = which;
                String softurl = AppContext.getInstance().wap_url + "sid=" + softlistResult.detailinfo.softid;
    			if ("".equals(softurl)) {
    				softurl = "www.appdear.com"; // �����ȡ�������ӵ�ַ�Ļ��������ð�Ƥ��ҳΪĬ�ϵ�����
    			}        			
    			// ��ȡĬ�϶�������
    		    shareContent = Utils.getDefaultSmsContent(softlistResult.detailinfo.softname, softurl);                    
                
                if(which==0)
                {
               	 weiboShare();
                }else
                {
               	 smsShare();
                }
              //  Toast.makeText(SoftWareDetailInfoActivity.this, "wwwww"+which,Toast.LENGTH_SHORT ).show();
                dialogInterface.cancel();
            }
            public int getWhich() {
                    return which;
            }
    }
	
	 /**
	 * ���ŷ���
	 */
	private void smsShare() {			
		// ������ű༭����
		Utils.gotoSmsEditPage(SoftwareMainDetilActivity.this, shareContent);
	}
	/**
	 * 163΢������
	 */
	private void weiboShare() {
		// ����������ҳ�������·��		
		
		 
	 	if(ServiceUtils.check163Login(SoftwareMainDetilActivity.this,shareContent))
	 	{ 
	 		if (!AppContext.getInstance().isNetState) {
				Toast.makeText(this, "���������������״̬", Toast.LENGTH_SHORT).show();
				return;
			}
	 	 	new LoginWeiboThread(MyApplication.getInstance().username163,  MyApplication.getInstance().password163, -1,handler1,shareContent).start();		 		 
	 	} 			 
	}
	
	private Handler handler1=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) 
			{
				case BaseActivity.SHARE_WEIBO_SUCESS: // ����΢���ɹ�
					Toast.makeText(SoftwareMainDetilActivity.this, "΢�����ͳɹ�", Toast.LENGTH_SHORT).show();
					 
					break;
				case BaseActivity.SHARE_WEIBO_FAILURE:  //����΢��ʧ��
					Bundle b = msg.getData();
					String message = b.getString("message");
					Toast.makeText(SoftwareMainDetilActivity.this,message, Toast.LENGTH_SHORT).show();				 
				break;	
			}
		}
	};

	@Override
	public void finish() {
 		if (mReceiver != null) {
			this.unregisterReceiver(mReceiver);
			mReceiver=null;
		}
		if (mReceiver1 != null) {
			this.unregisterReceiver(mReceiver1);
			mReceiver1=null;
		}
		dbinfo=null;
		loadingView=null;
		if (softinfoLayout != null && softwareDetailView != null) {
			softinfoLayout.removeView(softwareDetailView);
		}
		if (manager != null) {
			manager.destroyActivity("comment", true);
			manager.destroyActivity("anthor", true);
			manager.destroyActivity("softinfo", true);
		}
		
		try {
			for (String url : softlistResult.imgurl) {		 
						imageCache.remove(url);
			}
			 
		} catch (Exception e) {
		}
		if (mReceiver != null) {
			this.unregisterReceiver(mReceiver);
			mReceiver=null;
		}
		if (mReceiver1 != null) {
			this.unregisterReceiver(mReceiver1);
			mReceiver1=null;
		}
		super.finish();
	}

	private void recycledImg(List<String> imgurl) {
		if (imgurl == null || imgurl.size() == 0) {
			return;
		}
		if (imageCache != null) {
			BitmapTemp temp = null;
			for (String url : imgurl) {
				if ((temp = imageCache.isCached(url)) != null) {
					if (temp != null && temp.bitmap != null
							&& temp.bitmap.isRecycled() == false
							 ) {
						if (Constants.DEBUG)
							Log.i("info", "recycle");
					//	temp.bitmap.recycle();
						imageCache.remove(url);
					//	 System.out.println("-SoftwareMainDetilActivity---recycledImg()----------"+url);
					}
					
				}
			}
		}
	}

	@Override
	protected void onStart() {
		filter = new IntentFilter();
		//�������ˢ�½���
	
		if(mReceiver1!=null){
			filter.addAction(Common.DOWNLOAD_NOTIFY);
			registerReceiver(mReceiver1, filter);
		}
		//��װ���ˢ�½���
		
		if(mReceiver!=null){
			filter.addAction(Intent.ACTION_PACKAGE_ADDED);
			filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
			filter.addDataScheme("package");
			registerReceiver(mReceiver, filter);
		}
		super.onStart();
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			//��װ����Ѱ�װ
			if (intent == null || intent.getDataString() == null 
					|| intent.getDataString().equals(""))
				return;
			String packageName = intent.getDataString().substring(8);
 			if(packageName!=null&&!"".equals(packageName)&&SoftwareMainDetilActivity.appname!=null&&SoftwareMainDetilActivity.appname.equals(packageName))
			{
				int softid = -1;
				if (MyApplication.getInstance().getAppMap().get(packageName) != null) {
					softid = MyApplication.getInstance().getAppMap().get(packageName);
				}
				if (softid != -1) {
					MyApplication.getInstance().getSoftMap().put(softid, Math.abs(packageName.hashCode()));
					MyApplication.getInstance().getAppMap().remove(packageName);
				}
			 
				Drawable icon = getBaseContext().getResources().getDrawable(R.drawable.detail_open);
				downloadLayout.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
				downloadLayout.setText("��");
				downloadLayout.setClickable(true);
				downloadstate = 3;
			
			}
		};
	};
	
	protected BroadcastReceiver mReceiver1 = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			 /*  if(isDel)
			   {
				   return;
			   }*/
			   int softid=	intent.getIntExtra("softid", -1);
			   float process=intent.getFloatExtra("dprocess",0);
 
			   int downloadfinsh=intent.getIntExtra("downloadfinsh", 0);
			   if(SoftwareMainDetilActivity.this.softid==softid)
			   {
				   
				   if(downloadfinsh==1)
				   {
					   progressLayout.setVisibility(View.GONE);
					    downloadLayout.setVisibility(View.VISIBLE);
					    
					    setButton_visible(false);
					    
					    Drawable icon = getBaseContext().getResources().getDrawable(R.drawable.detail_install);
						downloadLayout.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
						downloadLayout.setText("��װ");
						downloadLayout.setClickable(true);
						downloadstate = 2;
				   }else
				   { 
					   processTextView.setText(process+"%");
					   SoftwareMainDetilActivity.this.progressBar.setProgress((int)process);  	
				   }
				   
			   }
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (mReceiver != null) {
			this.unregisterReceiver(mReceiver);
			mReceiver=null;
		}
		if (mReceiver1 != null) {
			this.unregisterReceiver(mReceiver1);
			mReceiver1=null;
		}
		super.onDestroy();
	}

	@Override
	public View viewFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View viewSecend() {
		// TODO Auto-generated method stub
		if (commentView == null) {
			int softid = 0;
			String appid = "";
			if (softlistResult != null && softlistResult.detailinfo != null) {
				softid = softlistResult.detailinfo.softid;
				appid = softlistResult.detailinfo.appid;
			}
			commentView = manager.startActivity(
					"comment",
					new Intent(SoftwareMainDetilActivity.this,
							SoftwareDetailCommentActivity.class).putExtra(
							"softid", softid + "").putExtra("appid",
									appid)).getDecorView();
		}
		return commentView;
	}

	@Override
	public View viewThird() {
		// TODO Auto-generated method stub
		if (authorInfoView == null) {
			// 2011 - 09 - 16 ������Ϣ����Ϊͬ������Ƽ�
			// TODO:�ȴ��������˷������id
			String catid = "0";
			if (softlistResult != null) {
				if (softlistResult.detailinfo != null
						&& !softlistResult.detailinfo.equals("")) {
					catid = softlistResult.detailinfo.catid + "";
				}
			}
			int softid = 0;
			if (softlistResult != null && softlistResult.detailinfo != null) {
				softid = softlistResult.detailinfo.softid;
			}
			authorInfoView = manager.startActivity(
					"anthor",
					new Intent(SoftwareMainDetilActivity.this,
							SoftwareDetailAuthorInfoActivity.class).putExtra(
							"category_id", catid).putExtra("softid",
									softid)).getDecorView();
		}
		return authorInfoView;
	}
	boolean flag=false;
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		if(flag){
			super.onSaveInstanceState(outState);
		}
	}

	public int dip2px(float dipValue){ 
		 return (int)(dipValue * ds + 0.5f); 
	} 
}
