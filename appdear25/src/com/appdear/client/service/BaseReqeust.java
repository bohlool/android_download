/**
 * BaseReqeust.java
 * created at:2011-5-10����10:44:08
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client.service;

import org.xml.sax.SAXParseException;

import com.appdear.client.exception.ApiException;
import com.appdear.client.utility.StringHashMap;
  
/** 
 * Э�鴦��ӿ�
 * 
 * @author zqm 
 * @param <StringHashMap>
 */
public interface BaseReqeust<T> {

	/**
	 * ����ʽPOST
	 */
	public static final int POST = 0;
	
	/**
	 * ����ʽGET
	 */
	public static final int GET = 1;
	
	/**
	 * ��������
	 * @param url �ӿڵ�ַ
	 * @param requesttype ����ʽ
	 * @param params �������
	 * @return �����
	 * @throws ApiException�쳣��Ϣ
	 */
//	public T sendRequest(String url, int requesttype, StringHashMap params) throws ApiException;
	public T sendRequest(StringHashMap params) throws ApiException;
	
	/**
	 * ����Э��
	 * 
	 * @param xmlText Э�鷵�ص���Ϣ
	 * @return ������Ľ����
	 * @throws ApiException �쳣��Ϣ
	 * @throws SAXParseException 
	 */
	public T parserXml(String xmlText) throws ApiException;
}

 