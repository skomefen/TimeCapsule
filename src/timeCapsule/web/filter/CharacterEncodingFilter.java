package timeCapsule.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class CharacterEncodingFilter implements Filter {
	
	private FilterConfig config;
	private String defaultCharset = "UTF-8";
	
	public void doFilter(ServletRequest req, ServletResponse res,
		FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String charset = this.config.getInitParameter("charset");
		if(charset==null){
			charset = defaultCharset;
		}
		request.setCharacterEncoding(charset);
		response.setCharacterEncoding(charset);
		response.setContentType("text/html;charset="+charset);
		
		chain.doFilter(request, response);

		
	}
	
//	class MyRequest extends HttpServletRequestWrapper{
//
//
//		private HttpServletRequest request;
//		public MyRequest(HttpServletRequest request) {
//			super(request);
//			this.request = request;
//		}
//		@Override
//		public String getParameter(String name) {
//			
//			String value = this.request.getParameter(name);
//			if(!request.getMethod().equalsIgnoreCase("get")){
//				return value;
//			}
//			
//			if(value==null){
//				return null;
//			}
//			
//			try {
//				return value = new String(value.getBytes("iso8859-1"),request.getCharacterEncoding());
//			} catch (UnsupportedEncodingException e) {
//				throw new RuntimeException(e);
//			}
//			
//		}
//		
//		
//	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

	

	public void destroy() {
		
	}

}
