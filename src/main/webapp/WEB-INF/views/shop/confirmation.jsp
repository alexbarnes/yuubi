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
						<li class="active">Checkout</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="span12">
			<h2 class="title">Confirm Purchase</h2>
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
	</div>
	<div class="row pull-right">
		<div class="span2">
			<a href="<spring:url value='/shop/checkout/complete'/>"><button class="btn">Confirm Order</button></a>
		</div>
	</div>
</div>
<script
	src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>
</html>