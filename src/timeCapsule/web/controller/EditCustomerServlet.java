package timeCapsule.web.controller;

import java.io.IOException;
import java.util.Enumeration;

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
import timeCapsule.service.impl.UserBusinessServiceImpl;
import timeCapsule.utils.WebUtils;
import timeCapsule.web.formbean.RegisterForm;

public class EditCustomerServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try{
		String id;
		User user=null;

		User userbySession = (User) request.getSession().getAttribute("user");
		ServiceFactory sf = ServiceFactory.getInstance();
		UserBusinessService service = sf.createService(UserBusinessService.class, userbySession);
		
		request.setCharacterEncoding("UTF-8");
		
		id =  (String) request.getParameter("id");
		user=service.find(id);
		RegisterForm form = new RegisterForm();
		WebUtils.copyBean(user, form);
		request.setAttribute("form", form);
		request.setAttribute("id", id);

		if(user==null){
			request.setAttribute("message", "用户名不存在<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/ListCustomerServlet'");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}		
		request.getRequestDispatcher("/WEB-INF/jsp/editcustomer.jsp").forward(request, response);
		}catch(Exception e){
			if(e.getCause() instanceof SecurityException){
				request.setAttribute("message",e.getCause().getMessage());
			}else{
				request.setAttribute("message", "服务器未知错误<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/ListCustomerServlet'");
			}
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//1.对提交表单字段进行合法性校验（把表单数据formbean）
		request.setCharacterEncoding("UTF-8");
		RegisterForm form = WebUtils.request2Bean(request, RegisterForm.class);
		boolean b = form.validate();
		String id =  request.getParameter("id");
		//2.如果校验失败，跳回到表单页面，回显校验失败信息
		if(!b){
			request.setAttribute("form", form);
			request.getRequestDispatcher("/WEB-INF/jsp/editcustomer.jsp").forward(request, response);
			return;
		}
		
		//3.如果校验成功，则调用service处理注册请求
		User user = new User();
		WebUtils.copyBean(form, user);
		user.setId(id);
		
		User userbySession = (User) request.getSession().getAttribute("user");
		ServiceFactory sf = ServiceFactory.getInstance();
		UserBusinessService service = sf.createService(UserBusinessService.class, userbySession);
		
		try {
			service.updateUserWhereChangePasswd(user);
			//6.如果serivce处理成功，跳转到网站的全局消息显示页面，为用户注册成功的消息
			request.setAttribute("message", "修改成功！！<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/ListCustomerServlet'>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
			
		} catch (UserExistException e) {
			//4.如果serivce处理不成功，并且不成功的原因，是因为注册用户已存在，则跳回到注册页面，显示用户已存在
			form.getErrors().put("username", "用户名已存在！！！");
			request.setAttribute("form", form);
			//request.setAttribute("message", "注册的用户名已存在");
			request.getRequestDispatcher("/WEB-INF/jsp/editcustomer.jsp").forward(request, response);
			return;
		}catch (EmailExistException e) {
			//4.如果serivce处理不成功，并且不成功的原因，是因为注册邮箱已存在，则跳回到注册页面，显示用户已存在
			form.getErrors().put("email", "邮箱已存在！！！");
			request.setAttribute("form", form);
			//request.setAttribute("message", "注册的用户名已存在");
			request.getRequestDispatcher("/WEB-INF/jsp/editcustomer.jsp").forward(request, response);
			return;
		}catch(Exception e){
			//5.如果serivce处理不成功，并且不成功的原因是其它问题的话，跳转到网站的全局消息显示页面，为用户显示友好错误信息
			e.printStackTrace();
			if(id!=null){
				if(e.getCause() instanceof SecurityException){
					request.setAttribute("message",e.getCause().getMessage());
				}else{
					request.setAttribute("message", "服务器未知错误！！<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/EditCustomerServlet?id="+id+"'>");
				}
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}
			if(e.getCause() instanceof SecurityException){
				request.setAttribute("message",e.getCause().getMessage());
			}else{
				request.setAttribute("message", "服务器未知错误！！<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/ListCustomerServlet>");
			}
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
	}
}