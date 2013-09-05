package com.appdear.client.model;

import java.io.Serializable;

import com.appdear.client.service.Constants;

import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * �Ѱ�װ�б���Ϣ
 * 
 * @author zqm
 *
 */
public class PackageinstalledInfo implements Serializable,Comparable {
	/**
	 * label
	 */
	public String appname = "";
	/**
	 * ���ַ�
	 */
	public String firstC = "Z";
	/**
	 * package name
	 */
	public String pname = "";
	
	/**
	 * ���°汾versionName
	 */
	public String updateVesrionName = "";
	
	/**
	 * version name
	 */
	public String versionName = "";
	
	/**
	 * version code
	 */
	public int versionCode = 0;
	
	/**
	 * icon
	 */
	public Drawable icon;
	
	/**
	 * is update
	 */
	public String isupdate = "";
	
	/**
	 * ���µ�ַ
	 */
	public String downloadUrl = "";
	
	/**
	 * ��Ƥ�����ID
	 */
	public int softID;
	
	/**
	 * �����С
	 */
	public int softsize = 0;
	
	/**
	 * �����С
	 */
	public String  formatsofttsize = "";
	/**
	 * ���״̬�Ƿ���sd
	 */
	public int  softsd = 0;
	
	/**
	 * �����ѡ��״̬
	 */
	
	public boolean isCheck = false;
	
	/**
	 * Ӧ�ÿ��ƶ�״̬
	 * auto = 0; Ĭ�ϰ�װ���ڴ棬���ƶ�
	 * internalOnly = 1; Ĭ��ֵ��ֻ�ܱ���װ���ڴ���
	 * preferExternal = 2; Ĭ�ϰ�װ���洢��
	 * Ϊ-1ʱ�����ڴ����Ե�ͬ��1
	 */
	public int installlocation = -1;
	
	/**
	 * ǩ��
	 */
	public Signature[] signatures;
	
	public String updatedesc;
	public int udlinenum;
	/**
	 * �Ƿ�ֻ�����ַ�
	 */
	public boolean isCharProxy=false;
	public void prettyPrint() {   
		if (Constants.DEBUG)
			Log.i("installedInfo", appname + "\t" + pname + "\t" + versionName + "\t" + versionCode + "\t" + "�Ƿ���ƶ�=" + installlocation +"\t");   
	}
	@Override
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		if(another!=null){
			PackageinstalledInfo pak=(PackageinstalledInfo)another;
			if(pak.firstC.charAt(0)>this.firstC.charAt(0)){
				return -1;
			}else if(pak.firstC.charAt(0)<this.firstC.charAt(0)){
				return 1;
			}else{
				return 0;
			}
		}
		return 0;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return pname+"="+softID+"="+appname;
	}
	public int status=0;
	
}
