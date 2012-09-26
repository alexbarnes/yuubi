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
					$('#item' + index).val(val);
				}
			});
		});
		
	});
	
</script>

<table class="table">
	<thead>
		<tr>
			<th>Component</th>
			<th>Number</th>
			<c:if test="${not readOnly}">
				<th/>
			</c:if>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${order.items}" var="current" varStatus="status">
			<tr>
				<td>
					<c:if test="${not readOnly}">
						<input 
							type="text" 
							class="input-xlarge component"
							autocomplete="off"
							value="${order.items[status.index].id.component.description}"
							id="componentName${status.index}" /> 
					</c:if>
					<c:if test="${readOnly}">
						<span class="input-xlarge uneditable-input">${order.items[status.index].id.component.description}</span>
					</c:if>
					<form:input
						path="order.items[${status.index}].componentId"
						type="hidden" id="component${status.index}" />
					</td>
				<td>
					<c:if test="${not readOnly}">
						<form:input
							path="order.items[${status.index}].number" class="input-small"/>
					</c:if>
					<c:if test="${readOnly}">
					<span class="input-small uneditable-input">${order.items[status.index].number}</span>	
					</c:if>
				</td>
				<c:if test="${not readOnly}">
					<td>
						<a  role="button" 
							class="btn btn-inverse"
							onclick="removeItem(${status.index});">Remove</a>
					</td>
				</c:if>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${not readOnly}">
	<a role="button" class="btn btn-inverse" onclick="addItem();">Add</a>
</c:if>



