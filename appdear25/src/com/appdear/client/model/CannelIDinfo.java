package com.appdear.client.model;

import java.io.Serializable;

/**
 * Ƶ��ID
 * @author zqm
 *
 */
public class CannelIDinfo implements Serializable {
	/**
	 * Ƶ��ID
	 */
	public int sectionid;
	
	/**
	 * Ƶ��ID��ID
	 */
	public int code;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return sectionid+"="+code;
	}
	
	
	
}
