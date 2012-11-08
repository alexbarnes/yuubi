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
	<div class="span9">
		<div class="row">
			<div class="span9">
				<h2 class="title">${product.title}</h2>
				<hr />
			</div>
		</div>
		<div class="row">
			<div class="span4">
				<a
					href="<spring:url value='/shop/product/primaryimage/${product.code}'/>"
					class="thumbnail" data-fancybox-group="group1"
					title="Description 1"><img alt=""
					src="<spring:url value='/shop/product/primaryimage/${product.code}'/>"></a>

				<!-- Other pictures go here -->
				<ul class="thumbnails small">
					<c:forEach items="${product.images}" var="image">
						<li class="span1"><a
							href="<spring:url value='/shop/product/image/${image.id}'/>"
							class="thumbnail" data-fancybox-group="group1"
							title="Description 2"> <img
								src="<spring:url value='/shop/product/image/${image.id}'/>"
								alt=""></a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="span5">
				<address>
					<strong>Product Code:</strong> <span>${product.code }</span><br>
					<strong>Availability:</strong>
					<c:if test="${product.stockLevel  == 0}">
						<span>Out Of Stock</span>
					</c:if>
					<c:if test="${product.stockLevel > 0}">
						<span>${product.stockLevel}</span>
					</c:if>


					<br>
				</address>
				<h4>
					<strong>Price: £${product.unitPrice}</strong>
				</h4>
			</div>
			<div class="span5">
				<form class="form-inline">
					<label>Qty:</label> <input type="text" class="span1"
						placeholder="1">
					<button class="btn" type="submit">Add to cart</button>
				</form>
			</div>
			<div class="span5">
				<ul class="social">
					<li>
						<div class="fb-like" send="false" layout="button_count"
							data-href="example.org"></div> <script
							src="http://connect.facebook.net/en_US/all.js#xfbml=1"></script>
					</li>
					<li><a href="https://twitter.com/share"
						class="twitter-share-button" data-count="horizontal"
						data-url="http://dev.a-smart.vn/products/itab-a989i3g-goi-dien.html"
						data-text="iTab A989i(3G,Gọi Điện)" data-via="a-smart.vn">Tweet</a>
						<script type="text/javascript"
							src="http://platform.twitter.com/widgets.js"></script></li>
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
<script
	src="<spring:url value='/resources/shop/js/jquery.fancybox.js'/>">"></script>
<script>
			$(function () {
				$('#myTab a:first').tab('show');
				$('#myTab a').click(function (e) {
					e.preventDefault();
					$(this).tab('show');
				});
			})
			
			$(document).ready(function() {
				$('.thumbnail').fancybox({
					openEffect  : 'none',
					closeEffect : 'none',
					type : 'image'
				});
				
				$('.carousel').carousel({
                    interval: false
                });
			});
		</script>
</body>
</html>
