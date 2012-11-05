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

<!DOCTYPE html>
<html lang="en">
<head>
<link href='http://fonts.googleapis.com/css?family=Marcellus+SC'
	rel='stylesheet' type='text/css'>
<meta charset="utf-8">
<title>YUBI JEWELLERY</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<link href="<spring:url value='/resources/shop/css/bootstrap.min.css'/>"
	rel="stylesheet">
<link href="<spring:url value='/resources/shop/css/main.css'/>"
	rel="stylesheet" />


<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>
<body>
	<div class="container">
		<div class="row" id="top-bar">
			<div class="span9">
				<ul id="links" class="nav nav-pills pull-left">
					<li class="active"><a href="index.html" title="Bitsy Shop">Home</a></li>
					<li><a href="products.html" title="All specials">Gallery</a></li>
					<li><a href="contact.html" title="Contact">Contact</a></li>
				</ul>
			</div>
		</div>
		<!-- Start Header-->
		<div class="row show-grid">
			<div class="span3 logo">
				<a href="index.html"> <img alt="" src="img/logo.png" />
				</a>
			</div>
			<div class="span5 offset4">
				<div class="row">
					<div class="span3"></div>
					<div class="span2">
						<div class="cart pull-right">
							<a href="<spring:url value='/shop/basket/show'/>">Basket</a><br />2
							items
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- End Header-->
		<!-- Start Navigation -->
		<div class="row">
			<div class="span12">
				<div class="navbar" id="main-menu">
					<div class="navbar-inner">
						<div class="container">
							<div class="nav-collapse">
								<ul class="nav pull-left" id="nav">
									<c:forEach items="${menu}" var="current">
										<li class="dropdown"><a href="products.html"
											class="dropdown-toggle" data-toggle="dropdown">${current.description}<b
												class="caret"></b></a>
											<ul class="dropdown-menu">
												<c:forEach items="${current.childCategories}" var="child"
													varStatus="status">
													<li><a
														href="<spring:url value='/shop/category/view/${child.id}'/>">${child.description}</a></li>
													<c:if test="${status.last == false}">
														<li class="divider"></li>
													</c:if>
												</c:forEach>
											</ul></li>
									</c:forEach>
									<li><a href="#">Gift Vouchers</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- End Navigation -->
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
						<a href="<spring:url value='/shop/product/primaryimage/${product.code}'/>" class="thumbnail"
							data-fancybox-group="group1" title="Description 1"><img
							alt="" src="<spring:url value='/shop/product/primaryimage/${product.code}'/>"></a>
							
						<!-- Other pictures go here -->
						<ul class="thumbnails small">
							<c:forEach items="${product.images}" var="image">
								<li class="span1">
								<a href="<spring:url value='/shop/product/image/${image.id}'/>"
									class="thumbnail" data-fancybox-group="group1"
									title="Description 2">
									<img src="<spring:url value='/shop/product/image/${image.id}'/>" alt=""></a>
								</li>
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
	<script src="<spring:url value='/resources/shop/js/jquery.fancybox.js'/>">"></script>
		<script>
			$(function () {
				$('#myTab a:first').tab('show');
				$('#myTab a').click(function (e) {
					e.preventDefault();
					$(this).tab('show');
				})
			})
			$(document).ready(function() {
				$('.thumbnail').fancybox({
					openEffect  : 'none',
					closeEffect : 'none'
				});
				
				$('.carousel').carousel({
                    interval: false
                });
			});
		</script>
</body>
</html>
