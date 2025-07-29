<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pojo.Book, pojo.Book.Availability, pojo.Book.Status, service.BookService, service.exceptions.DatabaseException, 
service.exceptions.InvalidInputException" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add New Book</title>
</head>
<body>
	<h1>Add New Book</h1>
	<%
	if ("POST".equalsIgnoreCase(request.getMethod())){
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String category = request.getParameter("category");
		   Book newBook = new Book();
		   newBook.setTitle(title);
		   newBook.setAuthor(author);
		   newBook.setCategory(category);
		   newBook.setStatus(Status.ACTIVE);      
		   newBook.setAvailability(Availability.AVAILABLE); 
		   BookService bookService = new BookService(); 
		   try {
		       bookService.addBook(newBook); %>
		   	<p style="color: green;">Book added successfully!</p>
		   <% } catch (InvalidInputException | DatabaseException e) { %>
		       <p style="color: red;">Error adding book: <%= e.getMessage() %></p>
		   <% }
	}%>
	<form action="addNewBook.jsp" method="post">
        <label for="title">Title:</label><br>
        <input type="text" id="title" name="title" required><br><br>

        <label for="author">Author:</label><br>
        <input type="text" id="author" name="author" required><br><br>

        <label for="category">Category:</label><br>
        <input type="text" id="category" name="category" required><br><br>

        <input type="submit" value="Add Book">
    </form>
    <p><a href="bookManage.jsp">Back to Previous Menu</a></p>
    <p><a href="index.jsp">Back to Main Menu</a></p>
</body>
</html>