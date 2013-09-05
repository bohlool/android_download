package com.appdear.client.model;

import java.io.Serializable;

/**
 * ������ϸ
 * @author zqm
 *
 */
public class OrderlistInfo implements Serializable {
	/**
	 * ����ID
	 */
	public int orderid;
	
	/**
	 * ��ˮ��
	 */
	public String tranid = "";
	
	/**
	 * ͼ���ַ
	 */
	public String icon = "";
	
	/**
	 * �������
	 */
	public String softname = "";
	
	/**
	 * �������
	 */
	public String desc = "";
	
	/**
	 * ����۸�
	 */
	public int price;
	
	/**
	 * �������
	 */
	public int grade;
	
	/**
	 * �ۿ�
	 */
	public int discount;
	
	/**
	 * ʵ��֧��
	 */
	public int pay;
	
	/**
	 * ֧������
	 */
	public int pytype;
}
