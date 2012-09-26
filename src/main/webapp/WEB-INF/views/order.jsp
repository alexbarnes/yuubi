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
			<!-- The form backing the order -->
			<c:url var="url" value="/order/save" />
			<form:form cssClass="form-horizontal" commandName="order"
				action="${url}" id="orderForm">
				<div class="row-fluid well">
					<c:choose>
						<c:when test="${screenMode == 'ENQUIRE'}">
							<legend>View order</legend>
						</c:when>
						<c:when test="${screenMode == 'CREATE'}">
							<legend>Add order</legend>
						</c:when>
						<c:when test="${screenMode == 'UPDATE'}">
							<legend>Maintain order</legend>
						</c:when>
					</c:choose>
					<div class="span6"></div>
					<input type="hidden" name="screenMode" value="${screenMode}" />
				</div>
				<div class="row-fluid well">
					<legend>Items</legend>
					<div class="span8">
						<div id="form"></div>
					</div>
				</div>
				<div class="row-fluid well controlContainer">
					<div class="offset7">
						<div class="control-group">
							<div class="controls ">
								<c:if test="${not readOnly}">
									<button type="submit" class="btn btn-primary btn-inverse">Save</button>
									<spring:url value="/order/cancel" var="cancelURL">
										<spring:param name="screenMode" value="${screenMode}" />
									</spring:url>
									<a href="${cancelURL}" role="button" class="btn btn-inverse"
										data-toggle="modal">Cancel</a>
								</c:if>
								<c:if test="${readOnly}">
									<a href="<spring:url value="/order/edit/${order.id}"/>"
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
	function addItem() {
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/order/item/add",
			data : $("#orderForm").serialize(),
			success : function(response) {
				$("#form").html(response);
			}
		});
	}

	function removeItem(index) {
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/order/item/remove/" + index,
			data : $("#orderForm").serialize(),
			success : function(response) {
				$("#form").html(response);
			}
		});
	}
	
	$('.controlContainer').hide();
	$('#form').hide();
	
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/order/items",
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