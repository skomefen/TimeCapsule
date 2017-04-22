package timeCapsule.web.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import timeCapsule.domain.Capsule;
import timeCapsule.domain.Email;
import timeCapsule.domain.QueryResult;
import timeCapsule.factory.ServiceFactory;
import timeCapsule.service.CapsuleBusinessService;
import timeCapsule.service.manage.EmailBusinessService;

/**
 * Application Lifecycle Listener implementation class AutoCapsuleEmail
 *
 */
@WebListener
public class AutoCapsuleEmail implements ServletContextListener, ServletContextAttributeListener {


    public AutoCapsuleEmail() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
    	Calendar calendar = Calendar.getInstance();
    	
    	int day = calendar.get(Calendar.DAY_OF_MONTH);
    	calendar.set(Calendar.DAY_OF_MONTH, day+1);
    	
    	calendar.set(Calendar.HOUR_OF_DAY,8);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	
		Timer time =  new Timer();
    	Date firstTime = calendar.getTime();
    	long period = 24*60*60*1000l;
    	
//    	Calendar calendar = Calendar.getInstance();
//    	calendar.set(Calendar.SECOND,20);
//		Timer time =  new Timer();
//    	Date firstTime = calendar.getTime();
//   // 	long period = 24*60*60*1000l;
//    	long period = 10*1000l;
    	
    	InputStream in = AutoCapsuleEmail.class.getClassLoader().getResourceAsStream("mail.properties");
    	
    	final Properties p = new Properties();
    	try {
			p.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
    	time.schedule(new TimerTask() {
			
			@Override
			public void run() {
				ServiceFactory sf = ServiceFactory.getInstance();
				CapsuleBusinessService cs = sf.createService(CapsuleBusinessService.class, null);
				
				String fromEmailAdress = p.getProperty("fromEmailAdress");
				String subject = "你的时间胶囊可以挖出了";

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
						String content = "<html><body background='cid:bg.jpg'>请挖出你的时间胶囊</br>"
								+ "http://localhost:8080/TimeCapsule<br>"
								+ "你的胶囊id是:"+capsule.getId()+"</body></html>";
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
    
    
	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent scae) {
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent scae) {
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent scae) {
    }


	
}
