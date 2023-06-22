package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

@SuppressWarnings("serial")
public class Login extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get data from Login.html
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		//Set data in Model
		Model m = new Model();
		m.setUsername(username);
		m.setPassword(password);

		//Verify customer login
		int x = m.validateLogin();

		//Obtain all required details from database and set in Model
		if(m.obtainCustomerDataUsingUsername()) {
			//Set all required data in session
			HttpSession session = request.getSession(true);
			session.setAttribute("name", m.getName());
			session.setAttribute("username", username);
			session.setAttribute("email", m.getEmail());
			session.setAttribute("balance", m.getBalance());
		}

		if(x==1) {
			response.sendRedirect("/BankSystem/LoginSuccess.jsp");
		}
		else if(x==0) {
			response.sendRedirect("/BankSystem/InvalidPassword.html");
		}
		else {
			response.sendRedirect("/BankSystem/InvalidUsername.html");
		}
	}
}