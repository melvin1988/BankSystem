package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

@SuppressWarnings("serial")
public class ViewBalance extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get data from session
		HttpSession session = request.getSession(true);
		String username = (String)session.getAttribute("username");

		//Set data in Model
		Model m = new Model();
		m.setUsername(username);

		//Obtain all required details from database and set in Model
		if(m.obtainCustomerDataUsingUsername()) {
			String balance = String.format("%.2f", m.getBalance()); //format String to 2 decimal places before setting in session
			session.setAttribute("balance", balance);
			response.sendRedirect("/BankSystem/ViewBalanceSuccess.jsp");
		}
		else {
			response.sendRedirect("/BankSystem/ViewBalanceFailure.html");
		}
	}
}