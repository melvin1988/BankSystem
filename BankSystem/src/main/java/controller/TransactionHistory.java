package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;
import model.Transaction;

@SuppressWarnings("serial")
public class TransactionHistory extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get data from TransactionHistory.html
		//Get date in SQL format
		Date from = Date.valueOf(request.getParameter("fromDate"));
		Date to = Date.valueOf(request.getParameter("toDate"));   

		//Check that "from" is less than or equals to "to"
		if(from.compareTo(to)<=0) {
			Date fromDate = Date.valueOf(from.toLocalDate().plusDays(-1)); //subtract 1 day from fromDate
			Date toDate = Date.valueOf(to.toLocalDate().plusDays(1)); //add 1 day to toDate

			//Get data from session
			HttpSession session = request.getSession(true);
			String username = (String)session.getAttribute("username");

			Model m = new Model();
			ArrayList<Transaction> tList = new ArrayList<Transaction>();
			ArrayList<Transaction> newList = new ArrayList<Transaction>();

			//Set username in Model
			m.setUsername(username);

			//Obtain all required details from database and set in Model
			if(m.obtainCustomerDataUsingUsername()) {
				//Get the list of Transaction objects
				tList = m.getTransactionData();

				//If transaction history is not empty
				if(tList.size()>0) {
					for(int i=0; i<tList.size(); i++) {
						//Convert Timestamp to Date in order to compare
						Date transactionDate = Date.valueOf(tList.get(i).getTransactionDate().toLocalDateTime().toLocalDate());

						//Only get the transactions within the specified date range
						if(transactionDate.after(fromDate) && transactionDate.before(toDate)) {
							newList.add(tList.get(i));
						}
					}
					//If there are transaction records within the specified date range
					if(newList.size()>0) {
						session.setAttribute("tList", newList);
						response.sendRedirect("/BankSystem/TransactionHistorySuccess.jsp");
					}
					//No transaction records found
					else {
						response.sendRedirect("/BankSystem/TransactionHistoryEmpty.html");
					}
				}
				else {
					response.sendRedirect("/BankSystem/TransactionHistoryFailure.html");
				}
			}
		}
		else {
			response.sendRedirect("/BankSystem/InvalidDateRange.html");
		}
	}
}