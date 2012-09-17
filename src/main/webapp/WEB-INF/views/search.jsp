<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp" />

<div class="container">
	<div class="row-fluid">
		<div class="span6 offset3">
		<c:choose>
				<c:when test="${type == 'SUPPLIER'}">
					<h3>Supplier Search</h3>
				</c:when>
				<c:when test="${type == 'ORDER'}">
					<h3>Order Search</h3>
				</c:when>
				<c:when test="${type == 'COMPONENT'}">
					<h3>Component Search</h3>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${type == 'SUPPLIER'}">
					<form:form cssClass="form-search" action="/search/suppliersearch"
						commandName="search">
						<div class="input-append">
							<form:input path="searchString" cssClass="span12 search-query" />
							<button type="submit" class="btn">Search</button>
						</div>
					</form:form>
				</c:when>
				<c:when test="${type == 'ORDER'}">
					<form:form cssClass="form-search" action="/search/ordersearch"
						commandName="search">
						<div class="input-append">
							<form:input path="searchString" cssClass="span12 search-query" />
							<button type="submit" class="btn">Search</button>
						</div>
					</form:form>
				</c:when>
				<c:when test="${type == 'COMPONENT'}">
					<form:form cssClass="form-search" action="/search/componentsearch"
						commandName="search">
						<div class="input-append">
							<form:input path="searchString" cssClass="span12 search-query" />
							<button type="submit" class="btn">Search</button>
						</div>
					</form:form>
				</c:when>
			</c:choose>

		</div>
	</div>
	<hr />
         <footer>
         	<p>� Alex Barnes & Yubi Jewellery 2012</p>
         </footer>
</div>

<jsp:include page="footer.jsp" />