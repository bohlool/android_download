package com.appdear.client.model;

import java.util.ArrayList;
import java.util.List;

import com.appdear.client.service.api.ApiResult;

/**
 * ���۽������
 * @author zqm
 *
 */
public class ApiCommentResult implements ApiResult{

	/**
	 * ƽ̨����汾��
	 */
	public String sv;
	
	/**
	 * �ֻ����ʶ����
	 */
	public String imei;
	
	/**
	 * ��������
	 */
	public int resultcode;
	
	/**
	 * ��ǰҳ��
	 */
	public int pageno;
	
	/**
	 * ÿҳ��¼��
	 */
	public int pagenum;
	
	/**
	 * ��¼����
	 */
	public int totalcount;
	
	/**
	 * �����б�
	 */
	public List<SoftlistInfo> list = new ArrayList<SoftlistInfo>();
	
}
