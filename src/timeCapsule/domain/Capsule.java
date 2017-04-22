package timeCapsule.domain;

import java.util.Date;

public class Capsule {
	private String id;    
	private String capsulename;  
	private Date savedate;
	private Date readdate;
	private String description;  //ÃèÊö
	private String email;
	private boolean isreaded;
	private int sendnum=0;
	private String usernameid;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isIsreaded() {
		return isreaded;
	}
	public void setIsreaded(boolean isreaded) {
		this.isreaded = isreaded;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCapsulename() {
		return capsulename;
	}
	public void setCapsulename(String capsulename) {
		this.capsulename = capsulename;
	}
	
	public Date getSavedate() {
		return savedate;
	}
	public void setSavedate(Date savedate) {
		this.savedate = savedate;
	}
	public Date getReaddate() {
		return readdate;
	}
	public void setReaddate(Date readdate) {
		this.readdate = readdate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUsernameid() {
		return usernameid;
	}
	public void setUsernameid(String usernameid) {
		this.usernameid = usernameid;
	}
	public int getSendnum() {
		return sendnum;
	}
	public void setSendnum(int sendnum) {
		this.sendnum = sendnum;
	}
	
	
}
