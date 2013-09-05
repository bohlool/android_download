/**
 * CatalogsoftlistInfo.java
 * created at:2011-5-25����03:13:58
 *
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 * 
 * All right reserved
 */
package com.appdear.client.model;

import java.io.File;
import java.io.Serializable;

import com.appdear.client.service.Constants;
import com.appdear.client.utility.ServiceUtils;

import android.graphics.drawable.Drawable;

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
public class SoftlistInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 112312L;
	
	/**
	 * �Ƿ�ѡ��d
	 */
	public boolean isCheck = false;
	
	/**
	 * ��׿���Ӧ��id
	 */
	public String appid = "";
	
	/**
	 * ��Ƥ�г����id
	 */
	public int softid = 0;
	
	/**
	 * ����id
	 */
	public String shareid = "";
	
	/**
	 * �������ͼ��
	 */
	public Drawable icon;
	
	/**
	 * ���ͼ��
	 */
	public String softicon = "";
	
//	 ���ͼ�걾��λ�� 
	public String softiconPath = "";
	
	
	/**
	 * �������
	 */
	public String softname = "";

	/**
	 * �������
	 */
	public String softdesc = "";

	/**
	 * ����۸�
	 */
	public int softprice = 0;
	
	/**
	 * ������ѻ���
	 */
	public int softpoints = 0;

	/**
	 * �������
	 */
	public int softgrade = 0;
	
	/**
	 * ���۵�IMEI
	 */
	public String commentimei = "";
	
	/**
	 * vesrioncode �汾����
	 */
	public int versioncode = 0;
	
	/**
	 * downloadurl
	 */
	public String downloadurl = "";
	
	/**
	 * ���ش���
	 */
	public int download = 0;
	
	/**
	 * ���۴���
	 */
	public int comment = 0;
	
	/**
	 * �����С
	 */
	public int softsize = 0;
	
	/**
	 * ������԰汾
	 */
	public String language = "";
	
	/**
	 * �����������
	 */
	public String publishtime = "";
	
	/**
	 * ����汾   �汾����
	 */
	public String version = "";
	
	/**
	 * �����Ҫ����
	 */
	public String summary = "";
	
	/**
	 * �����ϸ����
	 */
	public String detail = "";
	
	/**
	 * ����
	 */
	public int point = 0;
	
	/**
	 * ʱ��
	 */
	public String time = "";
	
	/**
	 * ����
	 */
	public int type = 0;
	
	/**
	 * ������Ϣ
	 */
	public String pointinfo = "";
	
	/**
	 * ����ID
	 */
	public String commentid = "0";
	
	/**
	 * �������ǳ�
	 */
	public String username = "";
	
	/**
	 * ������ID
	 */
	public String userid = "0";
	
	/**
	 * ��������
	 */
	public String text = "";
	
	/**
	 * ����
	 */
	public String adtype = "";
	
	/**
	 * ���id
	 */
	public int adid = 0;
	
	/**
	 * �������
	 */
	public String adtitle = "";
	
	/**
	 * ���ͼƬ��ַ
	 */
	public String adurl = "";
	
	/**
	 * ����ַ
	 */
	public String imgurl = "";
	
	/**
	 * ���ͼƬ��ַ
	 */
	public String catalogicon = "";
	
	/**
	 * ���ID
	 */
	public String catalogid = "";
	
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
	public int defaultIcon = 0;
	
	/**
	 * �������ķ���id
	 */
	public int catid = 0;

	
	public String isfirst="";
	
	public String isexclusive="";
	
	public String istop="";
	/**
	 * �Ա�
	 */
	public String  gender = "1";
	
	public String getSofticonPath() 
	{
		if(softiconPath.equals(""))
		{
			File fileDir = ServiceUtils.getSDCARDImg(Constants.CACHE_IMAGE_DIR);

			String f[] =  softicon.replace("http://", "")
					.split("/");
			  softiconPath = f[f.length - 1];
			  
		} 
		return softiconPath;
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof SoftlistInfo){
			SoftlistInfo soft=(SoftlistInfo)o;
			return soft.softid==this.softid; 
		}else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.softid;
	}
	
	
}
