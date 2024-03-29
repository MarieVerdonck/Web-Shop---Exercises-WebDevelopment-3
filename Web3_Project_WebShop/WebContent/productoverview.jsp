<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Product Overview"/>
</jsp:include>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param name="title" value="Product Overview"/>
		</jsp:include>
		<main>
			<table>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Description</th>
					<th>Price</th>
				</tr>
				<c:forEach var="product" items="${products}">
					<tr>
						<td>${product.productId}</td>
						<td>${product.name}</td>
						<td>${product.description}</td>
						<td>${product.price}</td>
						<td><a id="delete${product.productId}" href="Controller?action=deleteProductPage&id=${product.productId}">Delete</a></td>
						<td><a id="update${product.productId}" href="Controller?action=updateProductPage&id=${product.productId}">Update</a></td>
					</tr>
				</c:forEach>
				<caption>Products Overview</caption>
			</table>
		</main>
		<%@include file="footer.jspf" %>
	</div>
</body>
</html>