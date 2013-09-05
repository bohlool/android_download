package com.appdear.client.commctrls;

import java.util.ArrayList;
import java.util.List;

import com.appdear.client.R;
import com.appdear.client.commctrls.PagerContoler.MyOnPageChangeListener;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
 

public class PagerContolerDetail {
	public ViewPagerDetail mPager;//ҳ������
	public List<View> listViews; // Tabҳ���б�
	public ImageView cursor;// ����ͼƬ
	public FrameLayout t1, t2, t3;// ҳ��ͷ��
//	public int offset = 0;// ����ͼƬƫ����
//	public int initOffset=0;
	public int currIndex = 0;// ��ǰҳ�����
//	public int bmpW;// ����ͼƬ���
	Matrix matrix;
	public boolean isUpdata1;
	public boolean isUpdate2;
	public boolean isUpdate3;
	public PagerCallback callback;
	private  PagerAdapter pagerAdapter;
    boolean isFirstMove=true;
    
    TabbarNewDetail tab;
    int startPage;
    int screenW;
   
    //ͼƬ�ϱߵĸ߶�
    int ontouchheight;
    //ͼƬ����߶�
    int ontouchheighte;
    private TabCallBack tabCallback;
    
    public PagerContolerDetail(PagerCallback callback)
	{
		   this.callback=callback;
	}
//	public void initTextView(String[] args,Activity activity) {
// 		t1 = (TextView)  activity.findViewById(R.id.text1);
//		t2 = (TextView)  activity.findViewById(R.id.text2);
//		t3 = (TextView)  activity.findViewById(R.id.text3);
//        t1.setText(args[0]);
//        t2.setText(args[1]);
//        t3.setText(args[2]);
//      
//        switch (currIndex) {
//		case 0:
//			t1.setTextColor(Color.parseColor("#2da1e8"));
//			t2.setTextColor(Color.parseColor("#929191"));
//			t3.setTextColor(Color.parseColor("#929191"));
//			break;
//		case 1:
//			t2.setTextColor(Color.parseColor("#2da1e8"));
//			t1.setTextColor(Color.parseColor("#929191"));
//			t3.setTextColor(Color.parseColor("#929191"));
//			break;
//		case 2:
//			t3.setTextColor(Color.parseColor("#2da1e8"));
//			t1.setTextColor(Color.parseColor("#929191"));
//			t2.setTextColor(Color.parseColor("#929191"));
//			break;
//		default:
//			break;
//		}
//        
//	}
	/**
	 * ��ʼ��ViewPager
	 */
	public void initViewPager(TabbarNewDetail tab,View view1,View view2,View view3,boolean isUpdate1,boolean isUpdate2,boolean isUpdate3 ) {
		mPager = (ViewPagerDetail) tab.findViewById(R.id.vPagerDetail);
		mPager.setOnTouch(ontouchheight, ontouchheighte);
		mPager.setDetailview(view1);
		this.tab=tab;
		t1=(FrameLayout)tab.findViewById(R.id.tabbar_linearlayout_fistItemClick);
		t2=(FrameLayout)tab.findViewById(R.id.tabbar_linearlayout_secondItemClick);
		t3=(FrameLayout)tab.findViewById(R.id.tabbar_linearlayout_thirdItemClick);
		listViews = new ArrayList<View>();
		this.isUpdata1=isUpdate1;
        this.isUpdate2=isUpdate2;
        this.isUpdate3=isUpdate3;
        listViews.add(view1);
		listViews.add(view2);
		listViews.add(view3);
		
		mPager.setAdapter( getPagerAdapterInstance());		 
		mPager.setCurrentItem(currIndex);
		mPager.setOnPageChangeListener( getMyOnPageChangeListener());
 
		t1.setOnClickListener(getMyOnClickListenerInstance(mPager,0));
		t2.setOnClickListener(getMyOnClickListenerInstance(mPager,1));
		t3.setOnClickListener(getMyOnClickListenerInstance(mPager,2));
	}
	
	public void setTabCallback(TabCallBack tabCallback) {
		this.tabCallback = tabCallback;
	}
	

	/**
	 * ViewPager������
	 */
	public class MyPagerAdapter extends PagerAdapter {
	 
