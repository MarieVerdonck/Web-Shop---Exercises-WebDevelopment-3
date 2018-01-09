<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Add Product"/>
</jsp:include>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param name="title" value="Add Product"/>
		</jsp:include>
	
	<jsp:include page="addProductForm.jsp">
		<jsp:param name="action" value="addProductToDB"/>
		<jsp:param name="submit" value="Add Product"/>
	</jsp:include>
	
	<%@include file="footer.jspf" %>
	</div>
</body>
</html>
