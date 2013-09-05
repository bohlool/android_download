package com.appdear.client.service.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.appdear.client.model.SoftlistInfo;

public class ApiInstalledRecoverSoftListResult implements ApiResult, Serializable{
 
	
 
/**
 * SoftListInfo.java
 * created at:2011-5-24����12:02:07
 *
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 * 
 * All right reserved
 */
//	sv":"1.0",
//	"imei":"00000000000",


	/**
	 * ��������
	 */
	public String resultcode = "";
 
	
	/**
	 * ƽ̨����汾��
	 */
	public String sv = "";
	
	/**
	 * �ֻ����ʶ����
	 */
	public String imei = "";

	 
	/**
	 * �����Ƿ�ɹ�
	 */
	public int isok = 0;
	
	 
	/**
	 * ����б�
	 */
	public List<SoftlistInfo> softList = new ArrayList<SoftlistInfo>();

	 
}
