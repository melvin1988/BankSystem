package controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Email;
import model.Encryption;
import model.Model;

@SuppressWarnings("serial")
public class Forget extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get data from ForgetUsername.html
		String email = request.getParameter("email");

		//Set data in Model
		Model m = new Model();
		m.setEmail(email);

		//Obtain all required details from database and set in Model
		if(m.obtainCustomerDataUsingEmail()) {
			Encryption encryption = new Encryption();

			//Get data from Model
			String name = m.getName();
			String username = m.getUsername();
			String password = encryption.decrypt(m.getPassword());

			//Set all required data in session
			HttpSession session = request.getSession(true);
			session.setAttribute("email", email);

			try {
				//Send the email with verification code to the customer
				Email mail = new Email(name, email);
				mail.setRecipientUsername(username); //used in sendEmailForForget()
				mail.setRecipientPassword(password); //used in sendEmailForForget()
				mail.sendEmailForForget();
				response.sendRedirect("/BankSystem/ForgetSuccess.jsp");
			}
			catch(MessagingException e) {
				e.printStackTrace();
			}
		}
		else {
			response.sendRedirect("/BankSystem/ForgetFailure.html");
		}
	}
}