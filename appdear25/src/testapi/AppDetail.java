package testapi;

import java.io.Serializable;
import java.util.List;

/**
 * App����
 */
public class AppDetail implements Serializable {
	public String id; // Ӧ��Id
	public DetailContent content;
	public DetailApp app;
	
	public int  status= 3;
	public String statusDescOpt;
	public String staffIdOpt;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public DetailContent getContent()
	{
		return content;
	}
	public void setContent(DetailContent content)
	{
		this.content = content;
	}
	public DetailApp getApp()
	{
		return app;
	}
	public void setApp(DetailApp app)
	{
		this.app = app;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public String getStatusDescOpt()
	{
		return statusDescOpt;
	}
	public void setStatusDescOpt(String statusDescOpt)
	{
		this.statusDescOpt = statusDescOpt;
	}
	public String getStaffIdOpt()
	{
		return staffIdOpt;
	}
	public void setStaffIdOpt(String staffIdOpt)
	{
		this.staffIdOpt = staffIdOpt;
	} 
	
	/*public String appName; // Ӧ������
	public String category; // Ӧ�����
	public String package_name; // Ӧ�ð�Id
	public String version; // Ӧ��VersionName
	public int version_code; // Ӧ��VersionCode
	public String thumbnail; // ����ͼ����ͼ�꣩url
	public long appSize; // �ļ���С
	public String developer; // ������
	public String description; // Ӧ������
	public String download_url; // ���ص�ַ
	public List<String> preview; // Ԥ��ͼ�б�
    public String released_on; //����ʱ��
//    public String _tid; //�ն��ṩ����ֵ
	public String title; // ���ã���description��ͬ��
	// �����ֶ��Ƿ������ظ��ӵ���Ϣ
	public int downStatus; //0--δ���� 1--�������� 2--������� -1--����ʧ�� -2--�������޷���
*/
	 
}
