package com.appdear.client.download;

/**
 * list�б����ݰ�������
 * 
 * @author zqm
 *
 */
public interface ListAdatperDataProcessListener {

	/**
	 * �����¼�����
	 * @param object
	 * @param processTye
	 */
	public void keyPressProcess(Object object, int processTye);
	public void keyPressProcess(Object object, int processTye, int position);
}
