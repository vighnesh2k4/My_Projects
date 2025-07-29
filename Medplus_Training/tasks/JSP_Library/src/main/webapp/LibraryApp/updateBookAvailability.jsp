<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pojo.Book, pojo.Book.Availability, service.BookService, service.exceptions.DatabaseException, 
service.exceptions.InvalidInputException" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Book Availability</title>
</head>
<body>
	<h1>Update Book Availability</h1>
	<%
	if ("POST".equalsIgnoreCase(request.getMethod())){
		int bookId=Integer.parseInt(request.getParameter("bookid"));
		try {
			BookService bookService = new BookService(); 
	        Book existingBook = bookService.getBookById(bookId);
	        if (existingBook == null) { %>
	        	<p style="color: red;"> Book with ID <%=bookId%> not found. </p>
	       <% }
	        else{
	        	String availabilityChar= request.getParameter("newavailability");
	        	Availability newAvailability = null;
	            if (availabilityChar.equals("A")) {
	                newAvailability = Availability.AVAILABLE;
	            } else if (availabilityChar.equals("I")) {
	                newAvailability = Availability.ISSUED;
	            } 
	            bookService.updateBookAvailability(bookId, newAvailability);
	            %>
	        	<p style="color: green;">Book availability updated successfully!</p>
	        <%}  %>       
		<% } catch (InvalidInputException | DatabaseException e) { %>
	        <p style="color: red;">Error updating book availability: <%= e.getMessage() %></p>
	    <% }
	}%>
	<form action="updateBookAvailability.jsp" method="post">
        <label for="bookid">Book Id:</label><br>
        <input type="number" id="bookid" name="bookid" required><br><br>
        
        <label for="newavailability">New Availability (A or I):</label><br>
        <input type="text" id="newavailability" name="newavailability" required maxlength="1" pattern="[AI]"><br><br>        

        <input type="submit" value="Update Book Availability">
    </form>
    <p><a href="bookManage.jsp">Back to Previous Menu</a></p>
    <p><a href="index.jsp">Back to Main Menu</a></p>
</body>
</html>