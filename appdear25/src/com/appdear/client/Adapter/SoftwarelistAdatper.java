/**
 * TheSpecialListAdatper.java
 * created at:2011-5-26����03:32:23
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */
package com.appdear.client.Adapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Message;
import android.text.SpannableString;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.appdear.client.QianmingDialog;
import com.appdear.client.R;
 
import com.appdear.client.commctrls.AsynLoadImageView;
import com.appdear.client.commctrls.BaseActivity;
import com.appdear.client.commctrls.Common;
import com.appdear.client.commctrls.ListBaseActivity;
import com.appdear.client.commctrls.ListViewRefresh;
import com.appdear.client.commctrls.LoginWeiboThread;

import com.appdear.client.commctrls.SharedPreferencesControl;
import com.appdear.client.download.DownloadUtils;
import com.appdear.client.download.SiteInfoBean;
import com.appdear.client.exception.ApiException;
import com.appdear.client.model.SoftlistInfo;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.Constants;
import com.appdear.client.service.MyApplication;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.service.api.ApiNormolResult;
import com.appdear.client.utility.AsyLoadImageService;
import com.appdear.client.utility.BitmapTemp;
import com.appdear.client.utility.ImageCache;
import com.appdear.client.utility.ServiceUtils;
import com.appdear.client.utility.Utils;

/**
 * ����б�adapter
 * 
 * <code>title</code> abstract
 * <p>
 * description
 * <p>
 * example: <blockquote>
 * 
 * <pre>
 * </blockquote>
 * </pre>
 * @author Author
 * @version Revision Date
 */
