/**
 * TheSpecialListAdatper.java
 * created at:2011-5-26����03:32:23
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client.Adapter;

import java.util.List;

import com.appdear.client.model.SoftlistInfo;
import com.appdear.client.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
  
/**
 * ������Ϣadapter
 * 
 * <code>title</code>
 * abstract
 * <p>description
 * <p>example:
 * <blockquote><pre>
 * </blockquote></pre>
 * @author Author
 * @version Revision Date
 */
public class PointlistAdatper extends BaseAdapter {

	private Context context;
	private List<SoftlistInfo> list;
	private LayoutInflater mInflater;
	/**
	 * ��ʼ������
	 */
	public PointlistAdatper(Context context, List<SoftlistInfo> list) {
		this.context = context;
		this.list = list;
		mInflater=LayoutInflater.from(context);
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return list.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (position < list.size())
			return list.get(position);
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		if (position < list.size())
			return position;
		return -1;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SoftlistInfo item = list.get(position);
		View view;
		if (convertView != null) {
			view = convertView;
		} else {
			view = mInflater.inflate(R.layout.pointlist_item, parent, false);
		}
		ItemViewHolder holder = (ItemViewHolder) view.getTag();
		if (holder == null) {
			holder = new ItemViewHolder();
//			holder.time = (TextView) view.findViewById(R.id.point_time);
			holder.info = (TextView) view.findViewById(R.id.point_info); 
			holder.image = (ImageView) view.findViewById(R.id.point_image);
			view.setTag(holder);
		}
		if (item != null) {
//			holder.time.setText(item.time);
			holder.info.setText(item.pointinfo);
			view.setTag(holder);
		}
		view.setEnabled(false);
		return view;
	}


	protected class ItemViewHolder {
		public ImageView image;
//		public TextView time;
		public TextView info;
	}
}

 