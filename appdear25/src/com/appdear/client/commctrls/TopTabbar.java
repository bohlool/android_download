package com.appdear.client.commctrls;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appdear.client.R;
import com.appdear.client.service.Constants;
/**
 * top tab control ,usage see user book .
 * @author zxy
 *
 */
public class TopTabbar extends LinearLayout implements OnClickListener {
	
	/**
	 * namespace
	 */
	private final String nameSpace = "http://meiyitianabc.blog.163.com";
	
	/**
	 * tab������ɫ����ͨ״̬��
	 */
	private static final int TEXT_NORMAL_COLOR = Color.parseColor("#8be4f7");
	
	/**
	 * tab������ɫ��������ɫ��
	 */
	private static final int TEXT_PRESSED_COLOR = Color.WHITE;
	
	//each tab item
	/**
	 * the first
	 */
	private RelativeLayout firstItem = null;
	
	/**
	 * the second
	 */
	private RelativeLayout secondItem = null;
	
	/**
	 * the third
	 */
	private RelativeLayout thirdItem = null;
	
	/**
	 * ��һ��tab���ı�
	 */
	private TextView firstItemText = null;
	
	/**
	 * �ڶ���tab���ı�
	 */
	private TextView secondItemText = null;
	
	/**
	 * ������tab���ı�
	 */
	private TextView thirdItemText = null;

	/**
	 * ��view����
	 */
	private LinearLayout contentContainer;
	
	/**
	 * ��view
	 */
	private View contentContainer1;
	private View contentContainer2;
	private View contentContainer3;
	
	/**
	 * click callback for  onClick .
	 */
	private TabbarCallback tabOnClick = null;
	
	/**
	 * ����
	 */
	TextView tv1;
	/**
	 * ����
	 */
	TextView tv2;
	/**
	 * ����
	 */
	TextView tv3;
	/**
	 * tab����
	 */
	private LinearLayout topContainer = null;
	
	public TopTabbar(Context context,AttributeSet attr) {
		super(context);
		
		this.setOrientation(LinearLayout.HORIZONTAL); 
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.tabbar, this);
		
		//����Tab�İ�������
		topContainer = (LinearLayout) view.findViewById(R.id.tabbar_layout_topContainer);
		
		//��һ��Tab�İ�������
		firstItem = (RelativeLayout) topContainer.findViewById(R.id.tabbar_linearlayout_fistItem);
		firstItemText = (TextView) topContainer.findViewById(R.id.tabbar_tv_title_1);
		firstItem.requestFocus();
		firstItem.setFocusable(true);
		
		firstItemText.requestFocus();
		firstItemText.setFocusable(true);
		FrameLayout firstItemClick = (FrameLayout) topContainer.findViewById(R.id.tabbar_linearlayout_fistItemClick);
		firstItemClick.setOnClickListener(this);

		//�ڶ���Tab�İ�������
		secondItem = (RelativeLayout)topContainer.findViewById(R.id.tabbar_linearlayout_secondItem);
		secondItemText = (TextView) topContainer.findViewById(R.id.tabbar_tv_title_2);
		FrameLayout secondItemClick = (FrameLayout)topContainer.findViewById(R.id.tabbar_linearlayout_secondItemClick);
		secondItemClick.setOnClickListener(this);
		
		//������Tab�İ�������
		thirdItem = (RelativeLayout)topContainer.findViewById(R.id.tabbar_linearlayout_thirdItem);
		thirdItemText = (TextView) topContainer.findViewById(R.id.tabbar_tv_title_3);
		FrameLayout thirdItemClick = (FrameLayout)topContainer.findViewById(R.id.tabbar_linearlayout_thirdItemClick);
		thirdItemClick.setOnClickListener(this);

		contentContainer = (LinearLayout)view.findViewById(R.id.tabbar_layout_ContentContainer);

		contentContainer1 = (LinearLayout) contentContainer.findViewById(R.id.tabbar_layout_ContentContainer_1);
		contentContainer2 = (LinearLayout) contentContainer.findViewById(R.id.tabbar_layout_ContentContainer_2);
		contentContainer3 = (LinearLayout) contentContainer.findViewById(R.id.tabbar_layout_ContentContainer_3);
		
		onClick(R.id.tabbar_linearlayout_fistItemClick);
		
