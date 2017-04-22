package junit.test.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterTest extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String value = request.getParameter("banword");
		request.setAttribute("banword", value);
		request.getRequestDispatcher("/WEB-INF/jsp/test/filtertest.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void test(){
		Pattern p = Pattern.compile("²Ü+²Ù");
		Matcher m = p.matcher("²Ü²Ü²ÙÄãºÃ²Ü²Ù");

		
		while( m.find()){
			System.out.println(m.group());
		}
	}

}
