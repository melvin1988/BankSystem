<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transaction History Success</title>
<link href="style.css" type="text/css" rel="stylesheet">
</head>
<body>
<div>
    <nav>
      <label class="logo">iBank Singapore</label>
      <ul>
      	<li><a href="/BankSystem/LoginSuccess.jsp">Home</a></li>
        <li><a href="/BankSystem/ChangePasswordLinkSent">Change Password</a></li>
        <li><a href="/BankSystem/ApplyLoan.html">Apply Loan</a></li>
        <li><a href="/BankSystem/ViewBalance">View Balance</a></li>
        <li><a href="/BankSystem/Transfer.html">Transfer</a></li>
        <li><a href="/BankSystem/TransactionHistory.html">Transaction History</a></li>
        <li><a href="/BankSystem/Logout">Logout</a></li>
      </ul>
    </nav>
</div>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="model.Transaction"%>
<div class="solo-div">
	<button type="button" onclick="location.href='/BankSystem/GenerateReport.jsp'">Download</button>
	<table>
		<thead>
			<tr>
				<th>Balance</th>
				<th>Transaction</th>
				<th>Date</th>
			</tr>
		</thead>
		<tbody>
			<%
			@SuppressWarnings("unchecked")
			ArrayList<Transaction> list = (ArrayList<Transaction>) request.getSession().getAttribute("tList");
			for(Transaction t : list) {
				Date date = new Date(t.getTransactionDate().getTime()); //convert from Timestamp to Date
				String transactionDate = date.toString();
				%>
				<tr>
					<td>$<%out.print(String.format("%.2f", t.getBalance()));%></td>
					<td><%out.print(t.getTransactionType());%></td>
					<td><%out.print(transactionDate.substring(4,10));
					out.print(transactionDate.substring(23,28));%></td>
				</tr>
			<%}%>
		</tbody>
	</table>
</div>
</body>
</html>