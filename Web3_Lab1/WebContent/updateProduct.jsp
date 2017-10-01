<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Update Product"/>
</jsp:include>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param name="title" value="Update Product"/>
	<main>
	
	<jsp:include page="addProductForm.jsp">
		<jsp:param name="action" value="updateProduct"/>
		<jsp:param name="submit" value="Update Product"/>
	</jsp:include>

	</main>
	<footer>
	&copy; Webontwikkeling 3, UC Leuven-Limburg
	</footer>
	</div>
</body>
</html>
