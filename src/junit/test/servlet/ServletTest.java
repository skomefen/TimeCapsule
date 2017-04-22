package junit.test.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import sun.misc.BASE64Encoder;

public class ServletTest extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(this.getServletContext().getContextPath());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Test
	public void uuid(){
		System.out.println(new BASE64Encoder().encode(UUID.randomUUID().toString().getBytes()).length());
	}
}
