package timeCapsule.web.formbean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import timeCapsule.domain.UpCapsulefile;

public class AddCapsuleForm {
	private String capsulename;
	private String readdate;
	private String description;  //描述
	private String usernameid;
	private String email;
	private List<UpCapsulefile> upfiles = null;
	private Map errors = new HashMap();
	
	public Map getErrors() {
		return errors;
	}
	public void setErrors(Map errors) {
		this.errors = errors;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCapsulename() {
		if(capsulename==null||capsulename.trim().equals("")){
			return "无名胶囊";
		}
		return capsulename;
	}
	public void setCapsulename(String capusulename) {
		this.capsulename = capusulename;
	}
	public String getReaddate() {
		return readdate;
	}
	public void setReaddate(String readdate) {
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
	public List<UpCapsulefile> getUpfiles() {
		return upfiles;
	}
	public void setUpfiles(List<UpCapsulefile> upfiles) {
		this.upfiles = upfiles;
	}
	
	public boolean validate(){
		boolean isOK = true;
		if(upfiles.isEmpty()){
			errors.put("upfiles","文件不能为空" );
			isOK = false;
		}
		
		DateLocaleConverter dlc  = new DateLocaleConverter();
		
		Date saveDate = new Date();  //存放日期
		Date readDate = null;
		
		if(this.readdate==null || this.readdate.trim().equals("")){
			errors.put("readdate", "上传日期不能为空");
			isOK = false;
		}else{
			try{
				readDate = (Date) dlc.convert(this.readdate,"yyyy-MM-dd HH:mm:ss");
			}catch (Exception e) {
				isOK = false;
				errors.put("readdate", "日期格式不对！！！");
			}
		}
		if(readDate!=null&&saveDate!=null){
			if((readDate.getTime()- saveDate.getTime())<=(long)30*24*60*60*1000){
				
				isOK = false;
				errors.put("readdate","存放日期不能少于一个月" );
			}

		}
		if(description.length()>500){
			isOK = false;
			errors.put("description", "描述的值少于500字");
		}
		
		//电子邮箱不能为空，并且要是一个格式合法的邮箱
		if(this.email==null||this.email.trim().equals("")){
			isOK = false;
			errors.put("email", "邮箱不能为空！！");
			
		}else{
			//	aaa@sina.com aaa@sina.com.cn aa_bb.cc@sina.com.cn
			// \\w+@\\w+(\\.\\w+)+
			if(!this.email.matches("\\w+@\\w+(\\.\\w+)+")){
				isOK = false;
				errors.put("email", "邮箱格式不对！");
			}
		}
				
		return isOK;
	}
	
}
