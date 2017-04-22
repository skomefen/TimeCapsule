package timeCapsule.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import timeCapsule.dao.UserDao;
import timeCapsule.domain.PageBean;
import timeCapsule.domain.QueryInfo;
import timeCapsule.domain.User;
import timeCapsule.exception.SecurityException;
import timeCapsule.factory.DaoFactory;
import timeCapsule.factory.ServiceFactory;
import timeCapsule.service.UserBusinessService;
import timeCapsule.service.impl.UserBusinessServiceImpl;
import timeCapsule.utils.WebUtils;

public class ListCustomerServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try{
			
			User userbySession = (User) request.getSession().getAttribute("user");
			ServiceFactory sf = ServiceFactory.getInstance();
			UserBusinessService service = sf.createService(UserBusinessService.class, userbySession);
			
			QueryInfo queryinfo = WebUtils.request2Bean(request, QueryInfo.class);
			PageBean pagebean = service.pageQuery(queryinfo);
			request.setAttribute("pagebean", pagebean);
			request.getRequestDispatcher("/WEB-INF/jsp/listcustomer.jsp").forward(request, response);
		}catch(Exception e){
			if(e.getCause() instanceof SecurityException){
				request.setAttribute("message",e.getCause().getMessage());
			}else{
				request.setAttribute("message", "²éÑ¯¿Í»§Ê§°Ü");
			}
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}