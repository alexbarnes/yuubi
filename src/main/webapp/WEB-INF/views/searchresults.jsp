<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp" />
<div class="container">
	<h3>Search results</h3>
	<div class="row-fluid">
		<div class="span6 offset3">
			<c:choose>
				<c:when test="${empty results} }">
					<p>No results found</p>
				</c:when>
				<c:otherwise>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Type</th>
								<th>Name</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${results}" var="result">
								<tr>
									<td>
										<c:choose>
											<c:when test="${result.entityType == 'SUPPLIER'}">
												Supplier
											</c:when>
											<c:when test="${result.entityType == 'ORDER'}">
												Order
											</c:when>
											<c:when test="${result.entityType == 'COMPONENT'}">
												Component
											</c:when>
										</c:choose>
									</td>
									<td>${result.description}</td>
									<c:choose>
										<c:when test="${result.entityType == 'SUPPLIER'}">
											<td><a href='<spring:url value="/supplier/view/${result.id}"/>' role="button"
												class="btn btn-inverse" data-toggle="modal">Open</a></td>
										</c:when>
										<c:when test="${result.entityType == 'ORDER'}">
											<td><a href="<spring:url value="/order/view/${result.id}"/>" role="button"
												class="btn btn-inverse" data-toggle="modal">Open</a></td>
										</c:when>
										<c:when test="${result.entityType == 'COMPONENT'}">
											<td><a href="<spring:url value="/component/view/${result.id}"/>" role="button"
												class="btn btn-inverse" data-toggle="modal">Open</a></td>
										</c:when>
									</c:choose>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<hr />
         <footer>
         	<p>© Alex Barnes & Yubi Jewellery 2012</p>
         </footer>
</div>

<jsp:include page="footer.jsp" />