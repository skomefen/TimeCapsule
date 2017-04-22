package timeCapsule.web.filter;

import java.io.IOException;
import java.security.MessageDigest;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;
import timeCapsule.domain.User;
import timeCapsule.factory.ConfigFactory;
import timeCapsule.service.UserBusinessService;
import timeCapsule.service.impl.UserBusinessServiceImpl;
import timeCapsule.utils.WebUtils;


public class AutoLoginFilter implements Filter{
	

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		//1.�ȼ���û��Ƿ��ѵ�½��û��½���Զ���¼
		
		ConfigFactory config = ConfigFactory.getInstance();
		String key = config.getKey();

		User user = (User) request.getSession().getAttribute("user");
		if(user!=null){
			
			int validtime =config.getLoginexpirestime();
			Cookie cookie = WebUtils.makeCookie(user.getUsername(), user.getPassword(), request.getContextPath(), key, validtime);
			response.addCookie(cookie);
			
			chain.doFilter(request, response);
			return;
		}
		
		//2.û��½����ִ���Զ���¼�߼�
		
		//���û��Ƿ���Զ���¼��cookie
		
		Cookie autoLoginCookie = null;
		Cookie cookies[] = request.getCookies();
		for(int i=0;cookies!=null&&i<cookies.length;i++){
			if(cookies[i].getName().equals("autologin")){
				autoLoginCookie = cookies[i];
			}
		}
		if(autoLoginCookie==null){
			chain.doFilter(request, response);
			return;
		}
		
		//�û������Զ���¼cookie���cookie����Ч��
//		cookieֵ��Ϊautologin=username:expirestime:md5(state:password:expirestime:username)
//		key:isOK
		String values[] = autoLoginCookie.getValue().split("\\:");
		if(values.length!=3){
			chain.doFilter(request, response);
			return;
		}
		long expirestime = Long.parseLong(values[1]);
		if(System.currentTimeMillis()>expirestime){
			chain.doFilter(request, response);
			return;
		}
		
		//��Ч��û�����cookie��Ч��
		String username = values[0];
		String client_md5 = values[2];
			
		UserBusinessService service = new UserBusinessServiceImpl();
		user = service.findbyUsername(username);
		if(user==null){
			chain.doFilter(request, response);
			return;
		}
		
				
		String server_md5 = WebUtils.autoLoginMD5(key,user.getPassword(),expirestime,user.getUsername());
		
		if(!server_md5.equals(client_md5) ){
			chain.doFilter(request, response);
			return;
		}
		
		request.getSession().setAttribute("user", user);
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}


	public void destroy() {
		
	}

	
}
