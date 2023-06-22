package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

@SuppressWarnings("serial")
public class ApplyLoan extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get data from ApplyLoan.html
		String transactionType =  "Loan($"+request.getParameter("transactionType")+")";

		//Set transactionDate to the current date and time
		Timestamp transactionDate = new Timestamp(new Date().getTime());

		//Get data from session
		HttpSession session = request.getSession(true);
		String username = (String)session.getAttribute("username");

		//Set data in Model
		Model m = new Model();
		m.setUsername(username);
		m.setTransactionType(transactionType);
		m.setTransactionDate(transactionDate);

		int row = 0;

		//Obtain all required details from database and set in Model
		if(m.obtainCustomerDataUsingUsername()) {
			//Add the transaction
			row = m.createLoanTransaction();
		}

		if(row==1) {
			response.sendRedirect("/BankSystem/ApplyLoanSuccess.jsp");
		}
		else {
			response.sendRedirect("/BankSystem/ApplyLoanFailure.html");
		}
	}
}