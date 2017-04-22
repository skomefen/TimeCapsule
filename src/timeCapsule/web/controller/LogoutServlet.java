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
		
		//ע���ɹ���������Ϣȫ����Ϣ��ʾҳ�棬��ʾע���ɹ���Ϣ����������Ϣ��ʾҳ���3�����ת����ҳ
		
		request.setAttribute("message","ע���ɹ������3�����ת����ҳ<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/index.jsp'>");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}