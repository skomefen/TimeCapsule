package timeCapsule.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import timeCapsule.domain.User;
import timeCapsule.exception.SecurityException;
import timeCapsule.factory.ServiceFactory;
import timeCapsule.service.CapsuleBusinessService;
import timeCapsule.service.UserBusinessService;
import timeCapsule.service.impl.UserBusinessServiceImpl;

public class DeleteCustomerServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try{
			String id = request.getParameter("id");
			
			User user = (User) request.getSession().getAttribute("user");
			ServiceFactory sf = ServiceFactory.getInstance();
			UserBusinessService bs = sf.createService(UserBusinessService.class, user);
			
			bs.deleteUser(id);
			request.setAttribute("message", "删除成功<meta http-equiv='refresh' content='1;url="+request.getContextPath()+"/ListCustomerServlet'");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}catch(Exception e){
			if(e.getCause() instanceof SecurityException){
				request.setAttribute("message",e.getCause().getMessage());
			}else{
				request.setAttribute("message", "服务器未知错误");
			}
			request.getRequestDispatcher("/message.jsp").forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}