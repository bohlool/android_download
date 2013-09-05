package com.appdear.client.commctrls;

import com.appdear.client.service.AppContext;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * ������Ϣ
 * @author jdan
 *
 */
public class Common {
	/**
	 * ������°�ƤӦ������
	 */
	public final static String UPDATE_APPDEAR_LOG = "update_appdear_log";
	
	//��Դ�б������洢
	public final static String LISTVIEWSOURCE_XML="listview";
	//��ǰ�û���¼��Ϣ
	public final static String USERLOGIN_XML="userlogin";
	//��ǰ163�û���¼��Ϣ
	public final static String USERLOGIN_163_XML="userloginweibo";
	public final static String SEARCH_XML = "searchxml";
	
	//�����û�����洢��Ϣ
	public final static String USERPASSWDXML="userpasswd";
	
	public final static String TYPES_XML="types";
	//����ģ������
	public final static String SECTIONCODEXML = "sectioncode";
	
	//������Ϣ
	public final static String SETTINGS = "settings";
	public final static String UPDATE_VERSION = "updateversion";
	
	//������ʾ��Ҫ���ݵ�list��Ϣ
	public final static String SHOWTYPE="showtype";
	
	//point�����б�
	public final static int  POINT=1;
	
	//������ʾ��Ҫ���ݵ�list��Ϣ
	public final static int  ORDER=2;
	
	//������ʾ��Ҫ���ݵ�list��Ϣ
	public final static int  MESSAGE=3;
	//�绰��ֵö��
	public static enum PHONEACCOUNT{
		PHONE10(10),PHONE20(20),PHONE30(30),PHONE50(50),
		PHONE100(100),PHONE200(200),PHONE300(300),PHONE500(500);
		private final int value;
		private PHONEACCOUNT(int value){
			this.value=value;
		}
		public int getValue(){
			return value;
		}
	}
	
	/*
	 * ��Ƥ��֧����ʽ
	 */
	public final static int APPAY=11;
	/*
	 * ���֧����ʽ
	 */
	public final static int ACCOUNTPAY=12;
	/*
	 * �ֻ�֧����ʽ
	 */
	public final static  int SMSPAY=13;
	/*
	 * ��ֵ��֧����ʽ
	 */
	public final static  int CARDPAY=14;
	/*
	 * ��ֵ��֧����ʽ
	 */
	public final static  int CARDPAYSECOND=15;
	/*
	 * ��ֵ�������״̬
	 */
	public final static  int CARDPAYSECOND_ACCOUNT_NOTENOUGHFLAG=16;
	
	/*
	 * ��ֵ���������״̬
	 */
	public final static  int CARDPAYSECOND_ACCOUNT_ENOUGHFLAG=7;

	/*
	 * ֧��״̬
	 */
	public final static String PAYFLAG="pay_flag";
	
	/*
	 * ֧��״̬
	 */
	public final static String PAYSOFT="pay_soft";
	/**
	 * �Ƿ��Ƴ�����
	 */
	public final static int OFFVIEW=-1;
	/**
	 * ��¼״̬
	 */
	public final static String USERLOGINFLAG="userloginflag";
	
	/**
	 * ��Ϸ������
	 */
	public final static int GAMESHENGDA=1;
	/**
	 * ��Ϸ������
	 */
	public final static int GAMEZHENGTU=2;
	/**
	 * ��Ϸ������
	 */
	public final static int GAMEWANGYI=3;
	/**
	 * ��Ϸ������
	 */
	public final static int GAMEJUNWANG=4;
	/**
	 * ��ǰͼƬ������
	 */
	public final static String CURRENTBG="currentpic";
	/**
	 * ���ñ�������״̬
	 */
	public final static int ENDCURRENTBG=20;
	/**
	 * �����淢�͹㲥
	 */
	public final static String BACKGROUNDBFLAG="bgflag";
	/**
	 * ���µ�ַ
	 */
	public final static String UPDATEURL="updateurl";
	
	/**
	 * ���µ�ַ״̬
	 */
	public final static int UPDATEURLFLAG=30;
	
//	public static String updateurl="";
	public static boolean isNeedUpdateClient = false;
	
	public static boolean autosearch=true;
	
	public static Drawable BGDRAWABLE;
	public static Bitmap bgbitmap;

	public static String DOWNLOAD_NOTIFY = "DOWNLOAD_NOTIFY";
	
	public static boolean ISLOADSOFTICON=SharedPreferencesControl.getInstance().getBoolean(
			"loadsofticon",com.appdear.client.commctrls.Common.SETTINGS, AppContext.getInstance().appContext);
	
    public static boolean LOADSNAPSHOT=SharedPreferencesControl.getInstance().getBoolean(
			"loadsnapshot",com.appdear.client.commctrls.Common.SETTINGS,AppContext.getInstance().appContext);	
    /*
	 * ���ؾ��ȼ��
	 */
	public final static int DOWNPROCESS=1;
	/*
	 * ���ؾ��ȼ��
	 */
	public final static int DOWNPROCESS1=2;
	/*
	 * ���ؾ��ȼ��
	 */
	public final static int DOWNPROCESS2=4;
	/*
	 * ���ؾ��ȼ��
	 */
	public final static int DOWNPROCESS3=6;
	
	/*
	 * ���ؾ��ȼ��
	 */
	public final static int DOWNPROCESS4=15;
}

