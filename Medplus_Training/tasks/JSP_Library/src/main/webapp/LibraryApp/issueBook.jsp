<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="service.IssueService, service.exceptions.DatabaseException, 
service.exceptions.InvalidInputException, service.exceptions.LogicalError" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Issue Book</title>
</head>
<body>
	<h1>Issue Book</h1>
	<%
	if ("POST".equalsIgnoreCase(request.getMethod())){
		int bookId=Integer.parseInt(request.getParameter("bookid"));
		int memberId=Integer.parseInt(request.getParameter("memberid"));
		
		   try {
			   IssueService issueService = new IssueService();
			   issueService.issueBook(bookId, memberId); %>
		   	<p style="color: green;">Book issued successfully!</p>
		   <% } catch (InvalidInputException | LogicalError | DatabaseException e) { %>
		       <p style="color: red;">Error issuing book: <%= e.getMessage() %></p>
		   <% }
	}%>
	<form action="issueBook.jsp" method="post">
		<label for="bookid">Book Id:</label><br>
        <input type="number" id="bookid" name="bookid" required><br><br>
        
        <label for="memberid">Member Id:</label><br>
        <input type="number" id="memberid" name="memberid" required><br><br>

        <input type="submit" value="Issue Book">
    </form>
    <p><a href="issueReturn.jsp">Back to Previous Menu</a></p>
    <p><a href="index.jsp">Back to Main Menu</a></p>
</body>
</html>