package timeCapsule.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.util.MailSSLSocketFactory;

import sun.misc.BASE64Encoder;
import timeCapsule.domain.Email;
import timeCapsule.exception.EmailExistException;

public class ServiceUtils {
	
	public static String md5(String message){
		try {
			
			return new BASE64Encoder().encode(MessageDigest.getInstance("md5").digest(message.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Message createMessage(Email email, Session session)  {
		try {
			
			//使用SSL
			//开启安全协议
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			
			MimeMessage message = new MimeMessage(session);
			
			//创建邮件
			message.setFrom(new InternetAddress(email.getFromEmailAdress()));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getRecipientEmailAdress()));
			
			message.setSubject(email.getSubject());
			
			//创建bodypart封装正文
			MimeBodyPart text = new MimeBodyPart();
			text.setContent(email.getContent(),"text/html;charset=UTF-8");
			
			//创建bodypart封装图片
			Map<String,String> imageAdressMap = email.getImageAdress();
			if(imageAdressMap==null||imageAdressMap.isEmpty()){
				
				MimeMultipart mp = new MimeMultipart();
				mp.addBodyPart(text);
				
				message.setContent(mp);
				message.saveChanges();
				
				return message;
			}
			
			MimeMultipart contentmult = new MimeMultipart();
			
			for(Entry<String, String> entry : imageAdressMap.entrySet()){
				String cid = entry.getKey();
				String imageAdress = entry.getValue();
				if(cid.trim().equals("")||imageAdress.trim().equals("")){
					continue;
				}
	
				MimeBodyPart image = new MimeBodyPart();
				image.setDataHandler(new DataHandler(new FileDataSource(imageAdress)));
				image.setContentID(cid);
				
				contentmult.addBodyPart(image);
			}
			contentmult.addBodyPart(text);
			contentmult.setSubType("related");

			
			MimeBodyPart contentbody = new MimeBodyPart();
			contentbody.setContent(contentmult);
			
			//封装附件
			
			List<String> attachmentAdressList = email.getAttachmentAdress();;
			if(attachmentAdressList==null||attachmentAdressList.isEmpty()){
				
				message.setContent(contentmult);
				message.saveChanges();
				
				return message;
			}		
			
			MimeMultipart attachment = new MimeMultipart();

			for(String attachmentAdress : attachmentAdressList){
				if(attachmentAdress.trim().equals("")){
					continue;
				}
				
				MimeBodyPart attach = new MimeBodyPart();
				DataHandler dh = new DataHandler(new FileDataSource(attachmentAdress));
				attach.setDataHandler(dh);
				attach.setFileName(MimeUtility.encodeText(dh.getName()));
				
				attachment.addBodyPart(attach);
				
			}
			
			attachment.addBodyPart(contentbody);
			attachment.setSubType("mixed");
			
			message.setContent(attachment);
			message.saveChanges();
			
			return message;
			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
