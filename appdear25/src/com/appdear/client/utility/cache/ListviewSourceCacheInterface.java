package com.appdear.client.utility.cache;

import java.util.List;

public interface ListviewSourceCacheInterface<T> {
	//��ӻ�����Ϣ��key�ļ�/sdcard/appdear/source/key
	public boolean addListview(String key,T source);
	
	//�ӻ���ȡkey�ļ���Դ
	public T getListview(String key);
	
}
