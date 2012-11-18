<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="header.jsp" />
<div class="row">
	<div class="span12">
		<div class="row">
			<div class="span12">
				<ul class="breadcrumb">
					<li><a href="<spring:url value='/shop'/>"><i
							class="icon-home"></i></a> <span class="divider">/</span></li>
					<li class="active">${category.description}</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="span12">
		<div class="row">
			<div class="span9">
				<h2 class="title">${category.description}</h2>
				<hr />
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<ul class="thumbnails listing-products">
					<c:forEach items="${products}" var="product">
						<li class="span3">
							<div class="product-box">
								<a
									href="<spring:url value='/shop/product/view/${product.code}'/>"><h4>${product.title}</h4></a>
								<a
									href="<spring:url value='/shop/product/view/${product.code}'/>"><img
									src="<spring:url value='/shop/product/primaryimage/${product.code}'/>" /></a>
								<p>${product.description}</p>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>

<footer>
	<hr>
	<div class="row">
		<div class="span3">
			<div class="company_info">
				<h4 class="logo-title">
					<span>Y&#362;bi</span>Jewellery
				</h4>
			</div>
		</div>
		<div class="span3">
			<h4>Information</h4>
			<ul>
				<li><a href="about.html">About Us</a></li>
				<li><a href="#">Delivery Information</a></li>
				<li><a href="#">Privacy Policy</a></li>
				<li><a href="#">Terms &amp; Conditions</a></li>
			</ul>
		</div>
		<div class="span3">
			<h4 class="logo-title">Connect with us</h4>
			<a href="#">Facebook</a> <br /> <a href="#">Twitter</a>
		</div>
	</div>
</footer>
</div>

<script
	src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>
</body>
</html>
