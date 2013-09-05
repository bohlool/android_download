package com.appdear.client.service.api;

import com.appdear.client.model.UserInfo;

/**
 * �û�ע��
 * @author zqm
 *
 */
public class ApiUserResult implements ApiResult {
	
	/**
	 * ��������
	 */
	public String resultcode = "";
	
	/**
	 * ������Ϣ
	 */
	public String resultinfo = "";
	
	/**
	 * imei
	 */
	public String imei = "";
	
	/**
	 * �汾��
	 */
	public String sv = "";
	
	/**
	 * �Ƿ�ɹ�
	 */
	public int isok = 0;
	
	/**
	 * �û�����
	 */
	public String token = "";
	
	/**
	 * �û�ID
	 */
	public String userid = "0";
	
	/**
	 * �û��ǳ�
	 */
	public String nickname = "";
	
	/**
	 * �˻�
	 */
	public int account = 0;
	
	/**
	 * �ܻ���
	 */
	public int totalpoint = 0;
	
	/**
	 * �ȼ�
	 */
	public String level = "";
	
	/**
	 * �û���Ϣ
	 */
	public UserInfo userinfo = new UserInfo();
}
