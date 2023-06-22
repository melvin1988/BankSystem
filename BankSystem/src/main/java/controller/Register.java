package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

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
public class Register extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get data from Register.html
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		//Set data in Model
		Model m = new Model();
		m.setName(name);
		m.setUsername(username);
		Encryption encryption = new Encryption();
		m.setPassword(encryption.encrypt(password)); //encrypt password before setting it
		m.setEmail(email);

		//If username does not exist in the database
		if(!m.usernameExists()) {
			Email mail = new Email(name, email);
			//Generate and set the verification code
			String verificationCode = mail.generateVerificationCode();
			int row = 0;

			try {
				//Send the email with verification code to the customer
				mail.sendEmailForRegistration();

				//Set the remaining data in Model
				m.setVerificationCode(verificationCode);
				Timestamp verificationTime = new Timestamp(new Date().getTime()); //get the date and time the email was sent
				m.setVerificationTime(verificationTime);

				//Register the customer
				row = m.createCustomer();
			}
			catch(MessagingException e) {
				e.printStackTrace();
			}

			if(row==1) {
				//Set data in session
				HttpSession session = request.getSession(true);
				session.setAttribute("name", name);
				session.setAttribute("username", username); //used in Verify.java
				session.setAttribute("email", email);

				response.sendRedirect("/BankSystem/RegistrationSuccess.jsp");
			}
			else {
				response.sendRedirect("/BankSystem/RegistrationFailure.html");
			}
		}
		else {
			response.sendRedirect("/BankSystem/UsernameExists.html");
		}
	}
}