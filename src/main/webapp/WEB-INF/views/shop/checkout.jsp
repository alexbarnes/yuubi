<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="header.jsp" />
<div class="container">
	<div class="row">
		<div class="span12">
			<div class="row">
				<div class="span12">
					<ul class="breadcrumb">
						<li><a href="<spring:url value='/shop'/>"><i
								class="icon-home"></i></a> <span class="divider">/</span></li>
						<li class="active">Your Basket</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="span12">
			<h2 class="title">Your Basket</h2>
			<hr />
			<div class="entry">
				<table class="table table-bordered table-striped">
					<thead>
						<tr>
							<th>Product Name</th>
							<th>Quantity</th>
							<th>Unit Price</th>
							<th>Total</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${basket.items}" var="item">
							<tr>
								<td>${item.value.productDescription}</td>
								<td>${item.value.number}</td>
								<td>£${item.value.itemCost}</td>
								<td>£${item.value.totalCost}</td>
							</tr>
						</c:forEach>
						<!-- Shipping -->
						<tr>
							<td>&nbsp;</td>
							<td>
								<select id="country">
									<option value="">Select One</option>
									<c:forEach items="${countries}" var="country">
										<option value="${country.code}">${country.description}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select id="shipping"></select>
							</td>
							<td><strong>Shipping Total</strong></td>
						</tr>
						<!-- Discounts -->
						<!--  Totals -->
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><strong>£${total}</strong></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="row pull-right">
		<div class="span2">
			<a href="<spring:url value='/shop/checkout/setuppayment'/>"
				class="pull-right"> <img
				src="https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif"
				align="left" style="margin-right: 7px;">
			</a>
		</div>
	</div>
</div>


<script
	src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>

<script type="text/javascript">

	$(function() {
		$('#country').change(function() {
			var selectedCountry = $('#country').val();
			$('#shipping').empty();
			
			// Also null out the delivery method on the basket
			$.ajax({
				url :'<c:url value="/shop/checkout/listdeliverymethods/"></c:url>' + selectedCountry,
				type : 'GET',
				datatype : 'JSON',
				success: function( json ) {
					$('#shipping').append($('<option>').text('Select One').attr('value', ''));
					$.each(json.deliverymethods, function(i, value) {
						$('#shipping').append($('<option>').text(value.description + ' - £' + value.cost).attr('value', value.id));
					});
				}
			});
		});
		
		// Handle the selection of a 
		$('#shipping').change(function() {
		});
	});
	
</script>
</html>
