/**
 * Constants.java
 * created at:2011-5-9����04:12:35
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client.service;

/** 
 * ϵͳ��Ϣ
 * 
 * @author zqm
 */
public interface Constants {

	/**
	 * andoridҵ����ID
	 */
	public static final String BID = "100003";
	
	/**
	 * header--��Ƥ�汾��Ϣ
	 */
	public static final String VERSION = "2.6";
	
	/**
	 * ��Ƥ��vesrioncode
	 */
	public static final int VERSIONCODE = 18;
	
	/**
	 * header--User agent
	 */
	public static final String USER_AGENT = "Aipi-apk";
	
	/**
	 * header--ƽ̨
	 */
	public static final String PLATFORM = "3";
	
	/**
	 * header--��Ȩ��
	 */
	public static final String AUTHID = "123456";
	
	/**
	 * header--�豸����ϵͳ
	 */
	public static final String OPERATION = android.os.Build.VERSION.RELEASE;
	
	/**
	 * header--�ֻ��ͺ�
	 */
	public static final String DEVICEID = android.os.Build.MODEL;
	
	/**
	 * DB�汾
	 */
	public static final int DB_VERSION = 21;
	
	/**
	 * DBname
	 */
	public static final String DB_FILENAME = "aipi.db";
	
	/**
	 * ������ݱ�����
	 */
	public static final String DB_TABLENAME_GALLERYAD = "galleryAd";
	
	/**
	 * ͼƬ����Ŀ¼(�ϼ�Ŀ¼/sdcard/appdear)
	 */
	public static final String CACHE_IMAGE_DIR = "/appdear/img";
	
	/**
	 * �б���Դ����Ŀ¼(�ϼ�Ŀ¼/sdcard/appdear)
	 */
	public static final String CACHE_SOURCE_DIR = "/appdear/source";
	
	/**
	 * APK�洢λ��
	 */
	public static final String APK_DATA = "/appdear/apk";
	
	/**
	 * �������ݱ����ļ���
	 */
	public static final String CACHE_DATA_DIR = "/appdear";
	
	/**
	 * �ֻ��ڴ�Ŀ¼
	 */
	public static final String DATA_APK = "/data/data/com.appdear.client/files";
	
	/**
	 * ͼƬ�������ݿ�����
	 */
	public static  final String CACHE_IMAGE_TABLE_NAME = "imageCache";
	
	/**
	 * ��ʱʱ��
	 */
	public static final int CONNECTION_TIME_OUT = 15000;
	
	/**
	 * API ����
	 */
	public static final String SINA_CONSUMER_KEY = "1570233751";
	
	/**
	 * API ��Կ
	 */
	public static String SINA_CONSUMER_SECRET = "09c09e350c54929d5e7ae17e729af074";
	
	/**
	 * androidҵ����--3//������ʱɾ��
	 */
	public static String BUSINESSLINE = "3";
	
	/**
	 * DEBUGģʽ
	 */
	public static boolean DEBUG = true;
	
	/**
	 * �Ƿ�������÷�������ַ
	 */
	public static boolean SETTING_HOST_DEBUG = false;
	
	/**
	 * �������ҳ�������url��WapЭ�飩
	 */
	/*public static final String SOFT_WAP_URL = AppContext.getInstance().wap_url
		+ "wap/wap/softinfo.jsp?softid=";*/
	public static final String SOFT_WAP_URL = "wap/s.jsp?sid=";
	
	/**
	 * ���ŷ��������ģ��
	 */
	//public static final String SMS_CONTENT_A = "���ڰ�ƤӦ�������﷢����һ���������";
	//public static final String SMS_CONTENT_B = "���Ƽ�������һ�°���Ӧ������ҳ������:";
	public static final String SMS_CONTENT_A = "�Ƽ����氮ƤӦ���������";
	
	/**
	 * ������
	 */
	public static  String CANNEL_CODE = Channel.CANNEL_CODE;
	
	/**
	 * ����ҳ�̶����� 
	 */
	public static final String SOFTPARAM = "sdetail";
	/**
	 * ����ҳ�̶����� 
	 */
	public static final String UPDATEPARAM = "supdate";
	
	public static final int LABELVERSIONFORCLIENT = 1;
	
	//public static final String[] dynamic={"a","b","c","d","e","f"};
	/**
	 * ��Ͳ-ְҵ��Ĭ�����ݣ�
	 */
	public static final String[][] PROFESSION_ID_NAME_ARRAY = {
		{"ѧ��", "100921"},     {"��ʦ", "100922"},
		{"�߼����� ", "100917"},  {"�г���չ", "100925"},
		{"Ӫ������ ", "100926"},  {"IT����",  "100923"}, {"����", "100929"},
		{"����ҵְԱ", "100918"}, {"������Ա", "100920"},
		{"���� ", "100931"},     {"����", "100933"},
		{"����", "100932"},      {"����", "100927"},   {"ũ��", "100928"},
		{"����", "100938"},      {"����", "100939"},   {"����", "100940"}, {"����", "100941"},
		{"��������", "100936"},   {"���ų���", "100942"},
		{"��Ա", "100934"},      {"��������", "100935"},
		{"����", "100944"},      {"����", "100945"},
		{"���ε���", "100930"},   {"�������", "100937"},
		{"���̼���", "100924"},   {"ҽ��", "100919"},    {"�������", "100943"}};
	
