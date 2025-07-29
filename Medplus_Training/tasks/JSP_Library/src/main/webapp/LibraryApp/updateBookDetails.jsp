<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pojo.Book, pojo.Book.Status, service.BookService, service.exceptions.DatabaseException, 
service.exceptions.InvalidInputException" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Book Details</title>
</head>
<body>
	<h1>Update Book Details</h1>
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
	        	String newTitle = request.getParameter("newtitle");
	        	String newAuthor = request.getParameter("newauthor");
	        	String newCategory = request.getParameter("newcategory");
	        	String newStatusChar= request.getParameter("newstatus");
	        	existingBook.setTitle(newTitle);
	        	existingBook.setAuthor(newAuthor);
	        	existingBook.setCategory(newCategory);
	        	if (newStatusChar.equals("A"))	existingBook.setStatus(Status.ACTIVE);
	            else if (newStatusChar.equals("I"))	existingBook.setStatus(Status.INACTIVE);
	        	bookService.updateBookDetails(existingBook); %>
	        	<p style="color: green;">Book details updated successfully!</p>
	        <%}  %>       
		<% } catch (InvalidInputException | DatabaseException e) { %>
	        <p style="color: red;">Error updating book: <%= e.getMessage() %></p>
	    <% }
	}%>
	<form action="updateBookDetails.jsp" method="post">
        <label for="bookid">Book Id:</label><br>
        <input type="number" id="bookid" name="bookid" required><br><br>

		<label for="newtitle">New Title:</label><br>
        <input type="text" id="newtitle" name="newtitle" required><br><br>
        
        <label for="newauthor">New Author:</label><br>
        <input type="text" id="newauthor" name="newauthor" required><br><br>

        <label for="newcategory">New Category:</label><br>
        <input type="text" id="newcategory" name="newcategory" required><br><br>
        
        <label for="newstatus">New Status (A or I):</label><br>
        <input type="text" id="newstatus" name="newstatus" required maxlength="1" pattern="[AI]"><br><br>        

        <input type="submit" value="Update Book">
    </form>
    <p><a href="bookManage.jsp">Back to Previous Menu</a></p>
    <p><a href="index.jsp">Back to Main Menu</a></p>
</body>
</html>