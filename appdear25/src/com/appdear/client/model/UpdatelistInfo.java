package com.appdear.client.model;

import java.io.Serializable;

/**
 * �����б���Ϣ
 * @author zqm
 *
 */
public class UpdatelistInfo implements Serializable {

	/**
	 * Ӧ��ID
	 */
	public String appid = "";
	
	/**
	 * ���ID
	 */
	public int softid;
	
	/**
	 * softname
	 */
	public String softname = "";
	
	/**
	 * ���°汾��-versionName
	 */
	public String versionname = "";
	
	/**
	 * ������ص�ַ
	 */
	public String downloadurl = "";
	
	/**
	 * ��װ�ļ���С
	 */
	public int softsize = 0;
	
	/**
	 * ��װ�ļ���С
	 */
	public String updatedesc ="";
	/**
	 * ��������
	 */
    public int udlinenum;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return appid;
	}
	
	
	
}
