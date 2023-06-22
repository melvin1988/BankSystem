<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Success</title>
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
<div class="top-div">
	<h4>Hello <%out.print(session.getAttribute("name"));%>,<br>what would you like to do today?</h4>
	<img class="hello" src="hello.png" alt="Hello">
</div>
</body>
</html>