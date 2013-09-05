/**
 * RefreshListData.java
 * created at:2011-5-11����02:08:36
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client;
  
/** 
 * �첽ˢ������
 * 
 * @author zqm
 */
public interface RefreshDataListener {

	/**
	 * ����ˢ��״̬
	 * @param state
	 */
	public void refreshState(int state);
	
	/**
	 * ˢ������
	 */
	public void refreshData();
	
	/**
	 * ˢ�½��棨Ϊ����ʾͼƬ��
	 */
	public void refreshUI(int position) ;
	
	/**
	 * ˢ�½��棨Ϊ����ʾͼƬ��
	 */
	public void refreshUI(int first,int last) ;
	
	/**
	 * �������һҳ����ʾ
	 */
	public void showendalert();
}

 