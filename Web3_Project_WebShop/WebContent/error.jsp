<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isErrorPage="true" import="java.io.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Error page" />
</jsp:include>
<body>

	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param name="title" value="Oh no!" />
		</jsp:include>
		<main> You caused an exception ${exception.printStackTrace(new java.io.PrintWriter(out)); } on the server! </main>
		<%@include file="footer.jspf"%>
	</div>

</body>
</html>