		int id = attr.getAttributeIntValue(nameSpace, "id", 0x12345);
		if (id == 0x12345) {
			if (Constants.DEBUG)
				Log.i("TopTabbar", "sorry ,TopTabbar id is not set , you can access it by default id 0x12345");
			setId(0x12345);
		} else{
			setId(id);
		}
	}
	 
	@Override
	public void onClick(View v) {
		if (v == null)
			return;
		onClick(v.getId());
	}
	
	/**
	 * ������Ӧ
	 * @param id
	 */
	private void onClick(int id) {
		switch(id){
		case R.id.tabbar_linearlayout_fistItemClick:
			//set whitch tab view should light .
			firstItemText.setBackgroundResource(R.drawable.tab_bottom_white_line);
			secondItemText.setBackgroundResource(0);
			thirdItemText.setBackgroundResource(0);
			if(tv1!=null)
			//tv1.setShadowLayer(4, 2, 2, Color.WHITE);
			
			firstItemText.setTextColor(TEXT_PRESSED_COLOR);
			secondItemText.setTextColor(TEXT_NORMAL_COLOR);
			thirdItemText.setTextColor(TEXT_NORMAL_COLOR);
			
			contentContainer1.setVisibility(View.VISIBLE);
			contentContainer2.setVisibility(View.GONE);
			contentContainer3.setVisibility(View.GONE);
			
			//set callback .
			if(tabOnClick!=null) {
				tabOnClick.fistItemClickCallback();
			}
			break;
		case R.id.tabbar_linearlayout_secondItemClick:
			//set whitch tab view should light .
			firstItemText.setBackgroundResource(0);
			secondItemText.setBackgroundResource(R.drawable.tab_bottom_white_line);
			thirdItemText.setBackgroundResource(0);
			
			contentContainer1.setVisibility(View.GONE);
			contentContainer2.setVisibility(View.VISIBLE);
			contentContainer3.setVisibility(View.GONE);
			
			firstItemText.setTextColor(TEXT_NORMAL_COLOR);
			secondItemText.setTextColor(TEXT_PRESSED_COLOR);
			thirdItemText.setTextColor(TEXT_NORMAL_COLOR);
			//set callback .
			if (tabOnClick != null) {
				tabOnClick.secondItemClickCallback();
			}
			break;  
		case R.id.tabbar_linearlayout_thirdItemClick:
			//set whitch tab view should light .
			firstItemText.setBackgroundResource(0);
			secondItemText.setBackgroundResource(0);
			thirdItemText.setBackgroundResource(R.drawable.tab_bottom_white_line);
			
			//set whitch content view should visibile .
			contentContainer1.setVisibility(View.GONE);
			contentContainer2.setVisibility(View.GONE);
			contentContainer3.setVisibility(View.VISIBLE);
			
			firstItemText.setTextColor(TEXT_NORMAL_COLOR);
			secondItemText.setTextColor(TEXT_NORMAL_COLOR);
			thirdItemText.setTextColor(TEXT_PRESSED_COLOR);
			//set callback .
			if (tabOnClick != null) {
				tabOnClick.thirdItemClickCallback();
			}
			break;
		}
	}
	
	/**
	 * ���ý���
	 * @param id
	 */
	public void setFocus(int id) {
		onClick(id);
	}

	/**
	 * ����Tab�ı���topbar_font_color
	 */
	public void setTitle(String[] title) {
		
		tv1 = ((TextView) findViewById(R.id.tabbar_tv_title_1));
		tv1.setText(title[0]); 
		//tv1.setShadowLayer(1, 2, 2, Color.WHITE);
		tv1.setTextColor(TEXT_PRESSED_COLOR);
		
		//tv1.setTextColor(R.color.topbar_font_color);
	
		tv2 = ((TextView) findViewById(R.id.tabbar_tv_title_2));
		tv2.setText(title[1]);
		//tv2.setShadowLayer(1, 2, 2, Color.WHITE);
		tv2.setTextColor(TEXT_NORMAL_COLOR);
		//tv2.setTextColor(R.color.topbar_font_color);  
		
	
		tv3 = ((TextView) findViewById(R.id.tabbar_tv_title_3));
		tv3.setText(title[2]);  
		//tv3.setShadowLayer(1, 2, 2, Color.WHITE);
		tv3.setTextColor(TEXT_NORMAL_COLOR);
	
		//tv3.setTextColor(R.color.topbar_font_color);
		//Ч��
	}
	
	public LinearLayout getContentContainer() {
		return contentContainer;
	}
	
	public LinearLayout getTopContainer() {
		return topContainer;
	}
	
	/**
	 * ���ü���
	 * @param tabOnClick
	 */
	public void setTabOnClick(TabbarCallback tabOnClick) {
		this.tabOnClick = tabOnClick;
	}
	
}