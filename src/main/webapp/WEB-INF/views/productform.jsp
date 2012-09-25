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
						id="componentName${status.index}" />
						<form:input
							path="product.components[${status.index}].componentId" type="hidden"
							id="component${status.index}" />
						</td>
							<td><form:input path="product.components[${status.index}].number" /></td>

					<td><a role="button" class="btn btn-inverse"
						onclick="removeComponent(${status.index});">Remove</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a role="button" class="btn btn-inverse" onclick="addComponent();">Add</a>
	


