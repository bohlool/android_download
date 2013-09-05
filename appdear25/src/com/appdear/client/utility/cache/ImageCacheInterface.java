package com.appdear.client.utility.cache;

import java.security.Timestamp;
import java.util.LinkedHashSet;


public interface ImageCacheInterface {
	/**
	 * ������л���
	 */
	public void clearAll();
	
	/**
	 * ���key�Ļ�����Ϣ
	 * @param key
	 */
	public void clear(String key);
	/**
	 * ���뻺��
	 * @param key
	 * @param obj
	 */
	public void put(String key,LinkedHashSet<String>  obj);
	
	/**
	 * �õ�����ֵ
	 * @param key
	 * @param obj
	 */
	public LinkedHashSet<String> get(String key);
}	
