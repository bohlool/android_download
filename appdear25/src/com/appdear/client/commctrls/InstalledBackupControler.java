package com.appdear.client.commctrls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import com.appdear.client.AlterInstalledRestoreActivity;
import com.appdear.client.BeiFenActivity;
import com.appdear.client.ContactOperateActivity;
import com.appdear.client.R;
import com.appdear.client.exception.ApiException;
import com.appdear.client.model.PackageinstalledInfo;
import com.appdear.client.model.SoftlistInfo;
import com.appdear.client.service.Constants;
import com.appdear.client.service.api.ApiInstalledRecoverSoftListResult;
import com.appdear.client.service.api.ApiManager;
import com.appdear.client.service.api.ApiNormolResult;
import com.appdear.client.utility.ServiceUtils;

public class InstalledBackupControler {

	public List<String> appFailedNameList;// ����˷���ʧ��Ӧ���б�
	public Button installedBackup;// �Ѱ�װ�������
	public Button installeRestore;// �Ѱ�װ�����ԭ
	private BeiFenActivity activity;
	public Handler handler;
	public static List<SoftlistInfo> listData = null;

	public Hashtable<String, PackageinstalledInfo> installMap = new Hashtable<String, PackageinstalledInfo>();

	public void initView() {
		installedBackup = (Button) activity.findViewById(R.id.installed_backup);
		installeRestore = (Button) activity
				.findViewById(R.id.installed_restore);
		installedBackup.setOnClickListener(activity);
		installeRestore.setOnClickListener(activity);
	}

	public void clickBackup() {

		activity.dialog_daren();
		new Thread() {
			public void run() {

				String userid = SharedPreferencesControl
						.getInstance()
						.getString(
								"userid",
								com.appdear.client.commctrls.Common.USERLOGIN_XML,
								activity);
				String imsi = ServiceUtils.getSimsi(activity);
				installMap = ServiceUtils.getInstalledApps(activity, false,
						true);
				if (installMap == null || installMap.size() == 0||installMap.size()==1) {
					activity.showMessageInfo("��װ�б�Ϊ��");
					handler.sendEmptyMessage(ContactOperateActivity.INSTALLED_FAIL);
					return;
				}
				StringBuffer installbuffer = new StringBuffer();
				List<String> installList = new ArrayList<String>();
				if (installMap != null && installMap.size() != 0) {
					Enumeration<String> e = installMap.keys();
					installList = Collections.list(e);

					for (String item : installList) {
						installbuffer.append(item + ",");
					}
				}
		    //  System.out.println("installlist===="+installbuffer.toString());

			//   System.out.println("installlist===="+installbuffer.toString().substring(0,installbuffer.toString().length()-1));
				ApiNormolResult result = null;
				try {
				 
					result = ApiManager.installedBackup(
							userid,
							imsi,
							installbuffer.toString().substring(0,
									installbuffer.toString().length() - 1));

				} catch (ApiException e) {
					if (Constants.DEBUG)
						Log.e("net error:", e.getMessage(), e);
					activity.showMessageInfo(e.getMessage());
					handler.sendEmptyMessage(ContactOperateActivity.INSTALLED_FAIL);
					return;
				} catch (JSONException e) {
					e.printStackTrace();
					activity.showMessageInfo(e.getMessage());
					handler.sendEmptyMessage(ContactOperateActivity.INSTALLED_FAIL);
					return;
				}
				// [5,6,10,12]
				//  System.out.println("failidx===="+result.failidx);
				appFailedNameList = new ArrayList<String>();
				if (result != null && result.isok == 1) {
					if (!"".equals(result.failidx)) {
						String[] strs = result.failidx.split(",");
						int i = 1;
						for (String item : strs) {
							PackageinstalledInfo packinfo = installMap
									.get(installList.get(Integer.parseInt(item)));
							appFailedNameList.add((i++) + " "
									+ packinfo.appname);
						}

					}
				}else
				{
					activity.showMessageInfo("����û����Ϣ��");
					handler.sendEmptyMessage(ContactOperateActivity.INSTALLED_FAIL);
				}
				activity.myHandler.sendEmptyMessage(2);
			};
		}.start();
	}

