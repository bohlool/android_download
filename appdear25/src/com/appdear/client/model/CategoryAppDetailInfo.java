
package com.appdear.client.model;

import java.io.Serializable;

/**
 * �����ϸ�б�ʵ����
 * @author zxy
 *
 */
public class CategoryAppDetailInfo implements Serializable {
	/**
	 * soft icon 
	 */
	public String softIconUrl = "";
	/**
	 * default soft icon 
	 */
	public int softdefaultImage;
	/**
	 * soft title 
	 */
	public String softTitle = "";
	/**
	 * ÿһ��item��ʾ��ͷ�����������
	 */
	public String softTip = "";
	/**
	 * soft tag
	 */
	public String sfotTag = "";
	/***
	 * soft star rank 
	 */
	public int rank;
	/**
	 * fee flag ,free  or fee  or other
	 */
	public String feeFlag = "";
	
}
