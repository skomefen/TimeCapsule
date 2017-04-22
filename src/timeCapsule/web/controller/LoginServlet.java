package timeCapsule.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import timeCapsule.domain.User;
import timeCapsule.factory.ConfigFactory;
import timeCapsule.factory.ServiceFactory;
import timeCapsule.service.UserBusinessService;
import timeCapsule.service.impl.UserBusinessServiceImpl;
import timeCapsule.utils.WebUtils;


public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try{
			ConfigFactory f = ConfigFactory.getInstance();
			int validtime = f.getLoginexpirestime();
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			User userbySession = (User) request.getSession().getAttribute("user");
			ServiceFactory sf = ServiceFactory.getInstance();
			UserBusinessService service = sf.createService(UserBusinessService.class, userbySession);
			
			
			User user = service.login(username, password);
			
			if(user!=null){
				
				String key = f.getKey();
				
//				cookieֵ��Ϊautologin=username:expirestime:md5(state:password:expirestime:username)
//				key:isOK
				
				Cookie cookie = WebUtils.makeCookie(username, user.getPassword(), this.getServletContext().getContextPath(), key, validtime);
				
				response.addCookie(cookie);
				
				request.getSession().setAttribute("user", user);
				//���û���¼�ɹ�����ת����ҳ
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				
				return;
			}
			
			request.setAttribute("message", "�û�����������󣡣�");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}catch(Exception e){
			request.setAttribute("message", "������δ֪���󣡣�");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}




	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}