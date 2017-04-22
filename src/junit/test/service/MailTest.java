package junit.test.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.junit.Test;

import timeCapsule.domain.Capsule;
import timeCapsule.domain.Email;
import timeCapsule.domain.QueryResult;
import timeCapsule.factory.ServiceFactory;
import timeCapsule.service.CapsuleBusinessService;
import timeCapsule.service.manage.EmailBusinessService;
import timeCapsule.service.manage.impl.EmailBusinessServiceImpl;
import timeCapsule.utils.ServiceUtils;
import timeCapsule.utils.WebUtils;

public class MailTest {
	
	@Test
	public void mailtest() throws AddressException, Exception{
		//�����ʼ�
		Session session = Session.getDefaultInstance(new Properties());
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("aaa@flx.com"));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress("bbb@flx.com"));
		message.setSubject("����");
		
		
		//����bodypart��װ����
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("���������ʼ�a<img src='cid:1.jpg'>a<img src='cid:2.jpg'>", "text/html;charset=UTF-8");
		
		//����bodypart��װͼƬ
		MimeBodyPart image = new MimeBodyPart();
		image.setDataHandler(new DataHandler(new FileDataSource("src/junit/test/service/1.jpg")));
		image.setContentID("1.jpg");
		
		MimeBodyPart image2 = new MimeBodyPart();
		image2.setDataHandler(new DataHandler(new FileDataSource("src/junit/test/service/1.jpg")));
		image2.setContentID("2.jpg");
		
		//����bodypart��װ����
		MimeBodyPart attach = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource("src/junit/test/service/�������.mp3"));
		attach.setDataHandler(dh);
		attach.setFileName(MimeUtility.encodeText(dh.getName()));  //content-disposition
		
		
		
		//�������ݹ�ϵ
		MimeMultipart content = new MimeMultipart();
		content.addBodyPart(image);
		content.addBodyPart(image2);
		content.addBodyPart(text);

		content.setSubType("related");
		
		MimeBodyPart mbp = new MimeBodyPart();
		mbp.setContent(content);
		
		
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(mbp);
		mm.addBodyPart(attach);
		mm.setSubType("mixed");
		
		message.setContent(mm);
		message.saveChanges();
		
		message.writeTo(new FileOutputStream("d:\\3.eml"));
				
				
	}
	
	
	//mailtest��������
	@Test
	public void mailtest2() throws AddressException, Exception{
		//�����ʼ�
		Session session = Session.getDefaultInstance(new Properties());
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("aaa@flx.com"));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress("bbb@flx.com"));
		message.setSubject("����");
		
		
		//����bodypart��װ����
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("<html><body background='cid:bg.jpg'>lalala</body></html>", "text/html;charset=UTF-8");
		

		//����
		MimeBodyPart image2 = new MimeBodyPart();
		image2.setDataHandler(new DataHandler(new FileDataSource("src/timeCapsule/web/listener/emailbg.jpg")));
	//	image2.setHeader("Content-Location", "bg.jpg");  
		image2.setContentID("bg.jpg");

		//�������ݹ�ϵ
		MimeMultipart content = new MimeMultipart();
		content.addBodyPart(image2);
		content.addBodyPart(text);

		content.setSubType("related");

		
		message.setContent(content);
		message.saveChanges();
		
		message.writeTo(new FileOutputStream("d:\\3.eml"));
				
				
	}
	
	@Test
	public void testcreatemessage() throws Exception{
		Properties prop = new Properties();
		InputStream in = EmailBusinessServiceImpl.class.getClassLoader().getResourceAsStream("mail.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		
		Session session = Session.getDefaultInstance(prop);
		
		String fromEmailAdress = "1173326806@qq.com";
		String recipientEmailAdress = "1072760797@qq.com";
		String subject = "��Ķ�����";
		String content = "���������ʼ�a<img src='cid:1.jpg'>a<img src='cid:2.jpg'>";
		Map<String,String> imageAdress = new HashMap<>();
		imageAdress.put("1.jpg", "src/junit/test/service/1.jpg");
		imageAdress.put("2.jpg", "src/junit/test/service/1.jpg");

		List<String> attachmentAdress = new LinkedList<>();
		attachmentAdress.add("src/junit/test/service/�������.mp3");
		

		Email email = new Email();
		email.setFromEmailAdress(fromEmailAdress);
		email.setRecipientEmailAdress(recipientEmailAdress);
		email.setSubject(subject);
		email.setContent(content);
		email.setImageAdress(imageAdress);
		email.setAttachmentAdress(attachmentAdress);
		
		Message message = ServiceUtils.createMessage(email, session);
		
		message.writeTo(new FileOutputStream("d:\\3.eml"));
//		Transport ts = session.getTransport();
//		ts.connect("1173326806@qq.com","lkcsejyztflzjbga");
//		ts.sendMessage(message, message.getAllRecipients());
//		ts.close();
	}
	
	@Test
	public void testsendEmail(){
		
		String fromEmailAdress = "1173326806@qq.com";
		String recipientEmailAdress = "1072760797@qq.com";
		String subject = "��Ķ�����";
		String content = "���������ʼ�a<img src='cid:1.jpg'>a<img src='cid:2.jpg'>";
		Map<String,String> imageAdress = new HashMap<>();
		imageAdress.put("1.jpg", "src/junit/test/service/1.jpg");
		imageAdress.put("2.jpg", "src/junit/test/service/1.jpg");

		List<String> attachmentAdress = new LinkedList<>();
		attachmentAdress.add("src/junit/test/service/�������.mp3");
		

		Email email = new Email();
		email.setFromEmailAdress(fromEmailAdress);
		email.setRecipientEmailAdress(recipientEmailAdress);
		email.setSubject(subject);
		email.setContent(content);
		email.setImageAdress(imageAdress);
		email.setAttachmentAdress(attachmentAdress);
		
		EmailBusinessServiceImpl es = new EmailBusinessServiceImpl();
		es.sendEmail(email);
	}
	
	@Test
	public void autoSentEmail(){
		Timer time =  new Timer();
		Calendar now = Calendar.getInstance();
    	int day = now.get(Calendar.DAY_OF_MONTH);
    	now.set(Calendar.DAY_OF_MONTH, 311);
    	now.set(Calendar.HOUR_OF_DAY,0);
    	now.set(Calendar.MINUTE, 0);
    	now.set(Calendar.SECOND, 0);
		System.out.println("��: " + now.get(Calendar.YEAR));  
		System.out.println("��: " + (now.get(Calendar.MONTH) + 1) + "");  
		System.out.println("��: " + now.get(Calendar.DAY_OF_MONTH));  
		System.out.println("ʱ: " + now.get(Calendar.HOUR_OF_DAY));  
		System.out.println("��: " + now.get(Calendar.MINUTE));  
		System.out.println("��: " + now.get(Calendar.SECOND));  
		System.out.println("��ǰʱ���������" + now.getTimeInMillis());  
		System.out.println(now.getTime());  
		long period = 24*60*60*1000l;
		System.out.println(period);
	}
	
	@Test
	public void autoSentEmail2(){
	
		ServiceFactory sf = ServiceFactory.getInstance();
		CapsuleBusinessService cs = sf.createService(CapsuleBusinessService.class, null);
		
		String fromEmailAdress = "1173326806@qq.com";
		String subject = "���ʱ�佺�ҿ����ڳ���";
	
		Email email = new Email();
		email.setFromEmailAdress(fromEmailAdress);
		email.setSubject(subject);
		
		EmailBusinessService es = sf.createService(EmailBusinessService.class, null);
		
		QueryResult qr = new QueryResult();
		qr = es.getCanReadCapsule(0, 1);
		
		int count = qr.getTotalrecord();
		int startindex =0;
		while(startindex<=count){
			
			qr = es.getCanReadCapsule(startindex, 10);
			List<Capsule> list =qr.getList();
			
			for(Capsule capsule:list){
				email.setRecipientEmailAdress(capsule.getEmail());
				String content = "���ڳ����ʱ�佺��</br>"
						+ "http://localhost:8080/TimeCapsule/ManageUIServlet<br>"
						+ "��Ľ���id��:"+capsule.getId();
				email.setContent(content);
				es.sendEmail(email);
				System.out.println(email);
			}
			startindex+=10;
		}
	}

	
	public  static void main(String[] args){
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.SECOND,20);
		Timer time =  new Timer();
    	Date firstTime = calendar.getTime();
   // 	long period = 24*60*60*1000l;
    	long period = 10*1000l;
    	time.schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("hallo");
				ServiceFactory sf = ServiceFactory.getInstance();
				CapsuleBusinessService cs = sf.createService(CapsuleBusinessService.class, null);
				
				String fromEmailAdress = "1173326806@qq.com";
				String subject = "���ʱ�佺�ҿ����ڳ���";

				Email email = new Email();
				email.setFromEmailAdress(fromEmailAdress);
				email.setSubject(subject);
				
				EmailBusinessService es = sf.createService(EmailBusinessService.class, null);
				
				int count =  es.getCanReadCapsuleCount();
				int startindex =0;
				while(startindex<=count){
					QueryResult qr = new QueryResult();
					qr = es.getCanReadCapsule(startindex, 10);
					List<Capsule> list =qr.getList();
					
					for(Capsule capsule:list){
						email.setRecipientEmailAdress(capsule.getEmail());
						String content = "<html><body background='cid:bg.jpg'>���ڳ����ʱ�佺��</br>"
								+ "http://localhost:8080/TimeCapsule/ManageUIServlet<br>"
								+ "��Ľ���id��:"+capsule.getId()+"</body></html>";
						Map<String,String> imageAdress=new HashMap<String, String>();
						imageAdress.put("bg.jpg", "src/timeCapsule/web/listener/emailbg.jpg");
						
						email.setContent(content);
						email.setImageAdress(imageAdress);
						
						es.sendEmail(email);
						System.out.println(email);
					}
					startindex+=10;
				}
			}
		}, firstTime,period);
	}
	
