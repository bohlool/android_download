/**
 * BaseException.java
 * created at:2011-5-9 15:34:25
 * 
 * copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 * 
 * All right reserved
 * 
 */
package com.appdear.client.exception;

/**
 * �쳣����
 * 
 * @author zqm
 */
public abstract class BaseException extends Exception {

	/**
	 * �쳣���
	 */
	private int code;
	
	/**
	 * �쳣
	 * @param code �쳣���
	 */
	public BaseException(int code) {
		super();
		this.code = getExsistCode(code);
	}

	/**
	 * �쳣
	 * @param code �쳣���
	 * @param msg �쳣��Ϣ
	 */
	public BaseException(int code, String msg) {
		super(msg);
		this.code = getExsistCode(code);
	}

	/**
	 * �쳣
	 * @param code �쳣���
	 * @param msg �쳣��Ϣ
	 * @param throwable �쳣����
	 */
	public BaseException(int code, String msg, Throwable throwable) {
		super(msg, throwable);
		this.code =  getExsistCode(code);
	}

	/**
	 * �쳣
	 * @param code �쳣���
	 * @param throwable �쳣����
	 */
	public BaseException(int code, Throwable throwable) {
		super(throwable);
		this.code = getExsistCode(code);
	}
	
	/**
	 * ��ȡ�쳣���
	 * @return �쳣���
	 */
	public int getCode() {
		return code;
	}

	/**
	 * �����쳣���
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * ����������쳣���뷵��Ĭ���쳣��
	 * @param code
	 * @return
	 */
	protected  abstract int getExsistCode(int code);
}
