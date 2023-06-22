package controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Email;

@SuppressWarnings("serial")
public class ChangePasswordLinkSent extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get data from session
		HttpSession session = request.getSession(true);
		String name = (String)session.getAttribute("name");
		String email = (String)session.getAttribute("email");

		try {
			//Send email
			Email mail = new Email(name, email);
			mail.sendEmailForChangePassword();
			response.sendRedirect("/BankSystem/ChangePasswordLinkSent.html");
		}
		catch(AddressException e) {
			e.printStackTrace();
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}