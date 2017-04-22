package timeCapsule.web.formbean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

public class RegisterForm {
	
	private String username;
	private String password;
	private String password2;
	private String email;
	private String nickname;
	
	private Map errors = new HashMap();
	
	public Map getErrors() {
		return errors;
	}
	public void setErrors(Map errors) {
		this.errors = errors;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	//�û�������Ϊ�գ�����Ҫ��3-8λ���ֻ���ĸ
	//���벻��Ϊ�ա�����Ҫ��3-8λ���ֻ���ĸ
	//ȷ�����벻��Ϊ�գ�����Ҫ�͵�һ��һ��
	//�������䲻��Ϊ�գ�����Ҫ��һ����ʽ�Ϸ�������
	//���տ���Ϊ�գ���Ϊ��ʱ������Ҫ��һ������
	//�ǳƲ�����Ϊ��
	public boolean validate(){
		boolean isOK = true;
		
		//�û�������Ϊ�գ�����Ҫ��3-8λ���ֻ���ĸ
		if(this.username==null||this.username.trim().equals("")){
			isOK = false;
			errors.put("username", "�û�������Ϊ�գ���");
			
		}else{
			if(!this.username.matches("[A-Za-z0-9]{3,16}")){
				isOK = false;
				errors.put("username", "�û�������Ҫ��3-16λ��ĸ");
			}
		}
		
		//���벻��Ϊ�ա�����Ҫ��3-8λ���ֻ���ĸ
		if(this.password==null||this.password.trim().equals("")){
			isOK = false;
			errors.put("password", "���벻��Ϊ�գ���");
			
		}else{
			if(!this.password.matches("[A-Za-z0-9]{3,16}")){
				isOK = false;
				errors.put("password", "�������Ҫ��3-16λ");
			}
		}
		
		//ȷ�����벻��Ϊ�գ�����Ҫ�͵�һ��һ��
		if(this.password2==null||this.password2.trim().equals("")){
			isOK = false;
			errors.put("password2", "ȷ�����벻��Ϊ�գ���");
			
		}else{
			if(!this.password2.equals(password)){
				isOK = false;
				errors.put("password2", "��������Ҫһ�£���");
			}
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
		
		
		
		//�ǳƲ�����Ϊ��
		if(this.nickname==null||this.nickname.trim().equals("")){
			isOK = false;
			errors.put("nickname", "�ǳƲ���Ϊ�գ���");
			
		}else{
			if(!this.nickname.matches("[\u4e00-\u9fa5A-Za-z0-9]+")){
				isOK = false;
				errors.put("nickname", "�ǳ�Ҫ����������ĸ��");
			}
		}
		
		return isOK;
	}
}
