/**
 * CommentInfo.java
 * created at:2011-5-25����03:22:43
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
public class CommentInfo implements Serializable {

	/**
	 * ����ID
	 */
	public int commentid;
	
	/**
	 * �������ǳ�
	 */
	public String username = "";
	
	/**
	 * ������ID
	 */
	public int userid;
	
	/**
	 * ����ʱ��
	 */
	public String time = "";
	
	/**
	 * ��������
	 */
	public String text = "";
	
	/**
	 * ������Ϣ
	 */
	public int grade;
}
