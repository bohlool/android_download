package com.appdear.client.update;

import java.io.File;
/**
 * ��������֪ͨ�ӿ�
 * @author jindan
 *
 */
public interface FileDownloadHandlerI {
	/**
	 * 
	 * @param count �����ֽ���
	 * @param total  ���ֽ���
	 */
	public abstract void HandlerNocatifycation(long count,long total);
	/**
	 * ����ʧ�ܴ���
	 */
	public abstract void HandlerNocatifycationFail();
}
