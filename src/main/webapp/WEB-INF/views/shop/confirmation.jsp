<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<c:set var="title" scope="request" value="Y&#362;BI - Confirm Purchase"></c:set>
<c:set var="currency" value="${sessionScope.currency}" />
<jsp:include page="header.jsp" />
<body>
	<div class="container">
		<div class="row show-grid">
			<div class="span12 logo">
				<a href="<spring:url value='/shop'/>"> <img alt=""
					src="<spring:url value='/resources/shop/img/logo3.jpg'/>" />
				</a>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<div class="row">
					<div class="span12">
						<ul class="breadcrumb">
							<li><a href="<spring:url value='/shop'/>"><i
									class="icon-home"></i></a> <span class="divider">/</span></li>
							<li class="active">Confirm</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<h2 class="title">Confirm Purchase</h2>
				<hr />
				<h4>Delivery Address</h4>
				<address>
					<strong>${transaction.customer.name}</strong> <br>
					${transaction.customer.addressLine1}
					<c:if test="${transaction.customer.addressLine2 != null}">, ${transaction.customer.addressLine2 }</c:if>
					<br> ${transaction.customer.city},
					<c:if test="${transaction.customer.county != null}">${transaction.customer.county} </c:if>${transaction.customer.postCode}
					<br>
				</address>
				<hr />
				<h2 class="title">Special Instructions</h2>
				<br>
				<c:choose>
					<c:when test="${transaction.message == null}">
					None
					</c:when>
					<c:otherwise>${transaction.message}</c:otherwise>
				</c:choose>
				
				<hr />
				<c:if test="${stockIssue}">
				<div class="alert alert-error">We are very sorry but we have sold out of one or more of the products in your basket since you started shopping.
				</div>
				</c:if>
				<h4>Order</h4>
				<div class="entry">
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>Item</th>
								<th>Quantity</th>
								<th>Unit Price</th>
								<th>Total</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${basket.items}" var="item">
								<tr>
									<td><c:if test="${item.value.notEnoughStock}"><i class="cus-exclamation"></i> </c:if>${item.value.productDescription}</td>
									<td>${item.value.number}</td>
									<td><fmt:formatNumber value="${item.value.product.getPriceInCurrency(currency)}" /></td>
									<td><fmt:formatNumber value="${item.value.getLineCost(currency)}" /></td>
								</tr>
							</c:forEach>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								</tr>
							<c:if test="${basket.discount != null}">
								<tr>
									<td>${basket.discount.description}</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td id="total">- ${discountAmount}</td>
								</tr>
							</c:if>
							<tr>
									<td>${basket.deliveryMethod.description}</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td id="total">${basket.deliveryMethod.cost}</td>
								</tr>
								<tr>
									<td>Total</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td><strong> <c:out value="${sessionScope.currency.symbol}" />
								<fmt:formatNumber value="${total}" minFractionDigits="2" maxFractionDigits="2" />
							</strong></td>
								</tr>
						</tbody>
					</table>
				</div>
			</div>
				<div class="span3">
					<a href="<spring:url value='/shop/basket/show'/>"><button
							class="btn">
							<i class="icon-shopping-cart"></i> Return to Basket
						</button></a>
				</div>
				
				<!-- If there isn't enough stock hide the complete order button. -->
				<c:if test="${!stockIssue}">
					<div class="span2 offset7">
						<a href="<spring:url value='/shop/checkout/complete'/>"><button
								class="btn">
								<i class="icon-ok-circle icon"></i> Confirm Order
							</button></a>
					</div>
				</c:if>
		</div>
		<hr>
		<jsp:include page="footer.jsp" />
	</div>
	<script
		src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
	<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>
	<script>
		$(document).ready(function() {
			$('body').on('touchstart.dropdown', '.dropdown-menu', function(e) {
				e.stopPropagation();
			});

		});

		$(document).on('click', '.dropdown-menu a', function() {
			document.location = $(this).attr('href');
		});
	</script>
</body>
</html>