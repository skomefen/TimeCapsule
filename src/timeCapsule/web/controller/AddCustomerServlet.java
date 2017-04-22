package timeCapsule.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import timeCapsule.domain.User;
import timeCapsule.exception.EmailExistException;
import timeCapsule.exception.SecurityException;
import timeCapsule.exception.UserExistException;
import timeCapsule.factory.ServiceFactory;
import timeCapsule.service.UserBusinessService;
import timeCapsule.utils.WebUtils;
import timeCapsule.web.formbean.RegisterForm;

public class AddCustomerServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/addcustomer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//1.���ύ���ֶν��кϷ���У�飨�ѱ�����formbean��
		request.setCharacterEncoding("UTF-8");
		RegisterForm form = WebUtils.request2Bean(request, RegisterForm.class);
		boolean b = form.validate();
		
		//2.���У��ʧ�ܣ����ص���ҳ�棬����У��ʧ����Ϣ
		if(!b){
			request.setAttribute("form", form);
			request.getRequestDispatcher("/WEB-INF/jsp/addcustomer.jsp").forward(request, response);
			return;
		}
		
		//3.���У��ɹ��������service����ע������
		User user = new User();
		WebUtils.copyBean(form, user);
		user.setId(WebUtils.generateID());
		
		User userbySession=(User) request.getSession().getAttribute("user");
		ServiceFactory sf = ServiceFactory.getInstance();
		UserBusinessService service = sf.createService(UserBusinessService.class, userbySession);
		
		try {
			service.addUser(user);
			//6.���serivce����ɹ�����ת����վ��ȫ����Ϣ��ʾҳ�棬Ϊ�û�ע��ɹ�����Ϣ
			request.setAttribute("message", "ע��ɹ�����<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/AddCustomerServlet'>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
			
		} catch (UserExistException e) {
			//4.���serivce�����ɹ������Ҳ��ɹ���ԭ������Ϊע���û��Ѵ��ڣ������ص�ע��ҳ�棬��ʾ�û��Ѵ���
			form.getErrors().put("username", "ע���û����Ѵ��ڣ�����");
			request.setAttribute("form", form);
			//request.setAttribute("message", "ע����û����Ѵ���");
			request.getRequestDispatcher("/WEB-INF/jsp/addcustomer.jsp").forward(request, response);
			return;
		}catch (EmailExistException e) {
			//4.���serivce�����ɹ������Ҳ��ɹ���ԭ������Ϊע�������Ѵ��ڣ������ص�ע��ҳ�棬��ʾ�û��Ѵ���
			form.getErrors().put("email", "ע�������Ѵ��ڣ�����");
			request.setAttribute("form", form);
			//request.setAttribute("message", "ע����û����Ѵ���");
			request.getRequestDispatcher("/WEB-INF/jsp/addcustomer.jsp").forward(request, response);
			return;
		}catch(Exception e){
			//5.���serivce�����ɹ������Ҳ��ɹ���ԭ������������Ļ�����ת����վ��ȫ����Ϣ��ʾҳ�棬Ϊ�û���ʾ�Ѻô�����Ϣ
			if(e.getCause() instanceof SecurityException){
				request.setAttribute("message",e.getCause().getMessage());
			}else{
				request.setAttribute("message", "������δ֪���󣡣�<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/AddCustomerServlet'>");
			}
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
	}

}