package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Encryption;
import model.Model;

@SuppressWarnings("serial")
public class ChangePassword extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get data from ChangePassword.html
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		//Get username from session
		HttpSession session = request.getSession(true);
		String username = (String)session.getAttribute("username");

		int row = 0;

		//Verify that new password and confirm password are the same
		if(password1.equals(password2)) {
			Model m = new Model();
			Encryption encryption = new Encryption();
			m.setUsername(username);
			m.setPassword(encryption.encrypt(password1)); //encrypt password before setting in Model
			row = m.changePassword();
			if(row==1) {
				response.sendRedirect("/BankSystem/ChangePasswordSuccess.jsp");
			}
			else {
				response.sendRedirect("/BankSystem/ChangePasswordFailure.html");
			}
		}
		else {
			response.sendRedirect("/BankSystem/PasswordsDoNotMatch.html");
		}
	}
}