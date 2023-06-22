<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Logout Success</title>
<link href="style.css" type="text/css" rel="stylesheet">
</head>
<body>
<div>
    <nav>
      <label class="logo">iBank Singapore</label>
    </nav>
</div>
<div class="mid-div">
	<h3>Thank you for visiting iBank Singapore<br>You are now logged out</h3>
</div>
<div class="bottom-div">
	<div class="form-container">
		<form action="/BankSystem/Login">
			<ul class="list">
	            <li><h2>Login</h2></li>
	            <li><input type="text" name="username" placeholder="Username" required></li>
	            <li><input type="password" name="password" placeholder="Password" required></li>
	            <li><input type="submit" value="Login"></li>
	            <li id="forget">
		            <a href="/BankSystem/Forget.html">Forget username or password?</a><br>
		            <a href="/BankSystem/Register.html">Don't have an account?</a>
	            </li>
	        </ul>
		</form>
	</div>
</div>
</body>
</html>