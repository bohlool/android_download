package com.appdear.client.service.api;

import java.util.ArrayList;

import com.appdear.client.model.ShopModel;

public class ApiShopListResult {
//	{
//	    "result": {
//	        "resultcode": "000000"
//	    },
//	    "imei": "SDF434TTTTTTTTT",
//	    "sv": "1.0",
//	    "list": {
//	        "count": 5,
//	        "items": [
//	            {
//	                "tel": "010-89898989",
//	                "name": "������ſڵ�",
//	                "addr": "���ſ�"
//	            },  
//	            {
//	                "tel": "010-89111189",
//	                "name": "�����찲�ŵ�",
//	                "addr": "�찲�Ŵ��110�Ŷ������������ڴ��45��Ӻ�����Ķ������������ڴ��45��Ӻ�����Ķ������������ڴ��45��Ӻ������"
//	            }
//	        ]
//	    }
//	}
	
	public String resultcode;
	public String imei;
	public String sv;
//	public  int shopcCount;
	public ArrayList<ShopModel> shopList;
	public int pagenum = 0;
	public int totalcount = 0;
	public int pageno = 0;
}
