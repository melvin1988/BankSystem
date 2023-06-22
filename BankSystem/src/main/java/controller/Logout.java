package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class Logout extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Set session to false
		HttpSession session = request.getSession(false);

		//Remove all data from session
		if(session!=null) {
			session.invalidate();
			response.sendRedirect("/BankSystem/LogoutSuccess.jsp");
		}
		else {
			response.sendRedirect("/BankSystem/RestrictedAccess");
		}
	}
}