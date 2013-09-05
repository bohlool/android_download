package com.appdear.client.service.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.appdear.client.exception.ApiException;
import com.appdear.client.exception.ExceptionEnum.ServerExceptionCode;
import com.appdear.client.exception.ServerException;
import com.appdear.client.model.SoftlistInfo;
import com.appdear.client.utility.StringHashMap;

/**
 * TOSO:�����ӿڵ�URL���͸���Σ�
 * 
 * 
 * ���з�����ؽӿ�
 * @author  
 *
 */
public class ApiShareActionRequest {
	
	/**
	 * 3.5.50 �û������б�ӿڣ�usersharelist��
	 * �õ��Լ����������б�
	 * @param uid
	 * @param pageNo
	 * @param count
	 * @return
	 * @throws ServerException
	 * @throws ApiException
	 */
	public static ApiShareSoftResult requestMyShareSoftList(String userid,String pageNo,String count) throws ServerException, ApiException{
		 ArrayList<SoftlistInfo> shareList = new ArrayList<SoftlistInfo>();
		 StringHashMap params = new StringHashMap();
		 ApiShareSoftResult result = null;
		 
		 params.put("userid", userid);
		 params.put("pageno", pageNo);
		 params.put("count", count);
		 
		 StringBuffer sf = new StringBuffer();
		 sf.append(ApiUrl.getusersharelist);
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
				result = new ApiShareSoftResult();
				JSONObject retJson = new JSONObject(responseStr);
				if(retJson.has("result")&&retJson.getJSONObject("result").has("resultcode")){
					result.resultcode = retJson.getJSONObject("result").getString("resultcode");
				}
				
				if(retJson.has("imei")){
					result.imei = retJson.getString("imei");
				}
				
				if(retJson.has("nickname")){
					result.nickname = retJson.getString("nickname");
				}

				if(retJson.has("popcount")){
					result.popcount = retJson.getInt("popcount");
				}
				
				if(retJson.has("userid")){
					result.userid = retJson.getString("userid");
				}
				
				if(retJson.has("gender")){
					result.gender = retJson.getInt("gender");
				}
				
				if(retJson.has("sv")){
					result.sv = retJson.getString("sv");
				}
				
				JSONObject jsonobject = null;
				if (retJson.has("page")) {
					jsonobject = retJson.getJSONObject("page");
				
				
				if(jsonobject.has("totalcount")) {
					result.totalcount = jsonobject.getInt("totalcount");
				}
				
				if(jsonobject.has("pageno")) {
					result.pageno = jsonobject.getInt("pageno");
				}
				
				if(jsonobject.has("pagenum")) {
					result.pageno = jsonobject.getInt("pagenum");
				}
				JSONArray items = jsonobject.has("items")?jsonobject.get("items")!=null?jsonobject.getJSONArray("items"):null:null;
				if (items != null) {
					for (int i = 0; i < items.length(); i ++) {
						JSONObject object = items.getJSONObject(i);
						SoftlistInfo info = new SoftlistInfo();
						info.shareid = object.has("shareid")?object.get("shareid")!=null?object.getString("shareid"):"":"";
						info.softname = object.has("softname")?object.get("softname")!=null?object.getString("softname"):"":"";
						info.softid = object.has("softid")?object.get("softid")!=null?object.getInt("softid"):0:0;
						info.softicon = object.has("softicon")?object.get("softicon")!=null?object.getString("softicon"):"":"";
						info.softprice = object.has("softprice")?object.get("softprice")!=null?object.getInt("softprice"):0:0;
						info.appid = object.has("appid")?object.get("appid")!=null?object.getString("appid"):"":"";
						info.softdesc = object.has("softdesc")?object.get("softdesc")!=null?object.getString("softdesc"):"":"";
						info.softgrade = object.has("softgrade")?object.get("softgrade")!=null?object.getInt("softgrade"):0:0;
						info.versioncode = object.has("softversioncode")?object.get("softversioncode")!=null?object.getInt("softversioncode"):0:0;
						info.download = object.has("download")?object.get("download")!=null?object.getInt("download"):0:0;
						info.softsize = object.has("softsize")?object.get("softsize")!=null?object.getInt("softsize"):0:0;
						info.downloadurl = object.has("downloadurl")?object.get("downloadurl")!=null?object.getString("downloadurl"):"":"";
						shareList.add(info);
					}
				}
					result.myshareList = shareList;
				}
			} catch (JSONException e) {
				throw new ServerException(ServerExceptionCode.SERVER_OTHER_ERR.getValue(),"�����쳣��");
			}
		}
		return result;
	}

	/**
	 * 
	 *  �ӿ� 3.5.51.	����Ӧ���б�sharelist��
	 * 
	 * 1�����ص������У��ͷ����ֶεĶ�Ӧ��ϵ�������ȴ���ʽ���Է��ؽ�� �޸ġ�
	 * ������֣��������û�����ŵ���Ϣ ��������
	 * 
	 * 
	 * 
	 * @param type ��������   1���·���    2 ��������
	 * @param order 0 ���ݷ�����������
	 * @param pageNo �����ҳ��
	 * @param count ����ÿһҳ��ʾ����Ŀ
	 * @param appcount ��ʾÿһ���û�����������Ŀ
	 * @return
	 * @throws ServerException
	 * @throws ApiException
	 */
	public static ApiShareSoftResult requestShareSoftList(String type,String order,
		 String pageNo,String count,String cid) throws ServerException, ApiException{
		 ArrayList<SoftlistInfo> shareList = null;
		 StringHashMap params = new StringHashMap();
		 ApiShareSoftResult result = null;
		 
		 params.put("order", order);
		 params.put("pageno", pageNo);
		 params.put("count",  count);
		 params.put("cid", cid);
		 
		 StringBuffer sf = new StringBuffer();
		 sf.append(ApiUrl.getsharelist);
		 sf.append("?");
		 for (Map.Entry<String,String> param : params.entrySet()) {
			 if (!sf.toString().endsWith("?"))
				 sf.append("&");
			 sf.append(param.getKey()+"="+param.getValue());
		 }
		String responseStr = APIHelper.getURL(sf.toString(), 1);
		if("".equals(responseStr)||responseStr==null){
			throw new ServerException(ServerExceptionCode.SERVER_OTHER_ERR.getValue(),"����������Ϊ�գ�");
		} else {
			try {
				result = new ApiShareSoftResult();
				JSONObject retJson = new JSONObject(responseStr);
				if(retJson.has("result")&&retJson.getJSONObject("result").has("resultcode")){
					result.resultcode = retJson.getJSONObject("result").getString("resultcode");
				}
				
				if(retJson.has("imei")){
					result.imei = retJson.getString("imei");
				}
				
				if(retJson.has("sv")){
					result.sv = retJson.getString("sv");
				}
				
				JSONObject jsonobject = null;
				if (retJson.has("page")) {
					jsonobject = retJson.getJSONObject("page");
				}
				
				if (jsonobject == null)
					return null;
				
				if(jsonobject.has("pagenum")){
					result.pagenum = jsonobject.getInt("pagenum");
				}
				
				if(jsonobject.has("totalcount")){
					result.totalcount = jsonobject.getInt("totalcount");
				}
				
				if (jsonobject.has("items")) {
					shareList = new ArrayList<SoftlistInfo>();
					JSONArray items = jsonobject.has("items")?jsonobject.get("items")!=null?jsonobject.getJSONArray("items"):null:null;
					if (items != null) {
						for (int i = 0; i < items.length(); i ++) {
							JSONObject object = items.getJSONObject(i);
							SoftlistInfo info = new SoftlistInfo();
							info.softdesc = object.has("softdesc")?object.get("softdesc")!=null?object.getString("softdesc"):"":"";
							info.appid = object.has("appid")?object.get("appid")!=null?object.getString("appid"):"":"";
							int price = object.has("softprice")?object.get("softprice")!=null?object.getInt("softprice"):0:0;
							if (price != 0) {
								info.softprice = price/100;
							}
							info.softgrade = object.has("softgrade")?object.get("softgrade")!=null?object.getInt("softgrade"):0:0;
							info.softpoints = object.has("softpoints")?object.get("softpoints")!=null?object.getInt("softpoints"):0:0;
							info.softname = object.has("softname")?object.get("softname")!=null?object.getString("softname"):"":"";
							info.softicon = object.has("softicon")?object.get("softicon")!=null?object.getString("softicon"):"":"";
							info.softid = object.has("softid")?object.get("softid")!=null?object.getInt("softid"):0:0;
							info.versioncode = object.has("versioncode")?object.get("versioncode")!=null?object.getInt("versioncode"):0:0;
							
							info.userid = object.has("userid")?object.get("userid")!=null?object.getString("userid"):"":"";
							info.username = object.has("nickname")?object.get("nickname")!=null?object.getString("nickname"):"":"";
							info.gender = object.has("gender")?object.get("gender")!=null?object.getString("gender"):"":"";
							
							
							info.download = object.has("download")?object.get("download")!=null?object.getInt("download"):0:0;
							
							shareList.add(info);
						}
						result.myshareList = shareList;
					}
				}
			} catch (JSONException e) {
				throw new ServerException(ServerExceptionCode.SERVER_OTHER_ERR.getValue(),"�����쳣��");
			}
		}
		return result;
	}

	/**
	 * �û����� ����ӿ� ,�����û�����������count�������ж��Ƿ�ɹ����ɹ�֮����ʾ�м����Ƽ��ɹ�
	 * @param userid  �û�id
	 * @param appids  ���appid����  ,���appid���飬�ԡ������ָ�
	 * @return �ɹ�����True
	 * @throws ServerException
	 * @throws ApiException
	 * {"result":{"resultcode":"000000"},"count":0,"imei":"000000000000000","sv":"1.0"}
	 * 
	 * {"result":{"resultcode":"000000"},"count":1,"apps":["com.baike.chexian"],"imei":"000000000000000","sv":"1.0"}
	 */
	public static ApiShareResult   shareMySoftList(String userid,String appids) throws ServerException, ApiException{
		 StringHashMap params = new StringHashMap();
		 ApiShareResult result = new ApiShareResult();
		 
		 params.put("userid", userid);
		 params.put("appids", appids); 
 
		 String responseStr = APIHelper.postURL(ApiUrl.shareappaction, params, 1);
		if(responseStr == null||"".equals("responseStr")){
			throw new ServerException(ServerExceptionCode.SERVER_OTHER_ERR.getValue(),"����������Ϊ�գ�");
		}else{
			try {
				JSONObject retJson = new JSONObject(responseStr);
				if(retJson.has("result")){
					if("000000".equals(retJson.getJSONObject("result").getString("resultcode"))) {
						if(retJson.has("resultcode")){
							result.resultcode = retJson.getString("resultcode");
						}
						
						if(retJson.has("count")){
							result.count = retJson.getInt("count");
						}
						
						if(retJson.has("imei")){
							result.imei = retJson.getString("imei");
						}
						
						if(retJson.has("sv")){
							result.sv = retJson.getString("sv");
						}
						
						if(retJson.has("apps")){
							JSONArray appslist = retJson.getJSONArray("apps");
							List<String> list = new ArrayList<String>();
							for (int i = 0; i < appslist.length(); i ++) {
								list.add(appslist.getString(i));
							}
							result.apps = list;
						}
					} 
				}
			} catch (JSONException e) {
				throw new ServerException(ServerExceptionCode.SERVER_OTHER_ERR.getValue(),"�����쳣��");
				//e.printStackTrace();
			}
		}
		return result;
	}



	/**
	 * �û�ȡ������ ����ӿ�
	 * @param userid  �û�id
	 * @param appids  ���appid����  ,���appid���飬�ԡ������ָ�
	 * @return �ɹ�����True
	 * @throws ServerException
	 * @throws ApiException
	 */
	public static boolean cancelShareApp(String userid,String appids) throws ServerException, ApiException{
		 StringHashMap params = new StringHashMap();
		 
		 params.put("userid", userid);
		 params.put("shareids", appids);
		 
		String responseStr = APIHelper.postURL(ApiUrl.cancelshareappaction, params, 1);
		if(responseStr == null||"".equals("responseStr")){
			throw new ServerException(ServerExceptionCode.SERVER_OTHER_ERR.getValue(),"����������Ϊ�գ�");
		}else{
			try {
				JSONObject tempObj = new JSONObject(responseStr);
				if(tempObj.has("isok")){
					if(1==tempObj.getInt("isok")){
						return true;
					}else {
						return false;
					}
				}else{
					return false;
				}
			} catch (JSONException e) {
				throw new ServerException(ServerExceptionCode.SERVER_OTHER_ERR.getValue(),"�����쳣��");
				//e.printStackTrace();
			}
		}
	}
}