	public void clickRestore() {
		activity.dialog_daren();
		new Thread() {
			public void run() {
				String userid = SharedPreferencesControl
						.getInstance()
						.getString(
								"userid",
								com.appdear.client.commctrls.Common.USERLOGIN_XML,
								activity);
				String imsi = ServiceUtils.getSimsi(activity);
				ApiInstalledRecoverSoftListResult result = null;
				try {		 
					 result = ApiManager.installedRecover(userid, imsi);
				} catch (JSONException e) {
 					e.printStackTrace();
 					activity.showMessageInfo(e.getMessage());
					handler.sendEmptyMessage(ContactOperateActivity.INSTALLED_FAIL);
					return ;
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					activity.showMessageInfo(e.getMessage());
					handler.sendEmptyMessage(ContactOperateActivity.INSTALLED_FAIL);
					return ;
				}
				if (result != null &&result.isok==2)
				{
					activity.showMessageInfo("�ƶ˰�װ�б�Ϊ�գ������ϴ���");
					handler.sendEmptyMessage(ContactOperateActivity.INSTALLED_FAIL);
					return;
				}
				if (result != null &&result.isok!=0&& result.softList != null) {
					// System.out.println("size===" + result.softList.size());
					if(result.softList.size()==0)
					{
						activity.showMessageInfo("�ƶ˰�װ�б�Ϊ�գ������ϴ���");
						handler.sendEmptyMessage(ContactOperateActivity.INSTALLED_FAIL);
						return;
					}
					listData = result.softList;
				   /* for(SoftlistInfo info:listData)
				    {
				    	System.out.println("appid="+info.appid);
				    }*/
				} else
				{
					activity.showMessageInfo("��װ�б�ԭʧ�ܣ�");
					handler.sendEmptyMessage(ContactOperateActivity.INSTALLED_FAIL);
					return;
				}

				/*
				 * int id = SharedPreferencesControl.getInstance().getInt("102",
				 * com.appdear.client.commctrls.Common.SECTIONCODEXML,
				 * activity); ApiSoftListResult result=null; try { result =
				 * ApiManager.dynamicsoftlist(id+"", 1 + "", 50 + "","a"); }
				 * catch (ApiException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } listData = result.softList;
				 */
				activity.myHandler.sendEmptyMessage(3);
			};
		}.start();
	}

	public void handleBackup() {
		int backupOkNum = installMap.size() - appFailedNameList.size();
		// appFailedNameList;
		StringBuffer buffer = new StringBuffer("");
		if (backupOkNum != 0 && appFailedNameList.size() == 0) {
			buffer.append("��װ�б��ݳɹ�\n");
			buffer.append("�ɹ�����" + backupOkNum + "�����");
		}else
		{
			if (backupOkNum == 0 && appFailedNameList.size() != 0) {
				buffer.append(appFailedNameList.size() + "�����δ���ݣ�δ���ݵ�������£�\n");
			} else {
				buffer.append("�Ѿ��ɹ�����" + backupOkNum + "�������"
						+ appFailedNameList.size() + "�����δ���ݣ�δ���ݵ�������£�\n");
			}
			if (appFailedNameList.size() != 0) {
				for (String item : appFailedNameList) {
					buffer.append(item + "\n");
				}
			}
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("��װ�б��ݽ��") // ����
				.setIcon(R.drawable.installed_backup) // icon
				// .setCancelable(false) //����Ӧback��ť
				.setMessage(buffer.toString()) // �Ի�����ʾ����
				// ���ð�ť
				.setPositiveButton("ȷ��", null);
		// ����Dialog����
		AlertDialog dlg = builder.create();
		dlg.show();
	}

	public void handleRestore() {
		if (listData == null || listData.size() == 0) {
			return;
		}
		Hashtable<String,PackageinstalledInfo>  installlists= ServiceUtils.getInstalledApps(activity, false,true);
		boolean isAllInstalled=true;
		for(SoftlistInfo item:listData  )
		{
			if(installlists.get(item.appid)!=null)
			{
				item.type=1;// 1 �Ѿ���װ  0û�а�װ 
			}else
			{
				isAllInstalled=false;
				item.type=0;
			}
		}
		if(isAllInstalled)
		{
			activity.showMessageInfo("�����ޱ仯�����軹ԭ");
		   return ;	
		}
		Intent intent = new Intent(activity,
				AlterInstalledRestoreActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		Bundle bundle = new Bundle();// ��������Я������
		intent.putExtras(bundle);
		this.activity.startActivity(intent);
	}

	public List<SoftlistInfo> getListData() {
		return listData;
	}

	public void setActivity(BeiFenActivity activity, Handler handler) {
		this.activity = activity;
		this.handler = handler;
	}
}
