package com.appdear.client.service.api;

import java.util.ArrayList;

import com.appdear.client.model.SharePageModel;
import com.appdear.client.model.SoftlistInfo;

public class ApiShareSoftResult {
	public  String    resultcode;
	public  String   imei;
	public  String  sv;
 
	/**
	 * �����û���ҳ��
	 */
	public String page;
	/**
	 * ҳ��
	 */
	public int pageno;
	/**
	 * ÿҳ��¼��
	 */
	public int pagenum ;
	/**
	 * ������
	 */
	public int totalcount;
	public ArrayList<SoftlistInfo> myshareList = new ArrayList<SoftlistInfo>();
	public String nickname;
	public int popcount;
	public String userid;
	public int type;
	public int gender = 0;
 
}
