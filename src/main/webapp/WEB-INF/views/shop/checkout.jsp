<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<c:set var="title" scope="request" value=" Y&#362;BI - Checkout"></c:set>
<c:set var="currency" value="${sessionScope.currency}"/>
<jsp:include page="header.jsp" />
<body>
	<div class="container">
		<jsp:include page="menu.jsp" />
		<div class="row">
			<div class="span12">
				<div class="row">
					<div class="span12">
						<ul class="breadcrumb">
							<li><a href="<spring:url value='/shop'/>"><i
									class="icon-home"></i></a> <span class="divider">/</span></li>
							<li class="active">Checkout</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<h2 class="title">Checkout</h2>
				<hr />
				<c:if test="${deliveryMethod == false}">
					<div class="alert alert-error">
						<button type="button" class="close" data-dismiss="alert">×</button>
						<i class="icon-road"></i> A valid shipping method must be selected
						before checking out
					</div>
				</c:if>
				<c:if test="${error}">
					<div class="alert alert-error">
						<button type="button" class="close" data-dismiss="alert">×</button>
						We're very sorry but an unexpected error occurred. We have been notified and will look into it immediately. Please try ordering again later.
					</div>
				</c:if>
				<div class="entry">
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>Product Name</th>
								<th>Quantity</th>
								<th>Price</th>
								<th>Total</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${basket.items}" var="item">
								<tr>
									<td>${item.value.productDescription}</td>
									<td>${item.value.number}</td>
									<td><c:out value="${sessionScope.currency.symbol}"/> <fmt:formatNumber value="${item.value.product.getPriceInCurrency(currency)}"/></td>
									<td><c:out value="${sessionScope.currency.symbol}"/> <fmt:formatNumber value="${item.value.getLineCost(currency)}"/></td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="3">Product Total</td>
								<td><c:out value="${sessionScope.currency.symbol}"/> <fmt:formatNumber value="${basket.getTotal(currency)}"/></td>
							</tr>
						</tbody>
					</table>
					<div class="well">
						<h4>Shipping</h4>
						<hr>
						<div>
							<select id="country">
								<option class="input-large" value="">Select One</option>
								<c:forEach items="${countries}" var="country">
									<option value="${country.code}">${country.description}</option>
								</c:forEach>
							</select>
						</div>
						<div>
						<select id="shipping"></select>
						</div>
						<div id="shippingtotal" class="pull-right">
							<strong> <c:choose>
									<c:when test="${basket.deliveryMethod != null}">
										<c:out value="${sessionScope.currency.symbol}"/>  <fmt:formatNumber value="${shippingAmount}" />
									</c:when>
								</c:choose>
							</strong>
						</div>
						<br>
					</div>
					<div class="well">
						<h4>Discounts</h4>
						<hr>
						<div id="discountNotification"></div>
						<div class="control-group" id="discountFields">
							<div class="input-append controls" id="discountControls">
							<c:if test="${basket.discount == null}">
								<input class="span4" id="discountCode" type="text"
									name="discountCode">
								<button class="btn" id="applyDiscount" onClick="applyDiscount()" type="button">Apply</button>
							</c:if>
							<c:if test="${basket.discount != null}">
								<span class="input-xlarge uneditable-input">${basket.discount.description}</span>
								<button class="btn" onClick="removeDiscount()"><i class="icon-remove-sign"></i></button>
							</c:if>
							</div>
						</div>
						<div id="discountAmount" class="pull-right">
								<strong> <c:choose>
									<c:when test="${basket.discount != null}">
										<c:out value="${sessionScope.currency.symbol}"/> ${discountAmount}
									</c:when>
								</c:choose>
							</strong>
						</div>
						<br>
					</div>
					<div class="well">
						<h4>Total</h4>
						<hr>
						<div id="total" class="pull-right">
							<strong> <c:out value="${sessionScope.currency.symbol}"/> <fmt:formatNumber value="${total}" />
							</strong>
						</div>
						<br>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span2">
				<a href="<spring:url value='/shop/basket/show'/>"><button
						class="btn">
						<i class="icon-shopping-cart"></i> Basket
					</button></a>
			</div>
			<div class="span2 offset8">
				<a href="<spring:url value='/shop/checkout/setuppayment'/>"
					class="pull-right"> <img
					src="https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif"
					align="left" style="margin-right: 7px;">
				</a>
			</div>
		</div>
		<hr>
	</div>
	<script src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
	<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#country').val('${basket.deliveryMethod.country.code}');
			
			// If we have a country then load the delivery methods
			if ($('#country').val() != '') {
				$.ajax({
					url : '<c:url value="/shop/checkout/listdeliverymethods/${basket.deliveryMethod.country.code}"></c:url>',
					type : 'GET',
					datatype : 'JSON',
					success : function(json) {
						$('#shipping').append(
						$('<option>').text('Select One').attr('value', ''));
						$.each(json.deliverymethods,function(i, value) {
							 var localCost;
								<c:choose>
									<c:when test="${sessionScope.currency.currencyCode == 'USD'}">localCost = value.costUsd</c:when>
									<c:when test="${sessionScope.currency.currencyCode == 'EUR'}">localCost = value.costEur</c:when>
									<c:when test="${sessionScope.currency.currencyCode == 'GBP'}">localCost = value.cost</c:when>
								</c:choose>
							$('#shipping').append($('<option>')
									.text(value.description + ' - ' + '<c:out value="${sessionScope.currency.symbol}"/> ' + localCost)
									.attr('value',value.id));
						});
						$('#shipping').val('${basket.deliveryMethod.id}');
					}
				});
			}
			
			// Setup the screen for mobile devices
			$('body').on('touchstart.dropdown', '.dropdown-menu', function(e) {
				e.stopPropagation();
			});
		});

		// Handle a change of country.
		$(function() {
			$('#country').change(function() {
				var selectedCountry = $('#country').val();
				$('#shipping').empty();
				$('#shippingtotal').html('<strong>' + '<c:out value="${sessionScope.currency.symbol}"/> ' + '0.00' + '</strong>');
				$.ajax({
					url : '<c:url value="/shop/basket/setdeliverymethod/"></c:url>',
					type : 'GET',
					datatype : 'JSON',
					data : {
						id : ''
					},
					success : function(json) {
						$('#total').html('<strong>' + '<c:out value="${sessionScope.currency.symbol}"/> ' + json.newTotal + '</strong>');
					}
				});
				
				$('#shipping').append($('<option>').text('Select One').attr('value',''));
				if (selectedCountry != '') {
					$.ajax({
						url : '<c:url value="/shop/checkout/listdeliverymethods/"></c:url>' + selectedCountry,
						type : 'GET',
						datatype : 'JSON',
						success : function(json) {
							$.each(json.deliverymethods,function(i, value) {
								 var localCost;
								<c:choose>
									<c:when test="${sessionScope.currency.currencyCode == 'USD'}">localCost = value.costUsd</c:when>
									<c:when test="${sessionScope.currency.currencyCode == 'EUR'}">localCost = value.costEur</c:when>
									<c:when test="${sessionScope.currency.currencyCode == 'GBP'}">localCost = value.cost</c:when>
								</c:choose>
								$('#shipping')
									.append($('<option>')
											.text(value.description + ' - ' + '<c:out value="${sessionScope.currency.symbol}"/> ' + localCost)
											.attr('value',value.id));
							});
						}
					});
				}
			});

			$('#shipping').change(function() {
				$.ajax({
					url : '<c:url value="/shop/basket/setdeliverymethod/"></c:url>',
					type : 'GET',
					datatype : 'JSON',
					data : {
						id : $('#shipping').val()
					},
					success : function(json) {
						$('#shippingtotal').html('<strong>' + '<c:out value="${sessionScope.currency.symbol}"/> ' + json.deliveryCost + '</strong>');
						$('#total').html('<strong>' + '<c:out value="${sessionScope.currency.symbol}"/> ' + json.newTotal + '</strong>');
					}
				});
			});
		});
		
		$(document).on('click', '.dropdown-menu a', function() {
			document.location = $(this).attr('href');
		});
		
		
		// Handle the click of the discount button
		function applyDiscount() {
			if ($('#discountCode').val() == '') {
				$('#discountFields').addClass('error');
				$('#discountNotification').html('<div class="alert alert-error">Please enter a valid discount code</div>');
			} else {
				$.ajax({
					url : '<c:url value="/shop/checkout/applydiscount/"></c:url>' + $('#discountCode').val(),
					type : 'GET',
					datatype : 'JSON',
					success : function(json) {
						if (json.valid == true) {
							$('#total').html('<strong>' + '<c:out value="${sessionScope.currency.symbol}"/> '+ json.newTotal + '</strong>');
							
							$('#discountNotification').empty();
							$('#discountNotification').html('<div class="alert alert-success">Discount applied. Your total has been updated.</div>');
							
							$('#discountAmount').empty();
							$('#discountAmount').html('<strong>' + '<c:out value="${sessionScope.currency.symbol}"/> ' + json.amount + '</strong>');
							
							$('#discountControls').empty();
							$('#discountControls').html(
								'<span class="input-xlarge uneditable-input">' + json.discount.description +
								'</span><button class="btn" onClick="removeDiscount()"><i class="icon-remove-circle"></i></a>'
							);
						} else {
							$('#discountFields').addClass('error');
							$('#discountNotification').html('<div class="alert alert-error">Please enter a valid discount code</div>');
						}
					}
				});
			}
		};
		
		
		// Handle the removal of the discount
		function removeDiscount() {
			$('#discountNotification').empty();
			$.ajax({
				url : '<c:url value="/shop/checkout/removediscount"></c:url>',
				type : 'GET',
				datatype : 'JSON',
				success : function(json) {
						$('#total').html('<strong><c:out value="${sessionScope.currency.symbol}"/> '+ json.newTotal + '</strong>');
						$('#discountControls').empty();
						$('#discountAmount').empty();
						$('#discountControls').html(
								'<input class="span4" id="discountCode" type="text" name="discountCode">' + 
								'<button class="btn" id="applyDiscount" onClick="applyDiscount()" type="button">Apply</button>');
						$('#discountFields').removeClass('error');
				}
			});
		};
	</script>
</body>
</html>