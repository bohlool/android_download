/**
 * NewSoftware.java
 * created at:2011-5-11����03:37:33
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client.model;

import java.io.Serializable;

import android.graphics.drawable.Drawable;
  
/** 
 * �����Ϣ
 * 
 * @author zqm 
 */
public class NewSoftwareInfo implements Serializable {
	
	/**
	 * ���id
	 */
	public String id = "";
	
	/**
	 * �������
	 */
	public String name = "";
	
	/**
	 * �Ǽ�
	 */
	public String star = "";
	
	/**
	 * ����
	 */
	public String author = "";
	
	/**
	 * ͼ��
	 */
	public Drawable icon;
	
}

 