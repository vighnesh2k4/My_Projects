<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,pojo.Member, service.MemberService, service.exceptions.DatabaseException" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Members</title>
</head>
<body>
	<h1>All Members</h1>
    <%
    try {
    	MemberService memberService = new MemberService(); 
    	List<Member> members = memberService.getAllMembers();
    	if (members.isEmpty()) { %>
    	<p style="color: red;"> No members found in the library. </p>
    <% } else{
    for (Member member : members) {%>
        <%=member %><br>
   <% }
    }
    } catch (DatabaseException e ) { %>
    	<p style="color: red;">Error retrieving members: <%= e.getMessage() %></p>
    <%}
    %>
    <p><a href="memManage.jsp">Back to Previous Menu</a></p>
    <p><a href="index.jsp">Back to Main Menu</a></p>
</body>
</html>