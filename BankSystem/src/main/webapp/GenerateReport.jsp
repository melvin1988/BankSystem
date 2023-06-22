<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Generate Report</title>
</head>
<body>
<%@page import="java.util.ArrayList"%>
<%@ page import="model.Transaction"%>
<%@ page import="java.util.Date"%>
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
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline; filename=Transaction_History.xls");

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
</body>
</html>