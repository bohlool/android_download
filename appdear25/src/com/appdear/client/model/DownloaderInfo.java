package com.appdear.client.model;

import java.io.Serializable;

public class DownloaderInfo implements Serializable  {

	/**
	 * ��Ƥ�г����ID
	 */
	public String softid = "";
	
	/**
	 * ���ͼ��
	 */
	public String softIcon = "";
	
	/**
	 * ������ص�ַ
	 */
	public String softUrl = "";
	
	/**
	 * ������ؽ���
	 */
	public int progresssize;
	
	/**
	 * �汾ID
	 */
	public int versionid;
	
	/**
	 * �����С
	 */
	public int softSize;
	
	/**
	 * �������
	 */
	public int softName;
	
}
