package timeCapsule.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import timeCapsule.domain.Capsule;
import timeCapsule.domain.UpCapsulefile;
import timeCapsule.domain.User;
import timeCapsule.exception.NoTypeException;
import timeCapsule.factory.ServiceFactory;
import timeCapsule.service.CapsuleBusinessService;
import timeCapsule.service.impl.CapsuleBusinessServiceImpl;
import timeCapsule.utils.WebUtils;
import timeCapsule.web.formbean.AddCapsuleForm;
import timeCapsule.web.formbean.Loadbar;

public class AddCapsuleServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/jsp/capsule/addcapsule.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
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
				request.getRequestDispatcher("/WEB-INF/jsp/capsule/addcapsule.jsp").forward(request, response);
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
			CapsuleBusinessService cbs = sf.createService(CapsuleBusinessService.class, user);
			
			cbs.add(capsule, upfiles);
			
			request.setAttribute("message", "��Ľ���������<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/TreeHoleServlet'");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			form.getErrors().put("upfiles", "�ļ���С���ܳ���50m");
			request.setAttribute("form", form);
			request.getRequestDispatcher("/WEB-INF/jsp/capsule/addcapsule.jsp").forward(request, response);
		} catch(NoTypeException e){
			form.getErrors().put("upfiles", "��֧�ֵ��ļ�����");
			request.setAttribute("form", form);
			request.getRequestDispatcher("/WEB-INF/jsp/capsule/addcapsule.jsp").forward(request, response);
		}catch(Exception e){
			request.setAttribute("message", "������δ֪����");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	
}