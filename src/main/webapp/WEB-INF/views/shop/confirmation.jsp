<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="header.jsp" />
<body>
	<div class="container">
		<div class="row" id="top-bar">
			<div class="span9">
				<ul id="links" class="nav nav-pills pull-left">
					<li><a href="<spring:url value='/shop'/>">Home</a></li>
					<li><a href="products.html" title="All specials">Gallery</a></li>
					<li><a href="contact.html" title="Contact">Contact</a></li>
				</ul>
			</div>
		</div>
		<div class="row show-grid">
			<div class="span3 logo">
				<a href="<spring:url value='/shop'/>"> <img alt=""
					src="<spring:url value='/resources/shop/img/logo.jpg'/>" />
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
							<li class="active">Complete</li>
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
					<strong>${transaction.customer.name}</strong> 
					<br>
					${transaction.customer.addressLine1}<c:if test="${transaction.customer.addressLine2 != null}">, ${transaction.customer.addressLine2 }</c:if>
					<br>
					${transaction.customer.city}, <c:if test="${transaction.customer.county != null}">${transaction.customer.county} </c:if>${transaction.customer.postCode}
					<br>
				</address>
				<hr />
				<h2 class="title">Special Instructions</h2>
				${transaction.message}
				<hr />
				<div class="row">
					<div class="span2 offset7 pull-right">
						<a href="<spring:url value='/shop/checkout/complete'/>"><button
								class="btn btn-primary">
								<i class="icon-ok-circle icon-white"></i> Confirm Order
							</button></a>
					</div>
				</div>
				<h4>Order Detail</h4>
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
									<td>${item.value.productDescription}</td>
									<td>${item.value.number}</td>
									<td>£${item.value.itemCost}</td>
									<td>£${item.value.totalCost}</td>
								</tr>
							</c:forEach>
							<c:if test="${basket.discount != null}">
							<tr>
								<td>Discount</td>
								<td>${basket.discount.description}</td>
								<td>&nbsp;</td>
								<td id="total"><strong>£${total}</strong></td>
							</tr>
							</c:if>
						</tbody>
					</table>
					<h4>Shipping</h4>
					<div class="well">
						<div id="shippingtotal" class="pull-right">
							<strong>${basket.deliveryMethod.description}: £<fmt:formatNumber
									value="${basket.deliveryMethod.cost}"  />
							</strong>
						</div>
					</div>
					<h4>Total</h4>
					<div class="well">
						<div id="shippingtotal" class="pull-right">
							<strong> £<fmt:formatNumber value="${total}"  />
							</strong>
						</div>
					</div>
				</div>
			</div>
			<div class="row pull-right">
				<div class="span3">
					<a href="<spring:url value='/shop/basket/show'/>"><button
							class="btn">
							<i class="icon-shopping-cart"></i> Return to Basket
						</button></a>
				</div>
				<div class="span2 offset7">
					<a href="<spring:url value='/shop/checkout/complete'/>"><button
							class="btn btn-primary">
							<i class="icon-ok-circle icon-white"></i> Confirm Order
						</button></a>
				</div>
			</div>
		</div>
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