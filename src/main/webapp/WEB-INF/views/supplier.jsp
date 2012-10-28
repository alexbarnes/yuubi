<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Set some variables -->
<c:set var="readOnly" value="${screenMode == 'ENQUIRE'}" />

<jsp:include page="header.jsp" />
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span8 offset2">

			<!-- The main form backed by the supplier object - this posts to the save method -->
			<c:url var="url" value="/admin/supplier/save" />
			<form:form cssClass="form-horizontal" commandName="supplier"
				action="${url}">
				<div class="row-fluid well">
					<!-- Set the title of the page according to the screen mode  -->
					<c:choose>
						<c:when test="${screenMode == 'ENQUIRE'}">
							<legend>View supplier</legend>
						</c:when>
						<c:when test="${screenMode == 'CREATE'}">
							<legend>Add supplier</legend>
						</c:when>
						<c:when test="${screenMode == 'UPDATE'}">
							<legend>Maintain supplier</legend>
						</c:when>
					</c:choose>
					<div class="span6">
						<!-- The supplier name -->
						<c:set var="nameErrors">
							<form:errors path="name" />
						</c:set>
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
								<form:errors path="name" cssClass="help-inline" />
							</c:if>
						</div>
					</div>

					<!-- The supplier URL -->
					<c:set var="nameErrors">
						<form:errors path="url" />
					</c:set>
					<c:if test="${not empty nameErrors}">
						<div class="control-group error">
					</c:if>

					<c:if test="${empty nameErrors}">
						<div class="control-group">
					</c:if>
					<label class="control-label" for="inputIcon">URL</label>
					<div class="controls">
						<c:if test="${readOnly}">
							<div class="input-append">
								<span class="input-xlarge uneditable-input">${supplier.url}</span>
								<button class="btn" type="button"><i class="icon-globe"></i></button>
							</div>
							
						</c:if>
						<c:if test="${not readOnly}">
							<form:input type="text" path="url" cssClass="input-xlarge" />
							<form:errors path="url" cssClass="help-inline" />
						</c:if>
					</div>
				</div>

				<!-- Hidden fields which need to be provided to the model. Only required for updates -->
				<c:if test="${screenMode == 'UPDATE'}">
					<form:input type="hidden" path="id" />
					<form:input type="hidden" path="version" />
				</c:if>
		</div>
	</div>
	<div class="row-fluid well controlContainer">
		<div class="offset7">
			<!-- The form buttons, these depend on the screenmode -->
			<div class="control-group">
				<div class="controls">

					<c:if test="${not readOnly}">
						<button type="submit" class="btn">Save</button>
						<c:if test="${supplier.id > 0}">
							<a href="<spring:url value="/admin/supplier/view/${supplier.id}"/>"
								role="button" class="btn btn-inverse" data-toggle="modal">Cancel</a>
						</c:if>

						<c:if test="${supplier.id == 0}">
							<a href="<spring:url value="/admin/home"/>" role="button"
								class="btn btn-inverse" data-toggle="modal">Cancel</a>
						</c:if>
					</c:if>

					<c:if test="${readOnly}">
						<a href="<spring:url value="/admin/supplier/edit/${supplier.id}"/>"
							role="button" class="btn btn-inverse" data-toggle="modal">Edit</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	</form:form>
	<hr />
	<footer>
		<p>© Alex Barnes & Yubi Jewellery 2012</p>
	</footer>
</div>
<jsp:include page="footer.jsp" />