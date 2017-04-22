package timeCapsule.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import timeCapsule.utils.WebUtils;

public class DownLoadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FileInputStream in =null;
		OutputStream out = null;
		try{
			//得到要下载的文件名  uuid
			String filename = request.getParameter("filename");
			filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
			
			//找出这个文件  url    c:\\
			String path = this.getServletContext().getRealPath("/WEB-INF/upload") + File.separator + WebUtils.getpath(filename);
			
			File file = new File(path + File.separator + filename);
			if(!file.exists()){
				request.setAttribute("message", "对不起，您要下载的资源已被删除");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}
			
			//得到文件的原始文件名
			String oldname = file.getName().substring(file.getName().indexOf("_")+1);  
			
			//通知浏览器以下载方式打开下面发送的数据
			response.setHeader("content-disposition", "attachment;filename=" + new String(oldname.getBytes("UTF-8"),"ISO8859-1"));
			
			in = new FileInputStream(file);
			int len = 0;
			byte buffer[] = new byte[1024];
			out = response.getOutputStream();
			while((len=in.read(buffer))>0){
				out.write(buffer, 0, len);
			}
			in.close();
		}catch(Exception e){
			request.setAttribute("message", "服务器未知错误<meta http-equiv='refresh' content='3;url="+request.getContextPath()+"/AddCapsuleServlet'");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}finally{
			if(in!=null){
				try{
					in.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				in=null;
			}
			
			if(out!=null){
				try{
					out.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				out=null;
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	


}
