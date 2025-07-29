<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pojo.Member, pojo.Member.Gender, service.MemberService, service.exceptions.DatabaseException, 
service.exceptions.InvalidInputException" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Member Details</title>
</head>
<body>
	<h1>Update Member Details</h1>
	<%
	if ("POST".equalsIgnoreCase(request.getMethod())){
		int memberId=Integer.parseInt(request.getParameter("memid"));
		try {
			MemberService memberService = new MemberService(); 
			Member existingMember = memberService.getMemberById(memberId);
	        if (existingMember == null) { %>
	        	<p style="color: red;"> Member with ID <%=memberId%> not found. </p>
	       <% }
	        else{
	        	String newName = request.getParameter("name");
	        	String newEmail = request.getParameter("email");
	        	long newMobile = Long.parseLong((request.getParameter("mobile")));
	        	String newGenderChar= request.getParameter("gender");
	        	if (newGenderChar.equals("M")) {
                    existingMember.setGender(Gender.MALE);
                } else if (newGenderChar.equals("F")) {
                    existingMember.setGender(Gender.FEMALE);
                }
	        	String newAddress= request.getParameter("address");
	        	existingMember.setName(newName);
	        	existingMember.setEmail(newEmail);
	        	existingMember.setMobile(newMobile);
	        	existingMember.setAddress(newAddress);
	        	memberService.updateMember(existingMember); %>
	        	<p style="color: green;">Member details updated successfully!</p>
	        <%}  %>       
		<% } catch (InvalidInputException | DatabaseException e) { %>
	        <p style="color: red;">Error updating member details: <%= e.getMessage() %></p>
	    <% }
	}%>
	<form action="updateMemberDetails.jsp" method="post">
        <label for="memid">Member Id:</label><br>
        <input type="number" id="memid" name="memid" required><br><br>
        
        <label for="name">New Name:</label><br>
        <input type="text" id="name" name="name" required><br><br>

        <label for="email">New Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="mobile">New Mobile:</label><br>
        <input type="tel" id="mobile" name="mobile" required><br><br>
        
        <label for="gender">New Gender (M/F):</label><br>
        <input type="text" id="gender" name="gender" required maxlength="1" pattern="[MF]"><br><br>  
        
        <label for="address">New Address:</label><br>
        <input type="text" id="address" name="address" required><br><br>

        <input type="submit" value="Update Member">
    </form>
    <p><a href="memManage.jsp">Back to Previous Menu</a></p>
    <p><a href="index.jsp">Back to Main Menu</a></p>
</body>
</html>