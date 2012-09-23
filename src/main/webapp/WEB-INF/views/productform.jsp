<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="readOnly" value="${screenMode == 'ENQUIRE'}" />
<c:url var="componentUrl" value="/component/search"></c:url>
<script type="text/javascript">
// When ready loop over the inputs with class component
$(document).ready(function() {
	$('.component').each(function(index) {
		// Set them up for typeahead behaviour?
		$('#componentName' + index).typeahead({
			ajax : {
				url : '${componentUrl}',
				method : 'GET'
			},
		
			itemSelected : function setComponent(item, val, text) {
				$('#component' + index).val(val);
			}
		});
	});
	
})
</script>
<!-- The form backing the product -->
<c:url var="url" value="/product/save" />
<form:form cssClass="form-horizontal" commandName="product"
	action="${url}" id="productForm">
	<c:choose>
		<c:when test="${screenMode == 'ENQUIRE'}">
			<legend>View product</legend>
		</c:when>
		<c:when test="${screenMode == 'CREATE'}">
			<legend>Add product</legend>
		</c:when>
		<c:when test="${screenMode == 'UPDATE'}">
			<legend>Maintain product</legend>
		</c:when>
	</c:choose>

	<div class="control-group">
		<label class="control-label" for="name">Code</label>
		<div class="controls">
			<!--  Read only - display a span -->
			<c:if test="${readOnly}">
				<span class="input-xlarge uneditable-input">${product.code}</span>
			</c:if>

			<!--  Editable display a field -->
			<c:if test="${not readOnly}">
				<form:input type="text" path="code" cssClass="input-large" />
			</c:if>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="name">Name</label>
		<div class="controls">
			<!--  Read only - display a span -->
			<c:if test="${readOnly}">
				<span class="input-xlarge uneditable-input">${product.description}</span>
			</c:if>

			<!--  Editable display a field -->
			<c:if test="${not readOnly}">
				<form:input type="text" path="description" cssClass="input-xlarge" />
			</c:if>
		</div>
	</div>

	<!-- Hidden fields which need to be provided to the model. Only required for updates -->
	<c:if test="${screenMode == 'UPDATE'}">
		<form:input type="hidden" path="version" />
	</c:if>

	<table class="table">
		<thead>
			<tr>
				<th>Component</th>
				<th>Number</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${product.components}" var="current" varStatus="status">
				<tr>
					<td><input type="text" class="input-xlarge component"
						autocomplete="off"
						value="${product.components[status.index].id.component.description}"
						id="componentName${status.index}" /> <form:input
							path="components[${status.index}].componentId" type="hidden"
							id="component${status.index}" /></td>

					<td><form:input path="components[${status.index}].number" /></td>

					<td><a role="button" class="btn btn-inverse"
						onclick="removeComponent(${status.index});">Remove</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a role="button" class="btn btn-inverse" onclick="addComponent();">Add</a>
	<div class="control-group">
		<div class="controls ">
			<c:if test="${not readOnly}">
				<button type="submit" class="btn">Save</button>
				
				<!-- Cancel should either show the component in edit mode or return home -->
				<c:if test="${screenMode == 'UPDATE'}">
					<a href="<spring:url value="/product/view/${component.code}"/>"
						role="button" class="btn btn-inverse" data-toggle="modal">Cancel</a>
				</c:if>
				<c:if test="${screenMode == 'CREATE'}">
					<a href="<spring:url value="/home"/>" role="button"
						class="btn btn-inverse" data-toggle="modal">Cancel</a>
				</c:if>
			</c:if>
			<c:if test="${readOnly}">
				<a href="<spring:url value="/component/edit/${product.code}"/>"
					role="button" class="btn btn-inverse" data-toggle="modal">Edit</a>
			</c:if>
		</div>
	</div>
</form:form>


