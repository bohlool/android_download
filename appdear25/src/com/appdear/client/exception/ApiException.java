/**
 * ParamException.java
 * created at:2011-5-10����09:59:51
 * 
 * Copyright (c) 2011, ������Ƥ�Ƽ����޹�˾
 *
 * All right reserved
 */ 
package com.appdear.client.exception;
  
/** 
 * ҵ���쳣
 * 
 * @author zqm
 */
public class ApiException extends BaseException {
	
	/**
	 * @param code
	 */
	public ApiException(int code) {
		super(code);
	}
	
	public ApiException(int code, String msg) {
		super(code, msg);
	}
	
	public ApiException(int code, String msg, Throwable e) {
		super(code, msg, e);
	}

	@Override
	protected int getExsistCode(int code) {
		// TODO Auto-generated method stub
		if(ExceptionEnum.ApiExceptionCode.exsist(code)){
			return code;
		}else{
			return ExceptionEnum.ApiExceptionCode.API_OTHER_ERR.getValue();
		}
	}
}

 