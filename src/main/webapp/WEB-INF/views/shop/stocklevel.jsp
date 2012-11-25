<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="span5">
	<address>
		<strong>Product Code:</strong> <span>${product.code }</span><br>
		<strong>Availability:</strong>
		<c:if test="${product.stockLevel  == 0}">
			<span>Out Of Stock - <a href="#myModal" role="button"
				data-toggle="modal">Notify Me</a></span>
		</c:if>
		<c:if test="${product.stockLevel > 0}">
			<span>${product.stockLevel}</span>
		</c:if>
		<br>
	</address>
	<h4>
		<strong>Price: £<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${product.unitPrice}"/></strong>
	</h4>
</div>
<c:if test="${product.stockLevel > 0}">
	<div class="span5">
		<button class="btn" id="addToBasket" onclick="addItem()" rel="popover">
		<i class="icon-shopping-cart"></i> Add to basket</button>
	</div>
	<br>
</c:if>