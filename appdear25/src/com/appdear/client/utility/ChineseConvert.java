package com.appdear.client.utility;

import android.util.Log;

/**
 * ������ĵĺ���ƴ��
 * @author jindan
 *
 */
public class ChineseConvert {
	static final String include="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * 
	 * @param str      �ַ���
	 * @param isfirstC �Ƿ���ʾ���ַ�
	 * @return  ���غ���ƴ��
	 */
	public static  String ChineseToPing(String str,boolean isfirstC){
	
		if(str==null)return "";
		str=str.trim();
		int t0=str.length();
		String t2 = CnToSpell.getFirstSpell(str);
			if(t2==null||t2.equals("")){
				if(isfirstC==true){
					return "����";
				} 
			}else{
				if(isfirstC==true){
					t2=t2.toUpperCase();
					return String.valueOf(includeS(t2.charAt(0))?t2.charAt(0):"����").toUpperCase();
				}
			}
		return String.valueOf(t2.charAt(0)).toUpperCase();
	}
	
	private static boolean includeS(char a){
		char[] chars=include.toCharArray();
		for(char c:chars){
			if(a==c)return true;
		}
		return false;
	}
}
