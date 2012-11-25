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
					<li><a href="<spring:url value='/shop'/>" title="Bitsy Shop">Home</a></li>
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
					<strong>Twitter, Inc.</strong> <br>795 Folsom Ave, Suite 600<br>
					San Francisco, CA 94107<br> <abbr title="Phone">P:</abbr>
					(123) 456-7890
				</address>
				Estimated delivery date: 20th November 2012
				<hr />
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
							<!-- Shipping -->
							<tr>
								<td><strong>Shipping Total</strong></td>
								<td>${basket.deliveryMethod.country.description}</td>
								<td>${basket.deliveryMethod.description}</td>
								<td><strong>${basket.deliveryMethod.cost}</strong></td>
							</tr>
							<!-- Discounts -->
							<!--  Totals -->
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td id="total"><strong>£${total}</strong></td>
							</tr>
						</tbody>
					</table>
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
							class="btn">
							<i class="icon-ok-circle"></i> Confirm Order
						</button></a>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
	<script
		src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
	<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>
</body>
</html>