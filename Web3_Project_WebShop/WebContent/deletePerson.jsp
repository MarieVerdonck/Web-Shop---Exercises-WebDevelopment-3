<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Delete Person"/>
</jsp:include>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param name="title" value="Delete Person"/>
		</jsp:include>
		
		<p>Do you want to delete this person?</p>
		<p>User ID: ${person.userid}</p>
		<p>Name: ${person.firstName} ${person.lastName}</p>
		<p>E-mail: ${person.email}</p>
		
		<form method="POST" action="Controller?action=deletePerson&id=${person.userid}">
			<input type="submit" id="delete" name="submit" value="Delete" />
			<input type="submit" id="cancel" name="submit" value="Cancel" />
		</form>
		
	</main>
	<%@include file="footer.jspf" %>
	</div>
</body>
</html>
