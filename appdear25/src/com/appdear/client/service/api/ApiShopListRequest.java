package com.appdear.client.service.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appdear.client.exception.ApiException;
import com.appdear.client.exception.ExceptionEnum.ServerExceptionCode;
import com.appdear.client.exception.ServerException;
import com.appdear.client.model.ShopModel;
import com.appdear.client.utility.JsonUtil;
import com.appdear.client.utility.ServiceUtils;
import com.appdear.client.utility.StringHashMap;

/**
 * ���ݵ�ַ��������б�
 * @author zxy
 *
 */
public class ApiShopListRequest {
//	
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
	
	/**
	 * city�������
	 * @throws UnsupportedEncodingException 
	 */
	public static ApiShopListResult ShopListRequest(String city,String chtype,String androidchcode) throws ServerException, ApiException, UnsupportedEncodingException{
		ApiShopListResult  result =  null;
		ArrayList<ShopModel> shopList = null;
		StringHashMap params = new StringHashMap();
		params.put("cityid",city);
		params.put("chtype",chtype);
		params.put("androidchcode",androidchcode);
		StringBuffer sf = new StringBuffer();
		sf.append(ApiUrl.shoplist);
		sf.append("?");
		for (Map.Entry<String,String> param : params.entrySet()) {
			if (!sf.toString().endsWith("?"))
				sf.append("&");
			sf.append(param.getKey()+"="+param.getValue());
		}
		
		String responseStr = APIHelper.getURL(sf.toString(), 1);
		if("".equals(responseStr)||responseStr==null){
			throw new ServerException(ServerExceptionCode.SERVER_OTHER_ERR.getValue(),"����������Ϊ�գ�");
		}else{
			try {
				result = new ApiShopListResult();
				JSONObject retJson;
				
				retJson = new JSONObject(responseStr);
				
				if (retJson.has("result")&&retJson.getJSONObject("result").has("resultcode")) {
					result.resultcode = retJson.getJSONObject("result").getString("resultcode");
				}
				
				if (retJson.has("imei")) {
					result.resultcode = retJson.getString("imei");
				}

				if(retJson.has("sv")){
					result.resultcode = retJson.getString("sv");
				}
				
				if(retJson.has("list")){
					JSONObject list = retJson.getJSONObject("list");
					result.totalcount = list.getInt("count");
					ShopModel  model = null;
					if (list.has("items")) {
						shopList = new  ArrayList<ShopModel>();
						JSONArray items = list.getJSONArray("items");
						for(int i =0;i<items.length();i++){
							JSONObject obj = items.getJSONObject(i);
							model = new ShopModel();
							model.name = JsonUtil.getString(obj, "name", "����");
							model.tel = JsonUtil.getString(obj, "tel", "����");
							model.addr = JsonUtil.getString(obj, "addr", "����");
							shopList.add(model);
						}
					}
					result.shopList = shopList;
				}
				
				
		}catch (JSONException e) {
			throw new ApiException(ServerExceptionCode.SERVER_OTHER_ERR.getValue(),"�����쳣��");
		} 
		return result;
	}
	}
}
