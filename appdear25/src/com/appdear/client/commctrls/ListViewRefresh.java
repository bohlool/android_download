/**
 * ListRefresh.java
 * created at:2011-5-11����01:46:24
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client.commctrls;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.appdear.client.R;
import com.appdear.client.RefreshDataListener;
import com.appdear.client.download.FileDownloaderService;
import com.appdear.client.service.AppContext;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
  
/** 
 * ��̬ˢ���б�
 * 
 * @author zqm 
 */
public class ListViewRefresh extends ListView {
	private int index = -1;
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * ˢ��tag
	 */
	private boolean refreshTag = true;
	
	/**
	 * ����tag
	 */
	private boolean endTag = false;
	
	/**
	 * ������
	 */
	private boolean errTag = false;
	
	/**
	 * �״�ˢ��tag
	 */
	private boolean firstTag = true;
	
	/**
	 * ����ˢ��
	 */
	private RefreshDataListener refreshDataListener;
	
	private TextView loaddingAlert;
	
	private ExecutorService pool = Executors.newCachedThreadPool();
	/**
	 * �̳߳�
	 */
//	AbstractExecutorService pool=new ThreadPoolExecutor(5,10,15L,TimeUnit.SECONDS,new SynchronousQueue(),new ThreadPoolExecutor.DiscardOldestPolicy());
	
	private int countflag;
	
	/**
	 * ��¼��ǰ�ɼ�item��position
	 */
	private int currentpostion;
	
	/**
	 * ��¼�ɼ�item
	 */
	private int visibleItemc;
	
	/**
	 * ��¼�б�����
	 */
	private int totalItemc;
	private int state = -1;

	private Context context;

	public int getCurrentpostion() {
		return currentpostion;
	}

	public void setCurrentpostion(int currentpostion) {
		this.currentpostion = currentpostion;
	}

	private boolean isShowAlert = true;
	
	/**
	 * ��ʼ��
	 */
	private void init() {
		setOnScrollListener(new OnScrollListener() {
			/*scrollState������״̬��
			��SCROLL_STATE_FLING ���� 2 �û�֮ǰͨ�����ع�����ִ���˿��ٹ�������������������ֹͣ�㡣
			(SCROLL_STATE_TOUCH_SCROLL ), 0  �û�ͨ�����ع�����������ָû���뿪��Ļ��
			 ��SCROLL_STATE_IDLE ����1  ��ͼû�й�����ע�⣬ʹ�ù켣�����ʱ���ڹ���ֹ֮ͣǰ��һֱ���ڿ���״̬�� */
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			//	System.out.println("----onScrollStateChanged----");
				countflag=scrollState;
				
				//����ȡ����ʧ��ʱ�������б��ǵײ�ʱ����err״̬Ϊfalse��
				//��ʾ�ٴλ������б�ײ�ʱ��������
				if (countflag != OnScrollListener.SCROLL_STATE_IDLE 
						&& currentpostion + visibleItemc != totalItemc) {
					errTag = false;
				}
				
				//�б���ֹͣ״̬ʱ
				if (state != -1 && countflag == OnScrollListener.SCROLL_STATE_IDLE 
						&& refreshDataListener != null) {
					/*if (AppContext.getInstance().taskList.size() == 0)
						AppContext.getInstance().downloader.readDB();*/
					
					//refreshDataListener.refreshUI(currentpostion);
					int firstp=ListViewRefresh.this.getFirstVisiblePosition();
					int lastp=ListViewRefresh.this.getLastVisiblePosition();
					refreshDataListener.refreshUI(firstp,lastp);
					/*if(ListViewRefresh.this.getHeaderViewsCount()==0||firstp==0)
					{
						refreshDataListener.refreshUI(firstp,lastp);
					}else
					{
						refreshDataListener.refreshUI(firstp-1,lastp-1);
					}*/

//					int first=ListViewRefresh.this.getFirstVisiblePosition();
//					int last=ListViewRefresh.this.getLastVisiblePosition();
//					refreshDataListener.refreshUI(first>0?first-1:first,last<ListViewRefresh.this.getCount()-1?last+1:last);
				}
				state = scrollState;
			}
			
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				currentpostion=firstVisibleItem;
				visibleItemc = visibleItemCount;
				totalItemc = totalItemCount;
				
				if (firstTag) {
					firstTag = false;
					return;
				}
				//�����ǰҳ���Ѿ���������Ҳ���ҳβʱ����ʾ����Ϊ������ʾ
				if (endTag && firstVisibleItem + visibleItemCount != totalItemCount) {
					isShowAlert = true;
					return;
				}
				//�����ǰҳ����������ҵ�ҳβʱ����ʾ��ʾ����ʾ��ʾ��Ϻ���Ϊ��������ʾ��������ʾ��ʾ�ظ�
				if (endTag && firstVisibleItem + visibleItemCount == totalItemCount) {
					if (isShowAlert) {
						if (refreshDataListener != null) {
							Toast.makeText(context, "�Ѿ������һҳ", Toast.LENGTH_SHORT);
							isShowAlert = false;
						}
					}
					return;
				}
				
				if (refreshTag && !endTag && !errTag) {
					if (firstVisibleItem + visibleItemCount == totalItemCount 
							&& totalItemCount != 0) {
						refreshTag = false;
						if (refreshDataListener == null)
							return;
						refreshDataListener.refreshState(View.VISIBLE);
					pool.execute(new Runnable(){
						//new Thread(){
							@Override
							public void run() {
								if (refreshDataListener == null){
									return;
								}
								refreshDataListener.refreshData();
								handler.sendEmptyMessage(1);
							}
						//}.start();
					});
				   }
				}
			}
		});
	}
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (refreshDataListener == null)
					return;
				refreshDataListener.refreshState(View.INVISIBLE);
