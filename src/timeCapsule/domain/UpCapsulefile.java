package timeCapsule.domain;

import java.util.Date;

public class UpCapsulefile {
	private String id;
	private String uuidname;  //�ϴ��ļ������ƣ��ļ���uuid��
	private String filename; //�ϴ��ļ�����ʵ����
	private String savepath;     //��ס�ļ���λ��
	private Date uptime;     //�ļ����ϴ�ʱ��
	private String description;  //�ļ�������
	private String capsuleid; //�������ʱ�佺��id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUuidname() {
		return uuidname;
	}
	public void setUuidname(String uuidname) {
		this.uuidname = uuidname;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getSavepath() {
		return savepath;
	}
	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}
	public Date getUptime() {
		return uptime;
	}
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCapsuleid() {
		return capsuleid;
	}
	public void setCapsuleid(String capsuleid) {
		this.capsuleid = capsuleid;
	}
	
	
	
}

