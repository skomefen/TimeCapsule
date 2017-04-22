package timeCapsule.domain;

import java.util.Date;

public class UpCapsulefile {
	private String id;
	private String uuidname;  //上传文件的名称，文件的uuid名
	private String filename; //上传文件的真实名称
	private String savepath;     //记住文件的位置
	private Date uptime;     //文件的上传时间
	private String description;  //文件的描述
	private String capsuleid; //存放所属时间胶囊id
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