	/**
	 * ��Ͳ-ʹ�ó�����Ĭ�����ݣ�
	 */
	public static final String[][] USAGESCENARIO_ID_NAME_ARRAY = {
		{"ѧϰ", "100894"},        {"����", "100895"},
		{"�ȴ�ĳ�˻�ĳ�� ", "100898"}, {"���ֻ�", "100913"}, 
		{"�ϲ��� ", "100905"},       {"����",  "100900"},  {"����", "100901"},
		{"���鿴��", "100908"},      {"������", "100907"},
		{"���� ", "100896"},         {"����", "100899"},
		{"����Ӱ", "100906"},        {"�ڱ���", "100909"}, {"������", "100912"},
		{"����", "100897"},         {" �Է�", "100902"},  {"���", "100904"}, {"��", "100910"},
		{"������", "100903"},        {"��绰", "100911"},
		{"��ҽԺ", "100915"},        {"��Ӱ", "100914"},
		{"д�ĵ�", "100916"}};
	
	/**
	 * ��Ͳ-ʹ����Ⱥ��Ĭ�����ݣ�
	 * "ʱ�д���"��"ʱ��ǰ��"��id��"����һ��"��"����"��id
	 */
	public static final String[][] TARGETUSERS_ID_NAME_ARRAY = {
		{"ʱ�д���", "100103", "http://content.appdear.com/picture/icon_label/20120228/appdear_74367_label.png"}, 
		{"����һ��", "100104", "http://content.appdear.com/picture/icon_label/20120228/appdear_76523_label.png"}, 
		{"լ��լŮ ", "100089", "http://content.appdear.com/picture/icon_label/20120228/appdear_41973_label.png"},
		{"������ʿ", "100090", "http://content.appdear.com/picture/icon_label/20120228/appdear_75427_label.png"},
		
		{"���Ķ�", "100101", "http://content.appdear.com/picture/icon_label/20120228/appdear_94723_label.png"},  
		{"����Ӱ",  "100095", "http://content.appdear.com/picture/icon_label/20120228/appdear_79312_label.png"},  
		{"����Ӱ", "100096", "http://content.appdear.com/picture/icon_label/20120228/appdear_66018_label.png"}, 
		{"������", "100098", "http://content.appdear.com/picture/icon_label/20120228/appdear_31590_label.png"},
		
		{"��Уѧ�� ", "100091", "http://content.appdear.com/picture/icon_label/20120228/appdear_23427_label.png"}, 
		{"��Ϸ�� ", "100092", "http://content.appdear.com/picture/icon_label/20120228/appdear_12479_label.png"},  
		{"С��", "100094", "http://content.appdear.com/picture/icon_label/20120228/appdear_66342_label.png"},   
		{"����", "100093", "http://content.appdear.com/picture/icon_label/20120228/appdear_90068_label.png"}, 
		
		{"������", "100097", "http://content.appdear.com/picture/icon_label/20120228/appdear_94283_label.png"},  
		{"����", "100099", "http://content.appdear.com/picture/icon_label/20120228/appdear_53308_label.png"},    
		{"ѧ����", "100100", "http://content.appdear.com/picture/icon_label/20120228/appdear_99382_label.png"},  
		{"����", "100102", "http://content.appdear.com/picture/icon_label/20120228/appdear_98963_label.png"}};

	/**
	 * �������������Ϣ
	 */
	public static final String[] PROGRESS_MSG = {"������ͼ�꣬������ݲ˵�",
		"���һ����ɿ����л�����",
		"���������ʾ���Ժ���",
		"�����пɽ�Ӧ�����ֻ��ڴ��SD���������ƶ�",
		"��ǩ�пɸ���������Ⱥ��ʹ�ó�����ְҵ������Ӧ��",
		"��ҳ����б�̬�仯�������������ྫ�ʵ����",
		"ͨѶ¼���ݻ�ԭ��������ʹ���ֻ�����ȫ��������"
    };

	/**
		 * ��ҳ��������Ҳ��־��� 
		 */
	public static String[][] flagLog = {{"cid-","labelcatid-","modelarea-"},{"catalogid-","catalogid-","catalogid-"},{"search-1"},{"ad-1"},{"more-1"}};
	
	public static String[] PHONETAG={"�ֻ��ͺţ�","IMEI��","�豸���кţ�","����ʱ�䣺","�����ڣ�","���޷�Χ��","����ֻ�������Ϣ��","�ͷ��绰:"};
	
}

 