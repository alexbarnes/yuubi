<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Set some variables -->
<c:set var="readOnly" value="${screenMode == 'ENQUIRE'}" />

<jsp:include page="header.jsp" />
<div class="container">
	
	<!-- Set the title of the page according to the screen mode  -->
	<c:choose>
		<c:when test="${screenMode == 'ENQUIRE'}"><h2>View supplier</h2></c:when>
		<c:when test="${screenMode == 'CREATE'}"><h2>Add supplier</h2></c:when>
		<c:when test="${screenMode == 'UPDATE'}"><h2>Maintain supplier</h2></c:when>
	</c:choose>
	
	<hr />
	
	<!-- The main form backed by the supplier object - this posts to the save method -->
	<form:form cssClass="form-horizontal" commandName="supplier" action="/supplier/save">
		
		<!-- The supplier name -->
			<c:set var="nameErrors"><form:errors path="name"/></c:set>
			<c:if test="${not empty nameErrors}">
				<div class="control-group error">
			</c:if>

			<c:if test="${empty nameErrors}">
				<div class="control-group">
			</c:if>
			
			<label class="control-label" for="name">Name</label>
			<div class="controls">
				<!--  Read only - display a span -->
				<c:if test="${readOnly}">
					<span class="input-xlarge uneditable-input">${supplier.name}</span>
				</c:if>

				<!--  If editable display a field -->
				<c:if test="${not readOnly}">
					<form:input type="text" path="name" cssClass="input-xlarge" />
					<form:errors path="name" cssClass="help-inline"/>
				</c:if>
			</div>
		</div>

		<!-- The supplier URL -->
		<c:set var="nameErrors"><form:errors path="url"/></c:set>
			<c:if test="${not empty nameErrors}">
				<div class="control-group error">
			</c:if>

			<c:if test="${empty nameErrors}">
				<div class="control-group">
			</c:if>
			<label class="control-label" for="inputIcon">URL</label>
			<div class="controls">
				<c:if test="${readOnly}">
					<span class="input-xlarge uneditable-input">${supplier.url}</span>
					<a class="btn btn-small" href="#"><i class="icon-globe"></i></a>
				</c:if>
				<c:if test="${not readOnly}">
					<form:input type="text" path="url" cssClass="input-xlarge" />
					<form:errors path="url" cssClass="help-inline"/>
				</c:if>
			</div>
		</div>
		
		<!-- Hidden fields which need to be provided to the model. Only required for updates -->
		<c:if test="${screenMode == 'UPDATE'}">
			<form:input type="hidden" path="id"/>
			<form:input type="hidden" path="version"/>
		</c:if>

		<!-- The form buttons, these depend on the screenmode -->
		<div class="control-group">
			<div class="controls">
				<c:if test="${not readOnly}">
					<button type="submit" class="btn">Save</button>
					<a href="<spring:url value="/supplier/view/${supplier.id}"/>"
						role="button" class="btn btn-inverse" data-toggle="modal">Cancel</a>
				</c:if>
				<c:if test="${readOnly}">
					<a href="<spring:url value="/supplier/edit/${supplier.id}"/>"
						role="button" class="btn btn-inverse" data-toggle="modal">Edit</a>
				</c:if>
			</div>
		</div>
	</form:form>
	<hr />
	<footer>
		<p>© Alex Barnes & Yubi Jewellery 2012</p>
	</footer>
</div>
<jsp:include page="footer.jsp" />