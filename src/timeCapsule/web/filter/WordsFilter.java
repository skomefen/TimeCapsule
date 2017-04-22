package timeCapsule.web.filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class WordsFilter implements Filter {

	private List<String> banWords = new ArrayList<>(); //禁止
	private List<String> auditWords = new ArrayList<>(); //审计
	private List<String> replaceWords = new ArrayList<>(); //替换
	
	public void init(FilterConfig filterConfig) throws ServletException {
		try{
			String path = WordsFilter.class.getClassLoader().getResource("/timeCapsule/words").getPath();
			File files[] = new File(path).listFiles();
			for(File file:files){
				if(!file.getName().endsWith(".txt")){
					continue;
				}
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = null;
				while((line=br.readLine())!=null){
					String[] s =line.split("\\|");
					if(s.length!=2){
						continue;
					}
					if(s[1].trim().equals("1")){
						banWords.add(s[0].trim());
					}
					if(s[1].trim().equals("2")){
						auditWords.add(s[0].trim());
					}
					if(s[1].trim().equals("3")){
						replaceWords.add(s[0].trim());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		Enumeration<String> e = request.getParameterNames();
		while(e.hasMoreElements()){
			String name = e.nextElement();
			String data = request.getParameter(name);
			for(String regex : banWords){
				Pattern patten = Pattern.compile(regex);
				Matcher m = patten.matcher(data);
				if(m.find()){
					request.setAttribute("message", "文章中包含非法词汇，检查后再提交");
					request.getRequestDispatcher("/message.jsp").forward(request, response);
					return;
				}
			}
		}
		
		chain.doFilter(new MyRequest(request), response);
	}
	
	class MyRequest extends HttpServletRequestWrapper{

		private HttpServletRequest request;
		
		public MyRequest(HttpServletRequest request) {
			super(request);
			
			this.request = request;
			
		}

		@Override
		public String getParameter(String name) {
			
			String data = this.request.getParameter(name);
			if(data==null){
				return null;
			}
			
			for(String regex:auditWords){
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(data);
				while(m.find()){
					String value = m.group();
					data = data.replaceAll(value, "<font color='red'>"+value+"</font>");
				}
			}
			
			for(String regex:replaceWords){
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(data);
				while(m.find()){
					String value = m.group();
					String newvalue="";
					for(int i = 0;i<value.length();i++){
						newvalue+="*";
					}
					data = data.replaceAll(value, newvalue);
				}
			}
			
			return data;
		}
		
		
		
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