//				removeFooterView(loaddingAlert);
				refreshTag = true;
				break;
			}
		}
	};
	
	/**
	 * @param refreshDataListener the refreshDataListener to set
	 */
	public void setRefreshDataListener(RefreshDataListener refreshDataListener) {
		this.refreshDataListener = refreshDataListener;
		if (refreshDataListener == null)
			return;
		init();
	}
	
	/**
	 * @param context
	 */
	public ListViewRefresh(Context context, AttributeSet set) {
		super(context, set);
		this.context = context;
//		addLoadingView("���ڼ���...");
//		addHeaderView(loaddingAlert);
	}
	
	/**
	 * 
	 * @param context
	 */
	public ListViewRefresh(Context context) {
		super(context);
		this.context = context;
//		addLoadingView("���ڼ���...");
//		addHeaderView(loaddingAlert);
	}
	
	/**
	 * 
	 * @param context
	 * @param set
	 * @param arg1
	 */
	public ListViewRefresh(Context context, AttributeSet set, int arg1) {
		super(context, set, arg1);
		this.context = context;
//		addLoadingView("���ڼ���...");
//		addHeaderView(loaddingAlert);
	}
	
	/**
	 * @param refreshTag the refreshTag to set
	 */
	public void setRefreshTag(boolean refreshTag) {
		this.refreshTag = refreshTag;
	}
	
	public void removeHeaderview() {
//		removeHeaderView(loaddingAlert);
	}

	/**
	 * @param endTag the endTag to set
	 */
	public void setEndTag(boolean endTag) {
		this.endTag = endTag;
	}
	
	public void addLoadingView(String text) {
//		if (loaddingAlert == null)  {
//			loaddingAlert = new TextView(AppContext.getInstance().appContext);
//			loaddingAlert.setBackgroundResource(R.drawable.soft_list_item_unselected_bg);
//		}
//		loaddingAlert.setText(text);
	}

	public int getCountflag() {
		return countflag;
	}

	public void setCountflag(int countflag) {
		this.countflag = countflag;
	}

	public void setErrTag(boolean errTag) {
		this.errTag = errTag;
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(FileDownloaderService.delete_all==true)return false;
		View view=null;
        switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:

        case MotionEvent.ACTION_MOVE:
         	if (this != null&& index!=-1 && this.findViewWithTag("index"+index)!=null) 
			{
        		
				RelativeLayout   layout=(RelativeLayout )this.findViewWithTag("index"+index);
				layout.setVisibility(View.GONE);
				//((LinearLayout)layout.findViewById(R.id.actionLayout)).setVisibility(View.GONE);
				layout.setTag(null);
				index = -1;
			}
            break;
        default:
           break;
        }
        return  super.onTouchEvent(ev);
    }
}

 