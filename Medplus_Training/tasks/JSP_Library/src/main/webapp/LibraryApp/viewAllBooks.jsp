<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, pojo.Book, service.BookService, service.exceptions.DatabaseException" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Books</title>
</head>
<body>
	<h1>All Books</h1>
    <%
    try {
    	BookService bookService = new BookService(); 
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) { %>
        	<p style="color: red;"> No books found in the library. </p>
        <% } else{
        for (Book book : books) {%>
            <%=book %><br>
       <% }
        }
    } catch (DatabaseException e ) { %>
    	<p style="color: red;">Error retrieving books: <%= e.getMessage() %></p>
    <%}
    %>
    <p><a href="bookManage.jsp">Back to Previous Menu</a></p>
    <p><a href="index.jsp">Back to Main Menu</a></p>
</body>
</html>