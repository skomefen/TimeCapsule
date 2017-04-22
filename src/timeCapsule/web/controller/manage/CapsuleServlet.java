package timeCapsule.web.controller.manage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import timeCapsule.domain.Capsule;
import timeCapsule.domain.Email;
import timeCapsule.domain.PageBean;
import timeCapsule.domain.QueryInfo;
import timeCapsule.domain.UpCapsulefile;
import timeCapsule.domain.User;
import timeCapsule.exception.EmailExistException;
import timeCapsule.exception.NoTypeException;
import timeCapsule.exception.SecurityException;
import timeCapsule.exception.UserExistException;
import timeCapsule.factory.ServiceFactory;
import timeCapsule.service.CapsuleBusinessService;
import timeCapsule.service.UserBusinessService;
import timeCapsule.service.manage.CapsuleManageBusinessService;
import timeCapsule.service.manage.EmailBusinessService;
import timeCapsule.utils.WebUtils;
import timeCapsule.web.formbean.AddCapsuleForm;
import timeCapsule.web.formbean.RegisterForm;

public class CapsuleServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("intomailview")){
			intoMailView(request,response);
		}
		if(method.equals("setautomail")){
			setAutoMail(request,response);
		}
		if(method.equals("add")){
			addCapsule(request,response);
		}
		if(method.equals("goadd")){
			goaddCapsule(request,response);
		}
		if(method.equals("list")){
			listCapsule(request,response);
		}
		if(method.equals("goedit")){
			goeditCapsule(request,response);
		}
		if(method.equals("edit")){
			editCapsule(request,response);
		}
		if(method.equals("delete")){
			deleteCapsule(request,response);
		}
		if(method.equals("deletefile")){
			deletefileCapsule(request,response);
		}
	}

	private void deletefileCapsule(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void goeditCapsule(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try{

			User userbySession = (User) request.getSession().getAttribute("user");
			ServiceFactory sf = ServiceFactory.getInstance();
			CapsuleManageBusinessService service = sf.createService(CapsuleManageBusinessService.class, userbySession);
			
			request.setCharacterEncoding("UTF-8");
			String id =  (String) request.getParameter("id");
			Capsule capsule=service.findCapsule(id);
			AddCapsuleForm form = new AddCapsuleForm();
			WebUtils.copyBean(capsule, form);
			request.setAttribute("form", form);
			request.setAttribute("id", id);

			if(capsule==null){
				request.setAttribute("message", "���Ҳ�����<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/CapsuleServlet?method=list'");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
	
				return;
			}	
			
			List<UpCapsulefile> list = service.findCapulefile(id);
			request.setAttribute("list", list);

			request.getRequestDispatcher("/WEB-INF/jsp/manage/capsule/editcapsule.jsp").forward(request, response);
			}catch(Exception e){
				if(e.getCause() instanceof SecurityException){
					request.setAttribute("message",e.getCause().getMessage());
				}else{
					request.setAttribute("message", "������δ֪����<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/CapsuleServlet?method=list'");
				}
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
	}

	private void goaddCapsule(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/jsp/manage/capsule/addcapsule.jsp").forward(request, response);
	}

	private void deleteCapsule(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try{
			String id = request.getParameter("id");
			
			User user = (User) request.getSession().getAttribute("user");
			ServiceFactory sf = ServiceFactory.getInstance();
			CapsuleManageBusinessService bs = sf.createService(CapsuleManageBusinessService.class, user);
			
			bs.deleteCapsule(id);
			request.setAttribute("message", "ɾ���ɹ�<meta http-equiv='refresh' content='1;url="+request.getContextPath()+"/CapsuleServlet?method=list'");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}catch(Exception e){
			if(e.getCause() instanceof SecurityException){
				request.setAttribute("message",e.getCause().getMessage());
			}else{
				request.setAttribute("message", "������δ֪����");
			}
			request.getRequestDispatcher("/message.jsp").forward(request, response);

		}
		
	}

	private void editCapsule(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//1.���ύ���ֶν��кϷ���У�飨�ѱ�����formbean��
				request.setCharacterEncoding("UTF-8");
				AddCapsuleForm form = WebUtils.request2Bean(request, AddCapsuleForm.class);
				boolean b = form.validate();
				String id =  request.getParameter("id");
				//2.���У��ʧ�ܣ����ص���ҳ�棬����У��ʧ����Ϣ
				if(!b){
					request.setAttribute("form", form);
					request.getRequestDispatcher("/WEB-INF/jsp/editcustomer.jsp").forward(request, response);
					return;
				}
				
				//3.���У��ɹ��������service����ע������
				Capsule capsule = new Capsule();
				WebUtils.copyBean(form, capsule);
				capsule.setId(id);
				
				User userbySession = (User) request.getSession().getAttribute("user");
				ServiceFactory sf = ServiceFactory.getInstance();
				CapsuleManageBusinessService service = sf.createService(CapsuleManageBusinessService.class, userbySession);
				
				try {
					service.updateCapsule(capsule);
					//6.���serivce����ɹ�����ת����վ��ȫ����Ϣ��ʾҳ�棬Ϊ�û�ע��ɹ�����Ϣ
					request.setAttribute("message", "�޸ĳɹ�����<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/CapsuleServlet?method=list'>");
					request.getRequestDispatcher("/message.jsp").forward(request, response);
					return;
					
				}catch(Exception e){
					//5.���serivce�����ɹ������Ҳ��ɹ���ԭ������������Ļ�����ת����վ��ȫ����Ϣ��ʾҳ�棬Ϊ�û���ʾ�Ѻô�����Ϣ
					e.printStackTrace();
					if(id!=null){
						if(e.getCause() instanceof SecurityException){
							request.setAttribute("message",e.getCause().getMessage());
						}else{
							request.setAttribute("message", "������δ֪���󣡣�<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/CapsuleServlet?method=list&id="+id+"'>");
						}
						request.getRequestDispatcher("/message.jsp").forward(request, response);
						return;
					}
					if(e.getCause() instanceof SecurityException){
						request.setAttribute("message",e.getCause().getMessage());
					}else{
						request.setAttribute("message", "������δ֪���󣡣�<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/CapsuleServlet?method=list>");
					}
					request.getRequestDispatcher("/message.jsp").forward(request, response);
					return;
				}
		
	}

	private void listCapsule(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try{
			
			User userbySession = (User) request.getSession().getAttribute("user");
			ServiceFactory sf = ServiceFactory.getInstance();
			CapsuleManageBusinessService service = sf.createService(CapsuleManageBusinessService.class, userbySession);
			
			QueryInfo queryinfo = WebUtils.request2Bean(request, QueryInfo.class);
			PageBean pagebean = service.pageQueryCapsule(queryinfo);
			request.setAttribute("pagebean", pagebean);
			request.getRequestDispatcher("/WEB-INF/jsp/manage/capsule/listcapsule.jsp").forward(request, response);
		}catch(Exception e){
			if(e.getCause() instanceof SecurityException){
				request.setAttribute("message",e.getCause().getMessage());
			}else{
				request.setAttribute("message", "��ѯ�ͻ�ʧ��");
			}
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	}

	private void addCapsule(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AddCapsuleForm form = null;
		try {
			List<String> types = Arrays.asList("jpg","gif","txt","png");
		
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//�����ڴ滺������С
			factory.setSizeThreshold(20*1024);
			//����Ӳ�̻�����
			factory.setRepository(new File(this.getServletContext().getRealPath("/WEB-INF/temp")));
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			WebUtils.loadbar(request, upload);
			
			form = new AddCapsuleForm();
			Capsule capsule = new Capsule();
			List<UpCapsulefile> upfiles = null;
		
			String uppath = this.getServletContext().getRealPath("/WEB-INF/upload");
			form = WebUtils.doUploadCapsuleForm(request, uppath, upload,types);
			if(!form.validate()){
				request.setAttribute("form", form);
				request.getRequestDispatcher("/WEB-INF/jsp/manage/capsule/listcapsule.jsp").forward(request, response);
				return;
			}
			
			WebUtils.copyBean(form, capsule);
			capsule.setId(WebUtils.generateID());
			capsule.setSavedate(new Date());
			upfiles = form.getUpfiles();	
			for(UpCapsulefile upfile : upfiles){
				upfile.setCapsuleid(capsule.getId());
				upfile.setDescription("������ʱ�佺�ң�"+capsule.getId()+"_"+capsule.getCapsulename()+" �������û�id:"+capsule.getUsernameid());
			}
			User user = (User) request.getSession().getAttribute("user");
			ServiceFactory sf = ServiceFactory.getInstance();
			CapsuleManageBusinessService cbs = sf.createService(CapsuleManageBusinessService.class, user);
			
			cbs.addCapsule(capsule, upfiles);
			
			request.setAttribute("message", "��Ľ���������<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/CapsuleServlet?method=list'");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			form.getErrors().put("upfiles", "�ļ���С���ܳ���50m");
			request.setAttribute("form", form);
			request.getRequestDispatcher("/WEB-INF/jsp/manage/capsule/listcapsule.jsp").forward(request, response);
		} catch(NoTypeException e){
			form.getErrors().put("upfiles", "��֧�ֵ��ļ�����");
			request.setAttribute("form", form);
			request.getRequestDispatcher("/WEB-INF/jsp/manage/capsule/listcapsule.jsp").forward(request, response);
		}catch(Exception e){
			if(e.getCause() instanceof SecurityException){
				request.setAttribute("message",e.getCause().getMessage());
			}else{
				request.setAttribute("message", "������δ֪����");
			}
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	}

	
	
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void intoMailView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/manage/capsule/mail.jsp").forward(request, response);
	}
	
	private void setAutoMail(HttpServletRequest request,
			HttpServletResponse response) {
//		
//		User user = (User) request.getSession().getAttribute("user");
//		ServiceFactory sf = ServiceFactory.getInstance();
//		
//		
//		
//		String fromEmailAdress = "1173326806@qq.com";
//		String recipientEmailAdress = "1072760797@qq.com";
//		String subject = "���";
//		String content = "���������ʼ�a<img src='cid:1.jpg'>a<img src='cid:2.jpg'>";
//		Map<String,String> imageAdress = new HashMap<>();
//		imageAdress.put("1.jpg", "src/junit/test/service/1.jpg");
//		imageAdress.put("2.jpg", "src/junit/test/service/1.jpg");
//		List<String> attachmentAdress = new LinkedList<>();
//		attachmentAdress.add("src/junit/test/service/�������.mp3");
//		
//
//		Email email = new Email();
//		email.setFromEmailAdress(fromEmailAdress);
//		email.setRecipientEmailAdress(recipientEmailAdress);
//		email.setSubject(subject);
//		email.setContent(content);
//		email.setImageAdress(imageAdress);
//		email.setAttachmentAdress(attachmentAdress);
//		
//		EmailBusinessService es = sf.createService(EmailBusinessService.class, user);
//		es.sendEmail(email);
	}
}
