package timeCapsule.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GzipFilter implements Filter{
	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		try{
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			
			MyResponse myresponse = new MyResponse(response);
			chain.doFilter(request,myresponse);
			
			byte out[] = myresponse.getBuffer();
			byte gzipout[] = gzip(out);

			response.setHeader("content-encoding", "gzip");
			response.setHeader("content-length", gzipout.length+"");
			response.getOutputStream().write(gzipout);
			
			

		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	
	private byte[] gzip(byte[] b) throws IOException {
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gout = new GZIPOutputStream(bout);
		
		gout.write(b);
		gout.close();
		
		return bout.toByteArray();
	}



	class MyResponse extends HttpServletResponseWrapper{

		private HttpServletResponse response;
		private PrintWriter pw;
		private ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		
		public MyResponse(HttpServletResponse response) {
			super(response);
			this.response = response;
		}

		public byte[] getBuffer() {
			if(pw!=null){
				pw.close();
			}
			
			if(bout==null){
				return null;
			}
			
			return bout.toByteArray();
			
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return new MyServletOutputStream(bout);
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			pw = new PrintWriter(new OutputStreamWriter(bout,response.getCharacterEncoding()));
			return pw;
		}
		
		
		
	}
	
	class MyServletOutputStream extends ServletOutputStream{
		
		private ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		public MyServletOutputStream(ByteArrayOutputStream bout) {
			this.bout = bout;
		}

		public void write(int b) throws IOException {
			bout.write(b);
		}
		
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}



	public void destroy() {
		
	}
	
}
