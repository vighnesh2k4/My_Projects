<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pojo.Member, pojo.Member.Gender, service.MemberService, service.exceptions.DatabaseException, 
service.exceptions.InvalidInputException" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register New User</title>
</head>
<body>
	<h1>Register New User</h1>
	<%
	if ("POST".equalsIgnoreCase(request.getMethod())){
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String genderChar = request.getParameter("gender");
		Gender gender = null;
        if (genderChar.equals("M")) {
            gender = Gender.MALE;
        } else if (genderChar.equals("F")) {
            gender = Gender.FEMALE;
        }
		String address = request.getParameter("address");
		Member newMember = new Member();
        newMember.setName(name);
        newMember.setEmail(email);
        newMember.setMobile(Long.parseLong(mobile));
        newMember.setGender(gender);
        newMember.setAddress(address);
		 
		   try {
			   MemberService memberService = new MemberService();
			   memberService.addMember(newMember); %>
		   	<p style="color: green;">Member registered successfully!</p>
		   <% } catch (InvalidInputException | DatabaseException e) { %>
		       <p style="color: red;">Error registering member: <%= e.getMessage() %></p>
		   <% }
	}%>
	<form action="registerNewMember.jsp" method="post">
        <label for="name">Name:</label><br>
        <input type="text" id="name" name="name" required><br><br>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="mobile">Mobile:</label><br>
        <input type="tel" id="mobile" name="mobile" required><br><br>
        
        <label for="gender">Gender (M/F):</label><br>
        <input type="text" id="gender" name="gender" required maxlength="1" pattern="[MF]"><br><br>  
        
        <label for="address">Address:</label><br>
        <input type="text" id="address" name="address" required><br><br>

        <input type="submit" value="Add Member">
    </form>
    <p><a href="memManage.jsp">Back to Previous Menu</a></p>
    <p><a href="index.jsp">Back to Main Menu</a></p>
</body>
</html>