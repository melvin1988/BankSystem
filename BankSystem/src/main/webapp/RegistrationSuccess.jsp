<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration Success</title>
<link href="style.css" type="text/css" rel="stylesheet">
</head>
<body>
<div>
    <nav>
      <label class="logo">iBank Singapore</label>
    </nav>
</div>
<div class="mid-div">
	<img class="success" src="greentick.gif" alt="Green tick">
	<h3>Hello <%out.print(session.getAttribute("name"));%>, you are now registered<br>
	An email verification code have been sent to <%out.print(session.getAttribute("email"));%></h3>
</div>
<div class="bottom-div">
	<div class="form-container">
		<form action="/BankSystem/Verify">
			<ul class="list">
	            <li><h2>Email Verification</h2></li>
	            <li><input type="text" name="verificationCode" placeholder="Verification code" required></li>
	            <li><input type="submit" value="Verify"></li>
	        </ul>
		</form>
	</div>
</div>
</body>
</html>