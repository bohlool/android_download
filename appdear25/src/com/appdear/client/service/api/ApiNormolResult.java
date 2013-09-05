package com.appdear.client.service.api;

import org.json.JSONObject;


public class ApiNormolResult implements ApiResult {

	/**
	 * ��������
	 */
	public String resultcode = "";
	
	/**
	 * ������Ϣ
	 */
	public String resultinfo = "";
	
	

	/**
	 * �Ƿ��ύ�ɹ�
	 */
	public int isok = 0;
	
	/**
	 * �Ƿ��ղ� 0--δ�ղ� 1--���ղ�
	 */
	public int isfavorited = 0;
	
	/**
	 * ��Ա�û���
	 */
	public String username = "";
	
	/**
	 * imei
	 */
	public String imei = "";
	
	/**
	 * imsi
	 */
	public String imsi = "";
	
	/**
	 * sv
	 */
	public String sv = "";
	
	/**
	 * contact ��ϵ����Ϣ
	 */
	public String contact = "";
	
	/**
	 * contact ��ϵ����Ϣ����
	 */
	public String contactcount = "";
	
	/**
	 * failidx  �ϴ���װ�б�appid�����ݹ��ܣ����ص�ʧ��Ӧ�õ�����
	 */
    public  String failidx="";
    
    @Override
	public String toString() {
		return "ApiNormolResult [resultcode=" + resultcode + ", resultinfo="
				+ resultinfo + ", isok=" + isok + ", isfavorited="
				+ isfavorited + ", username=" + username + ", imei=" + imei
				+ ", imsi=" + imsi + ", sv=" + sv + ", contact=" + contact
				+ ", contactcount=" + contactcount + ", failidx=" + failidx
				+ "]";
	}
	/**
	 * sms ��ϵ����Ϣ
	 */
	public String sms = "";
	
	/**
	 * �����ֻ�����
	 */
	public String buydate="";
}
