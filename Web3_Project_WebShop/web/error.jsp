<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Error Page" />
</jsp:include>
<body>
	<div id="container">
	<jsp:include page="header.jsp">
		<jsp:param name="title" value="Oh no! Something went wrong :(" />
	</jsp:include>
		<main>
		<article>
			There was a "${pageContext.exception}" on the server! <br>
		</article>
		</main>
		<%@include file="footer.jspf" %>
	</div>
</body>
</html>