/**
 * BannerInfo.java
 * created at:2011-5-24����05:27:15
 *
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 * 
 * All right reserved
 */
package com.appdear.client.model;

import java.io.Serializable;

/**
 * Banner��Ϣ
 * 
 * @author zqm
 *
 */
public class BannerInfo implements Serializable {
	
	/**
	 * ����
	 */
	public String adtype = "";
	
	/**
	 * ���id
	 */
	public int adid;
	
	/**
	 * �������
	 */
	public String adtitle = "";
	
	/**
	 * ���ͼƬ��ַ
	 */
	public String adurl = "";
	
	/**
	 * ����ַ
	 */
	public String imgurl = "";
}
