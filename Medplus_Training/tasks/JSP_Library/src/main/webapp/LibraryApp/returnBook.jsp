<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="service.IssueService, service.exceptions.DatabaseException, 
service.exceptions.InvalidInputException, service.exceptions.LogicalError" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Return Book</title>
</head>
<body>
	<h1>Return Book</h1>
	<%
	if ("POST".equalsIgnoreCase(request.getMethod())){
		int issueId=Integer.parseInt(request.getParameter("issueid"));
		int bookId=Integer.parseInt(request.getParameter("bookid"));
		
		   try {
			   IssueService issueService = new IssueService();
			   issueService.returnBook(issueId, bookId); %>
		   	<p style="color: green;">Book returned successfully!</p>
		   <% } catch (InvalidInputException | LogicalError | DatabaseException e) { %>
		       <p style="color: red;">Error returning book: <%= e.getMessage() %></p>
		   <% }
	}%>
	<form action="returnBook.jsp" method="post">
		<label for="issueid">Issue Record ID:</label><br>
        <input type="number" id="issueid" name="issueid" required><br><br>
        
        <label for="bookid">Book ID related to this issue:</label><br>
        <input type="number" id="bookid" name="bookid" required><br><br>

        <input type="submit" value="Return Book">
    </form>
    <p><a href="issueReturn.jsp">Back to Previous Menu</a></p>
    <p><a href="index.jsp">Back to Main Menu</a></p>
</body>
</html>