<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- Loop over the basket items and display them -->
<div class="row">
	<div class="span12">
		<h2 class="title">Your Basket</h2>
		<hr />
	</div>
</div>
<c:choose>
	<c:when test="${fn:length(basket.items) > 0}">
		<div class="entry">
			<table class="table table-bordered table-striped">
				<thead>
					<tr>
						<th></th>
						<th>Product Name</th>
						<th>Quantity</th>
						<th>Unit Price</th>
						<th>Total</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${basket.items}" var="item">
						<tr>

							<td><a id="remove"
								onclick="changeItemCount('<spring:url value='/shop/basket/remove/${item.key}/${item.value.number}'/>')"><i
									class="icon-remove"></i></a></td>
							<td><a
								href="<spring:url value="/shop/product/view/${item.key}" />">${item.value.productDescription}</a></td>
							<td>${item.value.number} <a><i class="icon-plus-sign"
									onclick="changeItemCount('<spring:url value='/shop/basket/add/${item.key}'/>')"></i></a>
								<c:if test="${item.value.number > 1}">
									<a><i class="icon-minus-sign"
										onclick="changeItemCount('<spring:url value='/shop/basket/remove/${item.key}/1'/>')"></i></a>
								</c:if>
							</td>
							<td>£<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${item.value.itemCost}"/></td>
							<td>£<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${item.value.totalCost}"/></td>
						</tr>
					</c:forEach>
					<!--  Totals -->
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td><strong>£<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${total}"/></strong></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="row pull-right">
			<a href="<spring:url value='/shop/checkout'/>"><button
					class="btn">
					<i class="icon-gift"></i> Checkout
				</button></a>
		</div>
	</c:when>
	<c:otherwise>
		<h4>There are currently no items in your basket</h4>
	</c:otherwise>
</c:choose>
