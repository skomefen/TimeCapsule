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
	private String description;  //����
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
			return "��������";
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
			errors.put("upfiles","�ļ�����Ϊ��" );
			isOK = false;
		}
		
		DateLocaleConverter dlc  = new DateLocaleConverter();
		
		Date saveDate = new Date();  //�������
		Date readDate = null;
		
		if(this.readdate==null || this.readdate.trim().equals("")){
			errors.put("readdate", "�ϴ����ڲ���Ϊ��");
			isOK = false;
		}else{
			try{
				readDate = (Date) dlc.convert(this.readdate,"yyyy-MM-dd HH:mm:ss");
			}catch (Exception e) {
				isOK = false;
				errors.put("readdate", "���ڸ�ʽ���ԣ�����");
			}
		}
		if(readDate!=null&&saveDate!=null){
			if((readDate.getTime()- saveDate.getTime())<=(long)30*24*60*60*1000){
				
				isOK = false;
				errors.put("readdate","������ڲ�������һ����" );
			}

		}
		if(description.length()>500){
			isOK = false;
			errors.put("description", "������ֵ����500��");
		}
		
		//�������䲻��Ϊ�գ�����Ҫ��һ����ʽ�Ϸ�������
		if(this.email==null||this.email.trim().equals("")){
			isOK = false;
			errors.put("email", "���䲻��Ϊ�գ���");
			
		}else{
			//	aaa@sina.com aaa@sina.com.cn aa_bb.cc@sina.com.cn
			// \\w+@\\w+(\\.\\w+)+
			if(!this.email.matches("\\w+@\\w+(\\.\\w+)+")){
				isOK = false;
				errors.put("email", "�����ʽ���ԣ�");
			}
		}
				
		return isOK;
	}
	
}
