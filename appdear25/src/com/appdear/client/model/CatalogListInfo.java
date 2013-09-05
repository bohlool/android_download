/**
 * CatalogListInfo.java
 * created at:2011-5-25����03:03:00
 *
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 * 
 * All right reserved
 */
package com.appdear.client.model;

import java.io.Serializable;

/**
 * <code>title</code>
 * abstract
 * <p>description
 * <p>example:
 * <blockquote><pre>
 * </blockquote></pre>
 * @author Author
 * @version Revision Date
 */
public class CatalogListInfo implements Serializable {

	/**
	 * ���ͼƬ��ַ
	 */
	public String catalogicon = "";
	
	/**
	 * ���ID
	 */
	public String  catalogid  ;
	
	/**
	 * �������
	 */
	public String catalogname = "";
	
	/**
	 * �������
	 */
	public String catalogdesc = "";
	
	/**
	 * ����ڵ���Դ����
	 */
	public String catalognum = "";

	/**
	 * Ĭ��ͼƬ��ԴID
	 * @return
	 */
	public int defaultIcon;
	
	/**
	 * Ӧ������
	 * @return
	 */
	public String appCount="10";
	
	/**
	 * ������ʱ��
	 * @return
	 */
	public String catalogTime;
	
	
	/**
	 * ��Ͳ���ص���������
	 * ��0 �ӱ�ǩ�����б� 1 ��ǩ�б�
	 */
	public String ctype = "";
}
