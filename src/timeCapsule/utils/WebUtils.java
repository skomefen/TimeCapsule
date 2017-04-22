package timeCapsule.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.management.RuntimeErrorException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sun.misc.BASE64Encoder;
import timeCapsule.domain.UpCapsulefile;
import timeCapsule.exception.NoTypeException;
import timeCapsule.web.formbean.AddCapsuleForm;
import timeCapsule.web.formbean.Loadbar;

public class WebUtils {
	public static <T>T request2Bean(HttpServletRequest request,Class<T> beanClass){
		try{
			//1.创建要封装数据的bean
			T bean = beanClass.newInstance();
			
			//2.把request中的数据整到bean中
			
			Enumeration e = request.getParameterNames();
			while(e.hasMoreElements()){
				String name = (String)e.nextElement();
				String value = request.getParameter(name);
				BeanUtils.setProperty(bean, name, value);
			}
			return bean;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/*private String username;
	private String password;
	private String password2;
	private String email;
	private String nickname;*/
	
	/*private String id;
	private String username;
	private String password;
	private String email;
	private String nickname;*/
	
	public static void copyBean(Object src,Object dest){
		
		ConvertUtils.register(new Converter() {
			
			//注册日期转化器
			public Object convert(Class type, Object value) {
				if(value==null){
					return null;
				}
				String str = (String)value;
				if(str.trim().equals("")){
					return null;
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try {
					return df.parse(str);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
			
			
		}, Date.class);
		
			try {
				BeanUtils.copyProperties(dest, src);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		
	}
	
	/**
	 * 存放时间胶囊到服务器本地
	 * 
	 * */
	public static AddCapsuleForm doUploadCapsuleForm(HttpServletRequest request,String uppath,ServletFileUpload upload,List<String> types) throws NoTypeException,FileUploadBase.FileSizeLimitExceededException{
		
		AddCapsuleForm bean = new AddCapsuleForm();
	    List<UpCapsulefile> upfiles = new ArrayList();
		
		try {
			
			upload.setHeaderEncoding("UTF-8");
			upload.setFileSizeMax(1000*1000*50);
			
			
			List<FileItem> list = upload.parseRequest(request);
			
			for(FileItem item:list){
				
				if(item.isFormField()){
					//普通输入项
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					
					BeanUtils.setProperty(bean, name, value);
					

				}else{
					//上传文件
					//得到上传文件名
					String filename = item.getName().substring(item.getName().lastIndexOf("\\")+1);
					//得到文件的保存名称
					String uuidname = generateFilename(filename);
					//得到文件的保存路径
					String savepath = generateSavePath(uppath, filename);
					
					String ext = filename.substring(filename.lastIndexOf(".")+1);
					
					if(!types.contains(ext)){
						throw new NoTypeException(); 
					}
					
					InputStream in = null;
					FileOutputStream out = null;
					try{
						in = item.getInputStream();
						int len = 0;
						byte buffer[] = new byte[1024];
						out = new FileOutputStream(savepath+File.separator+uuidname);
						while((len=in.read(buffer))>0){
							out.write(buffer, 0, len);
						}
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
							in=null;
						}
						item.delete();
					}
					UpCapsulefile upfile = new UpCapsulefile();
					upfile.setFilename(filename);
					upfile.setId(UUID.randomUUID().toString());
					upfile.setSavepath(savepath);
					upfile.setUuidname(uuidname);
					upfile.setUptime(new Date());
					upfiles.add(upfile);
				}
			}
			bean.setUpfiles(upfiles);
			return bean;

		}catch (NoTypeException e) {
			throw e;
		}catch (FileUploadBase.FileSizeLimitExceededException e) {
			throw e;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String generateFilename(String filename){
		
		return UUID.randomUUID().toString() + "_"+ filename;
	}
	
	private static String generateSavePath(String path,String filename){
		int hashcode = filename.hashCode();
		int dir1 = hashcode&15;
		int dir2 = (hashcode>>4)&0xf;
		
		String savepath = path + File.separator + dir1 + File.separator + dir2;
		File file = new File(savepath);
		if(!file.exists()){
			file.mkdirs();
		}
		return savepath;
	}
	
	 /**
     *进度条
     */
	public static void loadbar(HttpServletRequest request, ServletFileUpload upload) {
		Loadbar loadbar = new Loadbar();
		loadbar.setRequest(request);
		upload.setProgressListener(loadbar);
	}

	
	/**
	 *产生全球唯一id 
	*/
	public static String generateID(){
		
		return UUID.randomUUID().toString();
		
	}

	public static void listcapsulefiles(List<UpCapsulefile> files, Map map) {
		// TODO Auto-generated method stub
		Iterator<UpCapsulefile> it = files.iterator();
		while(it.hasNext()){
			UpCapsulefile ucf = it.next(); 
			String uuidname = ucf.getUuidname();
			String filename = ucf.getFilename();
			map.put(uuidname, filename);
		}
	}
	/**
	 * 保存硬盘文件名得到地址
	 * */
	public static String getpath(String filename){
		String realname = filename.substring(filename.lastIndexOf("_")+1);
		int hashcode = realname.hashCode();  //121221
		int dir1 = hashcode&15;
		int dir2 = (hashcode>>4)&0xf;

		return dir1 + File.separator + dir2;  //   3/5
	}
	
	public static String autoLoginMD5(String key, String password, long expirestime,
			String username) {
		try{
			String value = key+":" + password + ":" + expirestime +":"+ username;
			MessageDigest md = MessageDigest.getInstance("md5");
			byte md5[] = md.digest(value.getBytes());
			BASE64Encoder encode = new BASE64Encoder();
			return encode.encode(md5);
		}catch(Exception e){
			throw new RuntimeException();
		}
	}
	
	public static Cookie makeCookie(String username, String password, String path,String key,
			int validtime) {
		long expirestime = System.currentTimeMillis()+ validtime*1000;
		String value = username + ":" + expirestime + ":" + WebUtils.autoLoginMD5(key, password, expirestime, username);
		Cookie cookie = new Cookie("autologin", value);
		cookie.setMaxAge(validtime);
		cookie.setPath(path);
		return cookie;
	}
}
