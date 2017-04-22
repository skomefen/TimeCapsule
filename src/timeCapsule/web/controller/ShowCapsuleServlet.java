package timeCapsule.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import timeCapsule.domain.Capsule;
import timeCapsule.domain.UpCapsulefile;
import timeCapsule.domain.User;
import timeCapsule.factory.ServiceFactory;
import timeCapsule.service.CapsuleBusinessService;
import timeCapsule.service.UserBusinessService;
import timeCapsule.service.impl.CapsuleBusinessServiceImpl;
import timeCapsule.utils.WebUtils;

public class ShowCapsuleServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try{
			String id = request.getParameter("id");
			
			User userbySession = (User) request.getSession().getAttribute("user");
			ServiceFactory sf = ServiceFactory.getInstance();
			CapsuleBusinessService cbs = sf.createService(CapsuleBusinessService.class, userbySession);
			
			
			Capsule capsule = cbs.fineCapsule(id);
			if(capsule==null){
				request.setAttribute("message", "没有这个胶囊哦<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/FindCapsuleServlet'");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}
			if(new Date().getTime()<capsule.getReaddate().getTime()){
				request.setAttribute("message", "没到时间可以挖哦<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/FindCapsuleServlet'");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}
			capsule.setIsreaded(true);
			cbs.updateCapsule(capsule);
			List<UpCapsulefile> files =  cbs.findCapulefile(id);
			Map map = new HashMap();
			WebUtils.listcapsulefiles(files,map);
			request.setAttribute("files",map);
			request.setAttribute("capsule", capsule);
			request.getRequestDispatcher("/WEB-INF/jsp/capsule/showcapsule.jsp").forward(request, response);
		}catch(Exception e){
			request.setAttribute("message", "服务器未知错误<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/FindCapsuleServlet'");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

}
