<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transfer Success</title>
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
<div class="solo-div">
	<img class="success" src="greentick.gif" alt="Green tick">
	<h3>$<%out.print(session.getAttribute("transactionType"));%> has been transferred to <%out.print(session.getAttribute("recipientUsername"));%></h3>
</div>
</body>
</html>