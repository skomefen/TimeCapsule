package timeCapsule.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession seeion = request.getSession(false);
		if(seeion!=null){
			seeion.removeAttribute("user");
		}
				
		Cookie cookie = new Cookie("autologin", "noOK");
		cookie.setMaxAge(0);
		cookie.setPath(this.getServletContext().getContextPath());
		response.addCookie(cookie);
		
		//注销成功，跳到消息全局消息显示页面，显示注销成功消息，并控制消息显示页面过3秒后跳转到首页
		
		request.setAttribute("message","注销成功浏览器3秒后跳转到首页<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/index.jsp'>");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}