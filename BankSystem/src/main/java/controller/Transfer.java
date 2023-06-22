package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

@SuppressWarnings("serial")
public class Transfer extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get data from session
		HttpSession session = request.getSession(true);
		String username = (String)session.getAttribute("username");

		//Get data from Transfer.html
		String recipientUsername = request.getParameter("recipientUsername");
		double debitAmount = Double.parseDouble(request.getParameter("transactionType"));
		String transactionType = "Debit($"+request.getParameter("transactionType")+") "+recipientUsername;
		String recipientTransactionType = "Credit($"+request.getParameter("transactionType")+") "+username;

		//Set transactionDate to the current date and time
		Timestamp transactionDate = new Timestamp(new Date().getTime());

		//Set data to session
		session.setAttribute("transactionType", String.format("%.2f", debitAmount));
		session.setAttribute("recipientUsername", recipientUsername);

		boolean balanceUpdated = false;
		boolean customerTransactionUpdated = false;
		boolean recipientTransactionUpdated = false;

		/*=================================================================================
		Update customer and recipient balance
		===================================================================================*/
		int row = 0;
		Model m = new Model();
		Model customerM = new Model();
		Model recipientM = new Model();
		ArrayList<Model> mList = new ArrayList<Model>();

		//Set username in Model
		customerM.setUsername(username);
		recipientM.setUsername(recipientUsername);

		//Customer and recipient cannot be the same person
		//Obtain all required details from database and set in Model
		if(!username.equals(recipientUsername) && customerM.obtainCustomerDataUsingUsername() && recipientM.obtainCustomerDataUsingUsername()) {
			//Calculate the new balance for customer and recipient
			double customerBalance = customerM.getBalance() - debitAmount;
			double recipientBalance = recipientM.getBalance() + debitAmount;

			//Check that customerBalance is not negative
			if(customerBalance>=0) {
				//Set balance in Model
				customerM.setBalance(customerBalance);
				recipientM.setBalance(recipientBalance);
				mList.add(customerM);
				mList.add(recipientM);
				row = m.updateBalance(mList); //return 2 if both customer and recipient updated
				if(row==2) {
					balanceUpdated = true;
				}
				else {
					response.sendRedirect("/BankSystem/BalanceUpdateFailure.html");
				}
			}
		}

		if(balanceUpdated) {
			/*==============================================================================
			Update customer transaction
			================================================================================*/
			row = 0;
			customerM.setTransactionType(transactionType);
			customerM.setTransactionDate(transactionDate);
			row = customerM.createTransferTransaction();

			if(row==1) {
				customerTransactionUpdated = true;
			}
			else {
				response.sendRedirect("/BankSystem/TransactionUpdateFailure");
			}

			/*==============================================================================
			Update recipient transaction
			================================================================================*/
			row=0;
			recipientM.setTransactionType(recipientTransactionType);
			recipientM.setTransactionDate(transactionDate);
			row = recipientM.createTransferTransaction();

			if(row==1) {
				recipientTransactionUpdated = true;
			}
			else {
				response.sendRedirect("/BankSystem/TransactionUpdateFailure");
			}
		}

		if(customerTransactionUpdated && recipientTransactionUpdated) {
			response.sendRedirect("/BankSystem/TransferSuccess.jsp");
		}
		else {
			response.sendRedirect("/BankSystem/TransferFailure.html");
		}
	}
}