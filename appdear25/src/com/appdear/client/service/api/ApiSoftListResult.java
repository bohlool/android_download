/**
 * SoftListInfo.java
 * created at:2011-5-24����12:02:07
 *
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 * 
 * All right reserved
 */
package com.appdear.client.service.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.appdear.client.model.BackgroundInfo;
import com.appdear.client.model.CannelIDinfo;
import com.appdear.client.model.OrderlistInfo;
import com.appdear.client.model.PermissionListInfo;
import com.appdear.client.model.Recommentlist;
import com.appdear.client.model.SoftlistInfo;
import com.appdear.client.model.UpdatelistInfo;

/**
 * ����Ƶ����������б�����Ϣ
 * 
 * @author zqm
 */
public class ApiSoftListResult implements ApiResult, Serializable {

	/**
	 * ��������
	 */
	public String resultcode = "";
	
	/**
	 * ������Ϣ
	 */
	public String resultinfo = "";
	
	/**
	 * ƽ̨����汾��
	 */
	public String sv = "";
	
	/**
	 * �ֻ����ʶ����
	 */
	public String imei = "";
	
	/**
	 * ��ǰҳ��
	 */
	public int pageno = 0;
	
	/**
	 * ÿҳ��¼��
	 */
	public int pagenum = 0;
	
	/**
	 * ��¼����
	 */
	public int totalcount = 0;
	
	/**
	 * �ǳ�
	 */
	public String nickname = "";
	
	/**
	 * ����
	 */
	public String level = "";
	
	/**
	 * ����ID
	 */
	public String authorid = "0";
	
	/**
	 * ��������
	 */
	public String author = "";
	
	/**
	 * ��ϵ��ʽ
	 */
	public String email = "";
	
	/**
	 * ������ҳ��ַ
	 */
	public String homeurl = "";
	
	/**
	 * �����ַǰ׺1
	 */
	public String spreurl = "";
	
	/**
	 * �����ַǰ׺0
	 */
	public String dpreurl = "";
	
	/**
	 * wapurl
	 */
	public String wapurl = "";
	
	/**
	 * �Ƿ���Ҫ������Ƥ�г��ͻ���
	 * 0--�� 1--��
	 */
	public String isupdate = "";
	
	/**
	 * ��Ƥ�г�������ַ
	 */
	public String updateurl = "";
	
	/**
	 * ����Ƽ��б�����
	 */
	public int recommentcount = 0;
	
	/**
	 * ���ģ��汾��
	 */
	public int sectionversion = 0;
	
	/**
	 * �����Ƿ�ɹ�
	 */
	public int isok = 0;
	
	/**
	 * �����ϸ��Ϣ
	 */
	public SoftlistInfo detailinfo = new SoftlistInfo();
	
	/**
	 * ����б�
	 */
	public List<SoftlistInfo> softList = new ArrayList<SoftlistInfo>();

	/**
	 * �ؼ����б�
	 */
	public List<String> keywordList = new ArrayList<String>();
	
	/**
	 * �����б�
	 */
	public List<OrderlistInfo> orderList = new ArrayList<OrderlistInfo>();

	/**
	 * ������Ϣ
	 */
	public List<UpdatelistInfo> updatelist = new ArrayList<UpdatelistInfo>();
	
	/**
	 * Ȩ����Ϣ
	 */
	public List<PermissionListInfo> permissionlist = new ArrayList<PermissionListInfo>();
	
	/**
	 * �����б���Ϣ
	 */
	public List<SoftlistInfo> searchlist = new ArrayList<SoftlistInfo>();
	
	/**
	 * Ƶ��ID
	 */
	public List<CannelIDinfo> initlist = new ArrayList<CannelIDinfo>();

	/**
	 * �����ͼ��url
	 */
	public List<String> imgurl = new ArrayList<String>();
	
	/**
	 * �Ƽ�����б�
	 */
	public List<Recommentlist> recommentlist = new ArrayList<Recommentlist>();
	public List<String> autolist = new ArrayList<String>();
	
	/**
	 * ���ñ����б�
	 */
	public List<BackgroundInfo> backgroundlist = new ArrayList<BackgroundInfo>();

	/**
	 * ������Ϣ
	 */
	public String softSize;

	public String softUpdateTip;

	public String softVersion;
	
	/**
	 * ������·url
	 */
	public String linkurl;
	
	/**
	 * ������·���� 1���� 0�ر�
	 */
	public int linkflag;

	/**
	 * ר�⸱����
	 */
	public String catdesc = "";
	
	public String dynamics="";
	
	public String def=null;
	
	public long timpstamp;
	
	public String asids=null;
}
