<%@taglib prefix="func" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- fn gaf error (Attempt to redefine the prefix [fn] to [http://java.sun.com/jsp/jstl/core], when it was already defined as [http://java.sun.com/jsp/jstl/functions] in the current scope.] with root cause
org.apache.jasper.JasperException: /addProductForm.jsp (line: [21], column: [72]) Attempt to redefine the prefix [fn] to [http://java.sun.com/jsp/jstl/core], when it was already defined as [http://java.sun.com/jsp/jstl/functions] in the current scope.)
WHY?! -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<main>
		<c:if test="${errors.size() > 0}">
			<div class="alert-danger">
				<ul>
					<c:forEach var="error" items="${errors}">
						<li>${error}</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
		
		<form method="post" action="Controller?action=${param.action}" novalidate="novalidate">
	    	<!-- novalidate in order to be able to run tests correctly -->
	    	<input type="hidden" id="productId" name="productId" value="${product.productId}" />
	        <p>
	        	<label for="name">Name</label>
	        	<input type="text" id="name" name="name" required value="${func:escapeXml(product.name)}"> 
	        	<!-- OR (XSS solution2) 
	        	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/core" %>
	        	 value="<c:out value='${product.name}'/>"
	        	-->
	         </p>
	        <p>
	        	<label for="description">Description</label>
	        	<input type="text" id="description" name="description" required value="${func:escapeXml(product.description)}"> 
	        </p>
	        <p>
	        	<label for="price">Price</label>
	        	<input type="number" id="price" name="price" min="0" step="0.05" required value="${product.price}"> 
	        </p>
	        <p><input type="submit" id="addProduct" value="${param.submit}"></p>
	        
	    </form>
	</main>