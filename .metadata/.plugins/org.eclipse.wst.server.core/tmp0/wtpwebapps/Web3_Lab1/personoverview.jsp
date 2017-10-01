<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param name="title" value="User Overview"/>
</jsp:include>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param name="title" value="User Overview"/>
		</jsp:include>
		<main>
			<table>
				<tr>
					<th>E-mail</th>
					<th>First Name</th>
					<th>Last Name</th>
				</tr>
				<c:forEach var="person" items="${persons}">
					<tr>
						<td>${person.email}</td>
						<td>${person.firstName}</td>
						<td>${person.lastName}</td>
					</tr>
				</c:forEach>
				<caption>Users Overview</caption>
			</table>
		</main>
		<footer>
			&copy; Webontwikkeling 3, UC Leuven-Limburg
		</footer>
	</div>
</body>
</html>