//	 public static void sendMail(MailModel mail) throws Exception {  
//	        Properties props = new Properties();  
//	        props.put("mail.smtp.auth", "true");  
//	        Session session = Session.getInstance(props);  
//	        Message message = new MimeMessage(session);  
//	        InternetAddress from = new InternetAddress();  
//	        from.setPersonal(MimeUtility.encodeText("������Ҷ<cuisuqiang@163.com>"));  
//	        message.setFrom(from);  
//	        InternetAddress to = new InternetAddress(mail.getTo());  
//	        message.setRecipient(Message.RecipientType.TO, to);  
//	        // �Ƿ���  
//	        if (null != mail.getCopy() && !"".equals(mail.getCopy())) {  
//	            InternetAddress copy = new InternetAddress(mail.getCopy());  
//	            message.setRecipient(Message.RecipientType.CC, copy);  
//	        }  
//	        message.setSubject(MimeUtility.encodeText(mail.getTitle()));  
//	        message.setSentDate(new Date());  
//	        // ָ��Ϊ��Ϲ�ϵ  
//	        MimeMultipart msgMultipart = new MimeMultipart("mixed");  
//	        message.setContent(msgMultipart);  
//	        MimeBodyPart content = new MimeBodyPart();  
//	        msgMultipart.addBodyPart(content);  
//	        // ������ϵ  
//	        MimeMultipart bodyMultipart = new MimeMultipart("related");  
//	        content.setContent(bodyMultipart);  
//	        MimeBodyPart htmlPart = new MimeBodyPart();  
//	        // ��װ��˳��ǳ���Ҫ  
//	        bodyMultipart.addBodyPart(htmlPart);  
//	        MimeBodyPart in_bg = new MimeBodyPart();  
//	        bodyMultipart.addBodyPart(in_bg);  
//	  
//	        DataSource bgsou = new FileDataSource(mail.getBgPath());  
//	        DataHandler bghd = new DataHandler(bgsou);  
//	        in_bg.setDataHandler(bghd);  
//	        in_bg.setHeader("Content-Location", "bg.jpg");  
//	        // �Ƿ�ʹ���˱�������  
//	        if (null == mail.getMusicPath() || "".equals(mail.getMusicPath())) {  
//	            String start = "<html><body background='bg.jpg'>";  
//	            String end = "</body></html>";  
//	            htmlPart.setContent(start + mail.getContext() + end,"text/html;charset=UTF-8");  
//	        } else {  
//	            MimeBodyPart in_Part = new MimeBodyPart();  
//	            bodyMultipart.addBodyPart(in_Part);  
//	            DataSource gifds = new FileDataSource(mail.getMusicPath());  
//	            DataHandler gifdh = new DataHandler(gifds);  
//	            in_Part.setDataHandler(gifdh);  
//	            in_Part.setHeader("Content-Location", "bg.mp3");  
//	            String start = "<html><head><bgsound src='bg.mp3' loop='-1'></head><body background='bg.jpg'>";  
//	            String end = "</body></html>";  
//	            htmlPart.setContent(start + mail.getContext() + end,"text/html;charset=UTF-8");  
//	        }  
//	        // ��װ����  
//	        if (null != mail.getFilePath() && !"".equals(mail.getFilePath())) {           
//	            MimeBodyPart file = new MimeBodyPart();  
//	            FileDataSource file_datasource = new FileDataSource(mail  
//	                    .getFilePath());  
//	            DataHandler dh = new DataHandler(file_datasource);  
//	            file.setDataHandler(dh);  
//	            file.setFileName(MimeUtility.encodeText(dh.getName()));  
//	            msgMultipart.addBodyPart(file);  
//	        }  
//	        message.saveChanges();  
//	        // �����ʼ�  
//	        OutputStream ips = new FileOutputStream("C:\\tmp\\test.eml");  
//	        message.writeTo(ips);  
//	        ips.close();  
//	        System.out.println("------------�������------------");  
//	        // ɾ����ʱ�ļ�  
//	        if (null != mail.getMusicPath() && !"".equals(mail.getMusicPath())) {  
//	            File file = new File(mail.getMusicPath());  
//	            file.delete();  
//	        }  
//	        if (null != mail.getFilePath() && !"".equals(mail.getFilePath())) {  
//	            File file = new File(mail.getFilePath());  
//	            file.delete();  
//	        }  
//	    }  
	
	
}
