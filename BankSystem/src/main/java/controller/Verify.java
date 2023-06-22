package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

public class Verify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get data from RegistrationSuccess.jsp
		String verificationCode = request.getParameter("verificationCode");

		//Get data from session
		HttpSession session = request.getSession(true);
		String username = (String)session.getAttribute("username");

		Model m = new Model();
		m.setUsername(username);
		m.setVerificationCode(verificationCode);
		
		int x = m.verifyCode();
		
		if(x==1) {
			response.sendRedirect("/BankSystem/VerificationSuccess.jsp");
		}
		else if(x==0) {
			response.sendRedirect("/BankSystem/InvalidCode.html");
		}
		else {
			m.deleteCustomer();
			response.sendRedirect("/BankSystem/TimeLapsed.html");
		}
	}
}
