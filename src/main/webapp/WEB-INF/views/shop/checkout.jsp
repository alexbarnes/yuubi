<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
						An un-expected error occurred. Please try checking out again
						later.
					</div>
				</c:if>
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
									<td>£<fmt:formatNumber value="${item.value.itemCost}"
											maxFractionDigits="2" minFractionDigits="2" /></td>
									<td>£<fmt:formatNumber value="${item.value.totalCost}"
											maxFractionDigits="2" minFractionDigits="2" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="well">
						<h4>Shipping</h4>
						<hr>
						<div>
							<select id="country">
								<option value="">Select One</option>
								<c:forEach items="${countries}" var="country">
									<option value="${country.code}">${country.description}</option>
								</c:forEach>
							</select>
						</div>
						<div><select id="shipping"></select></div>
						<div id="shippingtotal" class="pull-right">
							<strong> <c:choose>
									<c:when test="${basket.deliveryMethod != null}">
										£
											<fmt:formatNumber value="${basket.deliveryMethod.cost}"
											maxFractionDigits="2" minFractionDigits="2" />
									</c:when>
									<c:otherwise>
											£0.00
										</c:otherwise>
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
								<input class="span2" id="discountCode" type="text"
									name="discountCode">
								<button class="btn" id="applyDiscount" type="button">Apply</button>
							</c:if>
							<c:if test="${basket.discount != null}">
								<span class="input-xlarge uneditable-input">${basket.discount.description}</span>
								<button class="btn" id="removeDiscount"><i class="icon-remove-sign"></i></button>
							</c:if>
							</div>
						</div>
					</div>
					<div class="well">
						<h4>Total</h4>
						<hr>
						<div id="total" class="pull-right">
							<strong> £<fmt:formatNumber value="${total}"
									maxFractionDigits="2" minFractionDigits="2" />
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
		<jsp:include page="footer.jsp" />
	</div>

	<script src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
	<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#country').val('${basket.deliveryMethod.country.code}');
			$.ajax({
				url : '<c:url value="/shop/checkout/listdeliverymethods/${basket.deliveryMethod.country.code}"></c:url>',
				type : 'GET',
				datatype : 'JSON',
				success : function(json) {
					$('#shipping').append(
					$('<option>').text('Select One').attr('value', ''));
					$.each(json.deliverymethods,function(i, value) {
						$('#shipping').append($('<option>')
								.text(value.description + ' - £'+ value.cost)
								.attr('value',value.id));
					});
					$('#shipping').val('${basket.deliveryMethod.id}');
				}
			});
			
			$('body').on('touchstart.dropdown', '.dropdown-menu', function(e) {
				e.stopPropagation();
			});
		});

		// Handle a change of country.
		$(function() {
			$('#country').change(function() {
				var selectedCountry = $('#country').val();
				$('#shipping').empty();
				$('#shippingtotal').html('<strong>£0.00</strong>');
				$.ajax({
					url : '<c:url value="/shop/basket/setdeliverymethod/"></c:url>',
					type : 'GET',
					datatype : 'JSON',
					data : {
						id : ''
					},
					success : function(json) {
						$('#total').html('<strong>£'+ json.newTotal + '</strong>');
					}
				});

				$.ajax({
					url : '<c:url value="/shop/checkout/listdeliverymethods/"></c:url>' + selectedCountry,
					type : 'GET',
					datatype : 'JSON',
					success : function(json) {
						$('#shipping').append($('<option>').text('Select One').attr('value',''));
						$.each(json.deliverymethods,function(i,value) {
							$('#shipping')
								.append($('<option>')
										.text(value.description + ' - £'+ value.cost)
										.attr('value',value.id));
						});
					}
				});
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
						$('#shippingtotal').html('<strong>£'+ json.deliveryCost + '</strong>');
						$('#total').html('<strong>£'+ json.newTotal + '</strong>');
					}
				});
			});
		});
		
		$(document).on('click', '.dropdown-menu a', function() {
			document.location = $(this).attr('href');
		});
		
		// Handle the click of the discount button
		$('#applyDiscount').click(function() {
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
							$('#shippingtotal').html('<strong>£'+ json.deliveryCost + '</strong>');
							$('#total').html('<strong>£'+ json.newTotal + '</strong>');
							
							$('#discountNotification').empty();
							$('#discountNotification').html('<div class="alert alert-success">Discount applied. Your total has been updated.</div>');
							
							$('#discountControls').empty();
							$('#discountControls').html(
								'<span class="input-xlarge uneditable-input">' + json.discount.description +'</span><a class="btn btn-small" href="#"><i class="icon-star"></i></a>'
							);
						} else {
							$('#discountFields').addClass('error');
							$('#errors').html('<div class="alert alert-error">Please enter a valid discount code</div>');
						}
					}
				});
			}
		});
		
		
		// Handle the removal of the discount
		$('#removeDiscount').click(function() {
			$.ajax({
				url : '<c:url value="/shop/checkout/removediscount/"></c:url>',
				type : 'GET',
				datatype : 'JSON',
				success : function(json) {
						$('#shippingtotal').html('<strong>£'+ json.deliveryCost + '</strong>');
						$('#total').html('<strong>£'+ json.newTotal + '</strong>');
				}
			});
		});
	</script>
</body>
</html>
