<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Check Password" />
</jsp:include>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param name="title" value="Check Password" />
		</jsp:include>
		<div id="container">
			<p>User ID: ${person.userid}</p>

			<form method="POST"
				action="Controller?action=checkPassword&id=${person.userid}">
				<input type="password" id="password" name="password" /> <br>
				<input type="submit" id="submit" name="submit" value="Submit" />
			</form>

			<p id="responseCheckPassword">${response}</p>
		</div>

		</main>
		<%@include file="footer.jspf"%>
	</div>
</body>
</html>
