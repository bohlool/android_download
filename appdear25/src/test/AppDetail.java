package test;

import java.io.Serializable;
import java.util.List;

/**
 * App����
 */
public class AppDetail implements Serializable {
	public String id; // Ӧ��Id
	public String appName; // Ӧ������
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AppDetail))
			return false;
		return ((AppDetail) obj).id != null
				&& ((AppDetail) obj).id.equals(this.id);
	}

	@Override
	public String toString()
	{
		return "AppDetail [id=" + id + ", appName=" + appName + ", category="
				+ category + ", package_name=" + package_name + ", version="
				+ version + ", version_code=" + version_code + ", thumbnail="
				+ thumbnail + ", appSize=" + appSize + ", download_url="
				+ download_url + ", downStatus=" + downStatus + "]";
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getAppName()
	{
		return appName;
	}

	public void setAppName(String appName)
	{
		this.appName = appName;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getPackage_name()
	{
		return package_name;
	}

	public void setPackage_name(String package_name)
	{
		this.package_name = package_name;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public int getVersion_code()
	{
		return version_code;
	}

	public void setVersion_code(int version_code)
	{
		this.version_code = version_code;
	}

	public String getThumbnail()
	{
		return thumbnail;
	}

	public void setThumbnail(String thumbnail)
	{
		this.thumbnail = thumbnail;
	}

	public long getAppSize()
	{
		return appSize;
	}

	public void setAppSize(long appSize)
	{
		this.appSize = appSize;
	}

	public String getDeveloper()
	{
		return developer;
	}

	public void setDeveloper(String developer)
	{
		this.developer = developer;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDownload_url()
	{
		return download_url;
	}

	public void setDownload_url(String download_url)
	{
		this.download_url = download_url;
	}

	public List<String> getPreview()
	{
		return preview;
	}

	public void setPreview(List<String> preview)
	{
		this.preview = preview;
	}

	public String getReleased_on()
	{
		return released_on;
	}

	public void setReleased_on(String released_on)
	{
		this.released_on = released_on;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getDownStatus()
	{
		return downStatus;
	}

	public void setDownStatus(int downStatus)
	{
		this.downStatus = downStatus;
	}
}