public class SoftwarelistAdatper extends ArrayAdapter<SoftlistInfo> implements
		OnClickListener  {
	private Animation mTrackAnim;
	private ListBaseActivity context;
	private List<SoftlistInfo> list;
	private ListViewRefresh listview;
	private LayoutInflater mInflater;
	// private boolean
	// isloadsofticon=SharedPreferencesControl.getInstance().getBoolean(
	// "loadsofticon",com.appdear.client.commctrls.Common.SETTINGS,
	// AppContext.getInstance().appContext);;
//	 private   Map<String, SoftReference<Bitmap>> imageCache = MyApplication.getInstance().getImageCache();
	// ��������layout�л���ʾ ��ʼֵΪ-1 ����ʾ�����ؼ�
//	private int index = -1;

	private String keyword = "";
	private static Bitmap bitmap;
	 // ��������ȡ��������
	private  String shareContent;
	/**
	 * ��ʼ������
	 */
	public SoftwarelistAdatper(ListBaseActivity context,
			List<SoftlistInfo> list, ListViewRefresh listview) {
		super(context, R.layout.soft_list_item_layout, list);
		this.context = context;
		this.list = list;
		this.listview = listview;
		mInflater = LayoutInflater.from(context);
		if (bitmap == null) {
			bitmap = readBitMap(context, R.drawable.soft_lsit_icon_default);
		}
		this.setNotifyOnChange(true);
	}

	/**
	 * ��ʼ������
	 */
	public SoftwarelistAdatper(ListBaseActivity context,
			List<SoftlistInfo> list, ListViewRefresh listview, String keyword) {
		super(context, R.layout.soft_list_item_layout, list);
		this.context = context;
		this.list = list;
		this.keyword = keyword;
		this.listview = listview;
		if (bitmap == null) {
			bitmap = readBitMap(context, R.drawable.soft_lsit_icon_default);
		}
		mInflater = LayoutInflater.from(context);
		this.setNotifyOnChange(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (list != null && list.size() < 5) {
			this.setNotifyOnChange(false);
		}
		return list == null ? 0 : list.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}
	public String getStr(String str){
		if(str==null)return "";
		else return str;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
  		SoftlistInfo item = this.getItem(position);
		View view = null;
		if (convertView != null) {
			view = convertView;
		} else {
			view = mInflater.inflate(R.layout.soft_list_item_layout, parent,
					false);
		}

		ItemViewHolder holder = (ItemViewHolder) view.getTag();
		if (holder == null) {

			holder = new ItemViewHolder();
			holder.icon = (AsynLoadImageView) view.findViewById(R.id.imageView);
			holder.icon.setDefaultImage(R.drawable.soft_lsit_icon_default);
			holder.icon.setImageResource(R.drawable.soft_lsit_icon_default);
			holder.titleTextView = (TextView) view.findViewById(R.id.title);
			holder.contentTextView = (TextView) view.findViewById(R.id.desc);
			holder.priceTextView = (TextView) view.findViewById(R.id.price);
			holder.processTextView=(TextView) view.findViewById(R.id.process);
			holder.starView = (RatingBar) view.findViewById(R.id.star);
			holder.pricelayout = (RelativeLayout) view.findViewById(R.id.pricelayout);
			holder.sizeAndPriceLayout = (RelativeLayout) view.findViewById(R.id.sizeAndPriceLayout);
			holder.contentLayout = (RelativeLayout) view.findViewById(R.id.contentLayout);
			holder.shoufa=(TextView)view.findViewById(R.id.shoufa);
			// ���ͼ����ֵĸ�����
			// ���ͼ����ֵĸ�����
			holder.actionLayout = (LinearLayout) view.findViewById(R.id.actionLayout);
			holder.shareAndfavLayout=(RelativeLayout)view.findViewById(R.id.shareAndfavLayout);

			// �ղ�/���ղ�
			holder.favoriteImageView = (ImageView) view
					.findViewById(R.id.favoriteImageView);
			holder.favoriteImageView
					.setBackgroundResource(R.drawable.favorite_button_list);
			// ����
			holder.shareImageView = (ImageView) view
					.findViewById(R.id.shareImageView);
			holder.shareImageView
					.setBackgroundResource(R.drawable.share_button_list);
		}else{
			holder.isupload=false;
		}
		// ��ʼ״̬
		if (item.softprice == 0 && item.softpoints == 0) {
			holder.state = 0;// #2a90c6 32+10 90 12*16+6 42,90,198
			// �ɵ��ʱ����״̬
			//holder.sizeAndPriceLayout.setEnabled(true);
			holder.pricelayout.setEnabled(true);
		} else {
			// ����
			holder.state = 10;
		}

		if (item != null) {

			if (Common.ISLOADSOFTICON) {
				if ("".equals(item.softicon)) {
					holder.icon
					.setImageResource(R.drawable.soft_lsit_icon_default);
				} else {
					if ("".equals(getStr(item.softiconPath))) {

						File fileDir = ServiceUtils
								.getSDCARDImg(Constants.CACHE_IMAGE_DIR);

						String f[] = item.softicon.replace("http://", "")
								.split("/");

						String filename = f[f.length - 1];

						// �鿴�ļ��ǲ��Ǵ���
						if (fileDir != null
								&& fileDir.exists()
								&& new File(fileDir.getAbsoluteFile() + "/"
										+ filename).exists()) {

							String filepath = fileDir.getAbsoluteFile() + "/"
									+ filename;

							
							try{
								holder.icon.setImageURI(Uri.parse(filepath));
					    	}catch (OutOfMemoryError e) {
								System.gc();
								Log.e("load image", "�ڴ������");
							}	
							item.softiconPath = filepath;

						} else {
                               
							Bitmap bitmap =MyApplication.getInstance().getBitmapByUrl(item.softicon);
							
						//	BitmapTemp bite = imageCache.isCached(item.softicon);
							if (bitmap != null) {
									holder.icon.setImageBitmap( bitmap);
						    	 		
							} else {
								holder.icon.setImageResource(R.drawable.soft_lsit_icon_default);

								// ����
								if (listview != null
										&& listview.getCountflag() != OnScrollListener.SCROLL_STATE_FLING
										&& listview.getCountflag() != OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

									holder.icon.setImageUrl(item.softicon,
											position, listview, true);
								}

							}

							//
						}

					} else {
						// �Ѿ���ֵ��

						if (!new File(getStr(item.softiconPath)).exists()) {
							// ���ص����ڴ�
							Bitmap bitmap =MyApplication.getInstance().getBitmapByUrl(item.softicon);

							//BitmapTemp bite = imageCache.isCached(item.softicon);
							if (bitmap != null ) {
								holder.icon.setImageBitmap( bitmap);
							} else {

								holder.icon
										.setImageResource(R.drawable.soft_lsit_icon_default);

								// ����
								if (listview != null
										&& listview.getCountflag() != OnScrollListener.SCROLL_STATE_FLING
										&& listview.getCountflag() != OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

									holder.icon.setImageUrl(item.softicon,
											position, listview, true);
								}

							}

						} else {
							//
							try{
								holder.icon.setImageURI(Uri.parse(getStr(item.softiconPath)));
								holder.isupload=true;
							}catch (OutOfMemoryError e) {
								System.gc();
							}
						}

					}
			
				}
		
			}else{
				holder.icon
				.setImageResource(R.drawable.soft_lsit_icon_default);
			}
			holder.processTextView.setVisibility(View.GONE);
			if (keyword != null && !keyword.equals("")) {
				// �����tittle�������ؼ��ֱ�Ǹ���
				SpannableString highlightSoftName = Utils.getSpannableString(
						item.softname, this.keyword,
						Color.parseColor("#2a90c6"));
				holder.titleTextView.setText(highlightSoftName);
			} else
				holder.titleTextView.setText(item.softname);
			if(holder.contentTextView!=null)
			holder.contentTextView.setText(item.softdesc);
			holder.priceTextView.setText(ServiceUtils
					.returnSpace(item.softsize));

			// �¼Ӵ��� ��ȫ��mapȡֵ������ʾ״̬
			holder.pricelayout.setVisibility(View.VISIBLE);
			holder.priceTextView.setVisibility(View.VISIBLE);
			holder.processTextView.setVisibility(View.GONE);
			if(item.isfirst.equals("1")){
				holder.shoufa.setVisibility(View.VISIBLE);
			}else{
				holder.shoufa.setVisibility(View.INVISIBLE);
			}
			int i = 0;// Ĭ��ֵ��ʾ����
			if (MyApplication.getInstance().getSoftMap().get(item.softid) != null) {
				i = MyApplication.getInstance().getSoftMap().get(item.softid);
			}
			SiteInfoBean siteInfoBean =null;
			switch (i) {
			case 0:
				holder.pricelayout
						.setBackgroundResource(R.drawable.download_image_download);
//				holder.priceTextView.setTextColor(Color.parseColor("#2a90c6"));
				holder.state = 0;// #2a90c6 32+10 90 12*16+6 42,90,198
				// �ɵ��ʱ����״̬
				//holder.sizeAndPriceLayout.setEnabled(true);
				holder.pricelayout.setEnabled(true);
				break;
			case 1:
				holder.pricelayout
						.setBackgroundResource(R.drawable.download_image_downloading);
				holder.state = 1;
				// �ɵ��ʱ����״̬
				holder.sizeAndPriceLayout.setEnabled(true);
				holder.processTextView.setVisibility(View.VISIBLE);
				  siteInfoBean =AppContext.getInstance().taskList.get(item.softid);
				if(siteInfoBean!=null)
				{
					holder.processTextView.setText(siteInfoBean.getProgress1()+"%");
				}
				//holder.processTextView.setTextColor(Color.RED);
			//	holder.priceTextView.setVisibility(View.GONE);
				break;
			case 2:
				holder.pricelayout
						.setBackgroundResource(R.drawable.download_image_install);

				holder.state = 2;
				holder.processTextView.setVisibility(View.GONE);
				//holder.processTextView.setTextColor(Color.RED);
 				break;
			case 4:
				holder.pricelayout
						.setBackgroundResource(R.drawable.download_image_update);
				holder.state = 4;
//				holder.priceTextView.setText("����");
				//holder.priceTextView.setTextColor(Color.parseColor("#2a90c6"));
				// �ɵ��ʱ����״̬
				//holder.sizeAndPriceLayout.setEnabled(true);
				holder.pricelayout.setEnabled(true);
				break;
			case 5:
				holder.pricelayout
						.setBackgroundResource(R.drawable.conti);
				holder.state = 5;
				holder.sizeAndPriceLayout.setEnabled(true);
				holder.processTextView.setVisibility(View.VISIBLE);
				  siteInfoBean =AppContext.getInstance().taskList.get(item.softid);
				if(siteInfoBean!=null)
				{
					holder.processTextView.setText(siteInfoBean.getProgress1()+"%");
				}
				//holder.processTextView.setTextColor(Color.RED);
				// �ɵ��ʱ����״̬
				holder.sizeAndPriceLayout.setEnabled(true);	
			//	holder.priceTextView.setVisibility(View.GONE);
				break;
			default:
				holder.pricelayout.setBackgroundResource(R.drawable.download_image_installed);
				holder.state = 3;
//				holder.priceTextView.setText("�Ѱ�װ");
	
			//	holder.priceTextView.setTextColor(Color.rgb(66, 66, 66));
				//holder.sizeAndPriceLayout.setEnabled(false);
				holder.pricelayout.setEnabled(false);
			  break;
			}

			if (listview != null
					&& listview.getCountflag() != OnScrollListener.SCROLL_STATE_FLING
					&& listview.getCountflag() != OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
				holder.pricelayout.setVisibility(View.VISIBLE);
			}
			holder.item = item;
			holder.position = position;
			//holder.sizeAndPriceLayout.setTag(holder);
			//holder.sizeAndPriceLayout.setOnClickListener(this);
			holder.pricelayout.setTag(holder);
			holder.pricelayout.setOnClickListener(this);
			holder.starView.setProgress(item.softgrade * 2 / 10);
           /* if(listview.getIndex()!=-1&& listview.getIndex()==position)
            {
            	System.out.println("-----111");
            	if ( listview.findViewWithTag("index"+listview.getIndex())!=null) 
    			{
            		System.out.println("-----222");
    				RelativeLayout   layout=(RelativeLayout )listview.findViewWithTag("index"+listview.getIndex());
    				layout.setVisibility(View.GONE);
    				layout.setTag(null);
    				listview.setIndex(-1);
     			}
            }*/
			// �ж���ʾ�Ǹ�һ�ؼ�
			holder.icon.setTag(holder);
			holder.shareImageView.setTag(holder);
			holder.favoriteImageView.setTag(holder);
			holder.processTextView.setTag(item.softid);
			holder.icon.setOnClickListener(this);
			//holder.icon.setOnFocusChangeListener(this);

			holder.shareImageView.setOnClickListener(this);
			holder.favoriteImageView.setOnClickListener(this);
			 
			view.setTag(holder);
		}
 
 		return view;
	}

	@Override
	public void onClick(View v) {
		

		switch (v.getId()) {
		case R.id.pricelayout:
			
			
			v.requestFocus();
			actionLayoutNotShow();
			final ItemViewHolder holder = (ItemViewHolder) v.getTag();
		//	holder.priceTextView.setVisibility(View.GONE);
			if (holder.state == 0 || holder.state == 4) {
				if (download(holder.item, holder.item.downloadurl,holder.state)) {
					holder.state = 1;
 					holder.pricelayout
							.setBackgroundResource(R.drawable.download_image_downloading);
					holder.pricelayout.setEnabled(true);
					holder.processTextView.setVisibility(View.VISIBLE);
					holder.processTextView.setText("0"+"%");
				//	System.out.println("-----download==="+holder.item.downloadurl);
				}else
				{
					holder.priceTextView.setVisibility(View.VISIBLE);
				}
			} else if(holder.state == 1)
			{
			//R.drawable.pause
				holder.state = 5;
//				holder.priceTextView.setText("������");
				 
				holder.pricelayout
						.setBackgroundResource(R.drawable.conti);
				holder.pricelayout.setEnabled(true);
				MyApplication.getInstance().getSoftMap().put(holder.item.softid, 5);	
				SiteInfoBean siteInfoBean =AppContext.getInstance().taskList.get(holder.item.softid);
				AppContext.getInstance().downloader.pauseTask(siteInfoBean);
			}else if(holder.state == 5)
			{
				holder.state = 1;
//				holder.priceTextView.setText("������");
				 
				
				holder.pricelayout.setBackgroundResource(R.drawable.download_image_downloading);
				holder.pricelayout.setEnabled(true);
				MyApplication.getInstance().getSoftMap().put(holder.item.softid, 1);	
				SiteInfoBean siteInfoBean =AppContext.getInstance().taskList.get(holder.item.softid);
				DownloadUtils.addDownloadTask(siteInfoBean,context);
			} else if (holder.state == 2) {
				holder.priceTextView.setVisibility(View.VISIBLE);
				holder.state = 2;
				// ��װ
				String path = "";
				File file = ServiceUtils.getSDCARDImg(Constants.APK_DATA);
				if (file != null && ServiceUtils.isHasFile(file.getPath() +"/" + ServiceUtils.getApkname(holder.item.downloadurl))) {
					//ȡ��Դ�ļ�
					path = file.getPath() +"/" + ServiceUtils.getApkname(holder.item.downloadurl);
				} else {
					//ȡ�ڴ�
					path = Constants.DATA_APK + "/" + ServiceUtils.getApkname(holder.item.downloadurl);
				}
			    
				if (ServiceUtils.isHasFile(path)) {
					MyApplication.getInstance().getAppMap().put(holder.item.appid, holder.item.softid);
					if(ServiceUtils.isInstall(context,file,holder.item.appid)){
							installhandler(holder,path);
					}else{
							dojumpqian(context,"�����",holder.item.appid);
					}
				} else {
					Toast mScreenHint = Toast.makeText(context, "��װ�ļ�������",Toast.LENGTH_SHORT);
					mScreenHint.show();
				}
				
				//�ϴ���־���㰲װʱ
				
			}
			break;
		case R.id.shareImageView:
			 actionLayoutNotShow();
			clickShareImage(v);
			
			break;
		case R.id.favoriteImageView:
			 actionLayoutNotShow();
			clickFavoriteImage(v);

			break;
		case R.id.imageView:
			clickIconImage(v);
			break;
		}
	}

	public void installhandler(final ItemViewHolder holder,String path){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.putExtra("installappdear", holder.item.softid);
		intent.setDataAndType(
				Uri.parse("file://" + path),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ApiManager.downloadcomplete(holder.item.downloadurl, "2");
			}
		}).start();
	}
	public static void dojumpqian(Context context,String softname,String appid){
		Intent intent=new Intent(context,QianmingDialog.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("softname",softname);
		intent.putExtra("appid", appid);
		context.startActivity(intent);
	}
	 private class ShareItemAdapter extends BaseAdapter {
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
			Utils.gotoSmsEditPage(context, shareContent);
		}
		/**
		 * 163΢������
		 */
		private void weiboShare() {
			// ����������ҳ�������·��		 
		 	if(ServiceUtils.check163Login(context,shareContent))
		 	{ 
		 		if (!AppContext.getInstance().isNetState) 
		    	 {
		    		 context.showMessageInfo("���������������״̬");
					 return;
				 }
		 	 	new LoginWeiboThread(MyApplication.getInstance().username163,  MyApplication.getInstance().password163,-1, context.handler1,shareContent).start();		 		 
		 	} 			 
		}
	 public void clickShareImage(View v) {
 		  context.isrefreshUI = true;
		  // ��ȡ�����Ϣ�������+���ص�ַ��
		  v.requestFocus();
		  ItemViewHolder holder = (ItemViewHolder) v.getTag();
		  String softname = holder.item.softname;

		  // ����������ҳ�������·��
		  String softurl = AppContext.getInstance().wap_url + "sid="
				+ holder.item.softid;
		  if ("".equals(softurl)) {
			softurl = "www.appdear.com"; // �����ȡ�������ӵ�ַ�Ļ��������ð�Ƥ��ҳΪĬ�ϵ�����
		 }
		 // ��ȡĬ�϶�������
		 shareContent = Utils.getDefaultSmsContent(softname,softurl);
	     Activity pActivity = ((Activity) context).getParent();	
	     if (pActivity == null) {
	    	 pActivity=context;
			}
		 final selectShareOnClickListener choiceListener = new selectShareOnClickListener();			 
		 List<String>  data=new ArrayList<String> ();
		 data.add("��������΢��");data.add("���ŷ��������");
		 ShareItemAdapter adapter=new ShareItemAdapter(context,data);
    	 new AlertDialog.Builder(pActivity).setTitle("����").setAdapter(adapter, choiceListener).show();
	 }
    
    public void  actionLayoutNotShow()
    {
    	if(listview.getIndex()==-1) return;
 
    		
    		if ( listview.findViewWithTag("index"+listview.getIndex())!=null) 
			{
				RelativeLayout   layout=(RelativeLayout )listview.findViewWithTag("index"+listview.getIndex());
				layout.setVisibility(View.GONE);
				layout.setTag(null);
				listview.setIndex(-1);
 			}
 
    }
	/**
	 * ����iconͼ�갴ť
	 */

	 public void clickIconImage(View v) {
			ItemViewHolder holder = (ItemViewHolder) v.getTag();
        //  System.out.println("-------clickIconImage-------position="+holder.position);
        //  System.out.println("last="+listview.getLastVisiblePosition()+"    first="+listview.getFirstVisiblePosition());
			if(listview==null)return;
			if(listview.getIndex()!=-1)
			{
				if (listview != null&&listview.findViewWithTag("index"+listview.getIndex())!=null) 
				{
					RelativeLayout   layout=(RelativeLayout )listview.findViewWithTag("index"+listview.getIndex());
					layout.setVisibility(View.GONE);
					//((LinearLayout)layout.findViewById(R.id.actionLayout)).setVisibility(View.GONE);
					layout.setTag(null);
					//index = -1;
					listview.setIndex(-1);
					if(layout==holder.shareAndfavLayout)
					{
						return;
					}
				}
			}
			v.requestFocus();
			int[] xy = new int[2];
			v.getLocationInWindow(xy);
			 Rect rect = new Rect(xy[0], xy[1]+30, xy[0]+v.getWidth(), xy[1]+v.getHeight());	
			Display display = context.getWindowManager().getDefaultDisplay(); 

			int height = display.getHeight();
			int actualHeight = 0;
			 
			if (height > 480) {
				actualHeight = (int) (height * 0.75);
			} else {
				actualHeight = (int) (height * 0.7);
			}
 		   if (rect.top > actualHeight) {
 
			 if(listview.getHeaderViewsCount()==0)
			 {
				 listview.setSelectionFromTop(holder.position,(listview.getHeight() - ServiceUtils.dip2px(67+63,context)+3));
			 }else
			 {
				 listview.setSelectionFromTop(holder.position,(listview.getHeight() - ServiceUtils.dip2px(67*2+63,context)+3)); 
			 }
 
		 	} 
 			 holder.shareAndfavLayout.setTag("index"+holder.position);
			 listview.setIndex(holder.position);
 			 holder.shareAndfavLayout.setVisibility(View.VISIBLE);
 		     mTrackAnim = AnimationUtils.loadAnimation(context, R.anim.quickaction);
			 mTrackAnim.setInterpolator(new Interpolator() {
					public float getInterpolation(float t) {	 
						final float inner = (t * 1.55f) - 1.1f;
 						return 1.2f - inner * inner;
					}
				});			 
			 holder.actionLayout.startAnimation(mTrackAnim);
			    
		 
			 
	 	 
		}
	 public int dip2px(float dipValue){ 
		 float ds = ServiceUtils.getMetrics(((Activity) context).getWindowManager()).density;

		 return (int)(dipValue * ds + 0.5f); 
	} 
 
	/**
	 * �ղ�
	 * 
	 * @param info
	 * @param holder
	 */
	public void favorite(final ItemViewHolder holder,
			final boolean isaddFavorite) {
		Activity pActivity = ((Activity) context).getParent();
		if (pActivity == null) {
			pActivity = context;
		}
		if (!ServiceUtils.checkLogin(pActivity, isaddFavorite,holder.item.softid)) {
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
										context);
						String token = SharedPreferencesControl
								.getInstance()
								.getString(
										"sessionid",
										com.appdear.client.commctrls.Common.USERLOGIN_XML,
										context);
						if (isaddFavorite) {
							// ����ղ�
							ApiNormolResult result = ApiManager.addfavorite(
									userid, token, holder.item.softid + "");
							if (result.isok == 1) {
								context.showMessageInfo("�ղسɹ���");
								 
							} else {
								context.showMessageInfo("�ղ�ʧ�ܣ�"); 
							}
						} else {
							// ����Ƿ����ղ�
							ApiNormolResult result = ApiManager.isfavoritelist(
									userid, token, holder.item.softid + "");
							if (result.isfavorited == 1) {
								Message msg = new Message();
								msg.getData().putSerializable("info", holder);
								msg.what = BaseActivity.UPDATE_FAVORITE;
								context.handler1.sendMessage(msg);
							} else {
								Message msg = new Message();
								msg.getData().putSerializable("info", holder);
								msg.what = BaseActivity.UPDATE_UNFAVORITE;
								context.handler1.sendMessage(msg);
							}
						}
					} catch (ApiException e) {
						if (Constants.DEBUG)
							Log.e("net error:", e.getMessage(), e);
						context.showException(e);
					}
				}
			}).start();
		}
	}

	/**
	 * �����ղذ�ť
	 */
	public void clickFavoriteImage(View v) {
 		v.requestFocus();
		ItemViewHolder holder = (ItemViewHolder) v.getTag();
		if (!AppContext.getInstance().isNetState) {
			context.showMessageInfo("������󣬲��ܽ��иò�����");
			return;
		}
		favorite(holder, true);
	}

	/**
	 * ����
	 * 
	 * @param info
	 * @param apkname
	 */
	private boolean download(SoftlistInfo info, String apkname,int state) {
		final SiteInfoBean downloadbean = new SiteInfoBean(info.downloadurl,
				ServiceUtils.getSDCARDImg(Constants.APK_DATA) == null ? ""
						: ServiceUtils.getSDCARDImg(Constants.APK_DATA)
								.getPath(), ServiceUtils.getApkname(info.downloadurl), info.softname,
				info.softicon, info.version, info.softid, info.appid,
				info.softsize, 0, 1, null, null,
				BaseActivity.downloadUpdateHandler,state==4?Constants.UPDATEPARAM:"");
		String[] msg = DownloadUtils.download(downloadbean, context);
		// �����Ѵ���
		Toast mScreenHint = Toast.makeText(context, msg[0],Toast.LENGTH_SHORT);
		mScreenHint.show();
		if (msg[1] != null && msg[1].equals("0"))
			return true;
		return false;
	}

	public class ItemViewHolder implements Serializable {
		public RatingBar starView;
		public AsynLoadImageView icon;
		public TextView titleTextView;
		public TextView contentTextView;
		public TextView processTextView;
		public TextView priceTextView;
		public RelativeLayout pricelayout;
		public RelativeLayout sizeAndPriceLayout;
        public RelativeLayout shareAndfavLayout;
		public LinearLayout actionLayout;
		public String imgurl;
		// 0--���أ�1--������ 2--��װ     3����ϣcode��--�Ѱ�װ 4 ����  5 ��ͣ
		public int state = 0;
		public int position;
		public SoftlistInfo item;

		public RelativeLayout contentLayout;
		public ImageView favoriteImageView;
		public ImageView shareImageView;
		public boolean isupload=false;
		public TextView shoufa;
		// public ImageView downloadImageView ;
	}

	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// ��ȡ��ԴͼƬ
		InputStream is = context.getResources().openRawResource(resId);
		try {
			return BitmapFactory.decodeStream(is, null, opt);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

 
}
