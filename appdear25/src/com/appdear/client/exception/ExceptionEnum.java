package com.appdear.client.exception;
/**
 * �쳣ö�ٿ���չ
 * @author jdan
 *
 */
public class ExceptionEnum{
	/**
	 * ��Ƥ�г������Ȩδͨ��100000
	 * ���������ʽ����200000
	 * �����ڲ�����300000
	 * ���糬ʱ400000
	 * �û�Token�Ѿ�ʧЧ500000
	 * δ֪����600000
	 * ���粻����700000
	 */
	public static enum ApiExceptionCode{
		API_NOT_VERIFIED(100000),API_PARAMETERS_ERR(200000),API_SERVER_ERR(300000),API_NETWORK_TIMEOUT(400000),
		API_TOKEN_FAILURE(500000),API_OTHER_ERR(600000),API_NETWORK_NOAVALIBLE(700000);
		private final int value;
		private ApiExceptionCode(int value){
			this.value=value;
		}
		public int getValue(){
			return value;
		}
		public static boolean exsist(int code){
			if(code==API_NOT_VERIFIED.getValue()
				||code==API_PARAMETERS_ERR.getValue()
				||code==API_SERVER_ERR.getValue()
				||code==API_NETWORK_TIMEOUT.getValue()
				||code==API_TOKEN_FAILURE.getValue()
				||code==API_OTHER_ERR.getValue()
				||code==API_NETWORK_NOAVALIBLE.getValue()){
				return true;
			}else{
				return false;
			}	
		}
	}
	/**
	 * ����-1
	 * ��������ʧ�ܣ�Ŀ���ַ���ܱ���-2
	 * ��Ч����Ӧ�� -3
	 */
	public static enum ServerExceptionCode{
		SERVER_OTHER_ERR(-1),SERVER_EXCEPTION_CONNECTIONNOTOPEN(-2),SERVER_INVALID_RESPONSE(-3);
		private final int value;
		private ServerExceptionCode(int value){
			this.value=value;
		}
		public int getValue(){
			return value;
		}
		public static boolean exsist(int code){
			if(code==SERVER_OTHER_ERR.getValue()
				||code==SERVER_EXCEPTION_CONNECTIONNOTOPEN.getValue()
				||code==SERVER_INVALID_RESPONSE.getValue()){
				return true;
			}else{
				return false;
			}	
		}
	}
}