		 @Override   
	      public int getCount()    {    
 	          return listViews.size();    
	       }    
	      @Override   
	      public Object instantiateItem( View pager, int position )    { 
 	    	  View v1=listViews.get(position);
	    	  
	          ((ViewPagerDetail)pager).addView(v1);     
	           return v1;   
	       }   
	        @Override 
	       public void destroyItem( View pager, int position, Object view )    {
 	                ((ViewPagerDetail)pager).removeView((View)view );   
	       }     
	        @Override   
	       public boolean isViewFromObject( View view, Object object )    {  
 	               return view.equals( object );  
	       }     
	        @Override   
	       public void finishUpdate( View view ) {}   
	       @Override   
	       public void restoreState( Parcelable p, ClassLoader c ) {}   
	         @Override 
	       public Parcelable saveState() {       
 	                  return null;  
	       } 
	        @Override 
	      public void startUpdate( View view ) {
 	      }
			@Override
			public int getItemPosition(Object object) {
				// TODO Auto-generated method stub
				return POSITION_NONE;
			}
		 
	}
	  public  PagerAdapter getPagerAdapterInstance()
	    {
		  pagerAdapter= new MyPagerAdapter();
	    	return pagerAdapter;
	    }
//	/**
//	 * ͷ��������
//	 */
	private class MyOnClickListener implements View.OnClickListener {
		private int index = 0;
		private ViewPagerDetail mPager;//ҳ������
		MyOnClickListener(ViewPagerDetail pager,int i)
		{
			
			mPager=pager;
			index = i;
		}
		@Override
		public void onClick(View v) {
			tab.onClick(v.getId());
			mPager.setCurrentItem(index);
		}
	};
    public View.OnClickListener getMyOnClickListenerInstance(ViewPagerDetail pager,int i)
    {
    	return new MyOnClickListener(pager,i);
    }
    
	/**
	 * ҳ���л�����
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one ;// ҳ��1 -> ҳ��2 ƫ����
		int two ;// ҳ��1 -> ҳ��3 ƫ����

		MyOnPageChangeListener()
		{

		}
		@Override
		public void onPageSelected(int arg0) {
			currIndex = arg0;
			switch (arg0) {
			case 0:
				 
				 if(isUpdata1)
				 {
					View view1=	callback.viewFirst();
					listViews.remove(arg0);
					if(view1!=null)
					listViews.add(arg0, view1);
					pagerAdapter.notifyDataSetChanged();
					isUpdata1=false;
				 }
					 
				break;
			case 1:
				
				 if(isUpdate2)
				 {
					View view2=	callback.viewSecend();
					listViews.remove(arg0);
					if(view2!=null)
					listViews.add(arg0, view2);
					pagerAdapter.notifyDataSetChanged();
					isUpdate2=false;
				 }
				
				break;
			case 2:
				
				if(isUpdate3)
				 {
					View view3=	callback.viewThird();
					listViews.remove(arg0);
					if(view3!=null)
					listViews.add(arg0, view3);
					pagerAdapter.notifyDataSetChanged();
					isUpdate3=false;
				 }
				
				break;
			}
			
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			//Log.i("", "PagerContoler======onPageScrolled");
			
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		if(arg0==0){
		  if(tab!=null){
			if(currIndex==2){
					 tab.onClick(R.id.tabbar_linearlayout_thirdItemClick);
			}else if(currIndex==1){
					 tab.onClick(R.id.tabbar_linearlayout_secondItemClick);
			}else if(currIndex==0){
				 tab.onClick(R.id.tabbar_linearlayout_fistItemClick);
			}
		  }
		  mPager.setcurrent(currIndex);
		 }
		}
	}
	
	//OnPageChangeListener
	   public OnPageChangeListener getMyOnPageChangeListener()
	    {
		   return new MyOnPageChangeListener();
	    	//return new MyOnClickListener(pager,i);
	    }
	public void setOnTouch(int height,int heighte){
		ontouchheight=height;
		ontouchheighte=heighte;
	}
  /* public interface PagerCallback {
	    View viewFirst();
		View viewSecend();
		View viewThird();
	}*/
}
