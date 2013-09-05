package com.appdear.client.download;

/**
 * ���ؼ���
 * @author zqm
 *
 */
public interface DownloadListener {
    
	/**
     * ���ؽ���������
     * @param index
     * @param threadID
     * @param downloadsize
     * @param filesize
     */
	public void updateProcess(Object object);
	
	/**
	 * �쳣
	 * @param e
	 * @param msg
	 */
	public void updateProcess(Exception e, String msg, Object object);
	
	/**
	 * �������
	 */
	public void updateFinish(Object object);
}
