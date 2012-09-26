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
			<!-- The form backing the product -->
			<c:url var="url" value="/product/save" />
			<form:form cssClass="form-horizontal" commandName="product"
				action="${url}" id="productForm">
				<div class="row-fluid well">
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
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="name">Code</label>
							<div class="controls">
								<!--  Read only - display a span -->
								<c:if test="${screenMode != 'CREATE'}">
									<span class="input-xlarge uneditable-input">${product.code}</span>
									<c:if test="${screenMode == 'UPDATE'}">
										<form:input type="hidden" path="code" />
									</c:if>
								</c:if>

								<!--  Editable display a field -->
								<c:if test="${screenMode == 'CREATE'}">
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
									<form:input type="text" path="description"
										cssClass="input-xlarge" />
								</c:if>
							</div>
						</div>
					</div>
					<input type="hidden" name="screenMode" value="${screenMode}" />
				</div>
				<div class="row-fluid well">
					<legend>Components</legend>
					<div class="span8">
						<div id="form"></div>
					</div>
				</div>
				<div class="row-fluid well controlContainer" >
					<div class="offset7">
						<div class="control-group">
							<div class="controls ">
								<c:if test="${not readOnly}">

									<button type="submit" class="btn btn-primary btn-inverse">Save</button>
									<spring:url value="/product/cancel" var="cancelURL">
										<spring:param name="screenMode" value="${screenMode}" />
										<spring:param name="code" value="${product.code}" />
									</spring:url>
									<a href="${cancelURL}" role="button" class="btn btn-inverse"
										data-toggle="modal">Cancel</a>
								</c:if>
								<c:if test="${readOnly}">
									<a href="<spring:url value="/product/edit/${product.code}"/>"
										role="button" class="btn btn-inverse btn-primary"
										data-toggle="modal">Edit</a>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	<hr />
	<footer>
		<p>© Alex Barnes & Yubi Jewellery 2012</p>
	</footer>

</div>
<script type="text/javascript">
	function addComponent() {
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/product/component/add",
			data : $("#productForm").serialize(),
			success : function(response) {
				$("#form").html(response);
			}
		});
	}

	function removeComponent(index) {
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/product/component/remove/" + index,
			data : $("#productForm").serialize(),
			success : function(response) {
				$("#form").html(response);
			}
		});
	}
	
	$('.controlContainer').hide();
	$('#form').hide();
	
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/product/components",
		data : ({
			screenMode : "${screenMode}",
		}),
		success : function(response) {
			$("#form").html(response);
			$('#form').fadeIn(1000);
			$('.controlContainer').fadeIn(2000);
		}
	});
</script>
<jsp:include page="footer.jsp" />