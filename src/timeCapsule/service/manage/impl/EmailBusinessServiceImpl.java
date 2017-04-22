package timeCapsule.service.manage.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import timeCapsule.dao.CapsuleDao;
import timeCapsule.domain.Capsule;
import timeCapsule.domain.Email;
import timeCapsule.domain.QueryResult;
import timeCapsule.factory.DaoFactory;
import timeCapsule.service.manage.EmailBusinessService;
import timeCapsule.utils.ServiceUtils;

public class EmailBusinessServiceImpl implements EmailBusinessService {
	
	/********************************
	 * 
	 * 后台管理eamil
	 * 
	 * *****************************/
	
	/******************************
	 * 
	 * 时间胶囊email  只实现自动发送email功能，其他以后再实现
	 * 
	 * ***************************/
	private static Properties prop=null;
	static{
		prop = new Properties();
		InputStream in = EmailBusinessServiceImpl.class.getClassLoader().getResourceAsStream("mail.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	
	}
	public void addCapsuleAutoEmail(){
		
	}
	
	public void deleteCapsuleAutoEmail(){
		
	}
	
	public void sendEmail(Email email){
		if(email==null){
			return;
		}
		
		Session session = Session.getDefaultInstance(prop);

		Message message = ServiceUtils.createMessage(email, session);
		Transport ts;
		try {
			ts = session.getTransport();
			ts.connect(prop.getProperty("fromEmailAdress"),prop.getProperty("emailAuthorityKey"));
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}


	
	public QueryResult getCanReadCapsuleEmailAdressPage(int startindex,int pagesize){
		DaoFactory df = DaoFactory.getInstance();
		CapsuleDao dao = df.createDao(CapsuleDao.class);
		
		List<String> adresslist = new ArrayList<String>();
		QueryResult qr = dao.capsuleCanRead(startindex, pagesize);
		List<Capsule> capsulelist = qr.getList();
		for(Capsule capsule:capsulelist){
			String adress = capsule.getEmail();
			adresslist.add(adress);
		}
		qr.setList(adresslist);
		return 	qr;
	}


	@Override
	public QueryResult getCanReadCapsule(int startindex, int pagesize) {
		DaoFactory df = DaoFactory.getInstance();
		CapsuleDao dao = df.createDao(CapsuleDao.class);
		QueryResult qr =dao.capsuleCanRead(startindex, pagesize);
		List<Capsule> capsulelist = qr.getList();
		for(Capsule capsule :capsulelist){
			capsule.setSendnum(capsule.getSendnum()+1);
			dao.update(capsule);
		}
		return qr;

	}

	@Override
	public int getCanReadCapsuleCount() {
		DaoFactory df = DaoFactory.getInstance();
		CapsuleDao dao = df.createDao(CapsuleDao.class);
		int count =dao.capsuleCanReadCount();
		return count;
	}
	

	
}
