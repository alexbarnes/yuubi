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
							<a href="cart.html">Basket</a><br />2 items
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
													<li><a href="products.html">${child.description}</a></li>
													<c:if test="${status.last == false}">
														<li class="divider"></li>
													</c:if>
												</c:forEach>
											</ul></li>

									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- End Navigation -->
	<div class="container">
            <div class="row">
                <div class="span12">
                    <div class="row">
						<div class="span12">
							<ul class="breadcrumb">
								<li>
									<a href="#"><i class="icon-home"></i></a> <span class="divider">/</span>
								</li>
								<li class="active">Your Cart</li>
							</ul>
						</div>
					</div>
                </div>
            </div>
            <div class="row">
                <div class="span12">                    
                    <h2 class="title">Your Cart</h2>
					<hr/>
					<div class="entry">
                	    
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>Remove</th>
									<th>Image</th>
									<th>Product Name</th>
									<th>Quantity</th>
									<th>Unit Price</th>
									<th>Total</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="center"><input type="checkbox" value="option1"></td>
									<td class="center"><a href="product.html"><img alt="" src="img/thumbs/1.jpg"></a></td>
									<td>Fusce id molestie massa</td>
									<td><input type="text" placeholder="1" class="input-mini"></td>
									<td>$2,350.00</td>
									<td>$2,350.00</td>
								</tr>			  
								<tr>
									<td class="center"><input type="checkbox" value="option1"></td>
									<td class="center"><a href="product.html"><img alt="" src="img/thumbs/2.jpg"></a></td>
									<td>Luctus quam ultrices rutrum</td>
									<td><input type="text" placeholder="1" class="input-mini"></td>
									<td>$1,150.00</td>
									<td>$2,450.00</td>
								</tr>				 
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td><strong>$3,600.00</strong></td>
								</tr>		  
							</tbody>
						</table>						
					</div>
                </div>
            </div>
			<div class="row pull-right">
				<div class="span2">
					<button class="btn btn-primary" type="submit">Update</button>
				</div>		  
				<div class="span2">
					<a href="<spring:url value='/shop/checkout'/>" class="pull-right">
					<img src="https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif" align="left" style="margin-right:7px;">
					</a>
				</div>
			</div>
        </div>	


	<script
		src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
	<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>
</html>
