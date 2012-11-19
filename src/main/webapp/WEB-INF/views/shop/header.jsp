<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link href='http://fonts.googleapis.com/css?family=Marcellus+SC'
	rel='stylesheet' type='text/css'>
<meta charset="utf-8">
<title>Y&#362;BI</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<link href="<spring:url value='/resources/shop/css/bootstrap.min.css'/>"
	rel="stylesheet">
<link href="<spring:url value='/resources/shop/css/main.css'/>"
	rel="stylesheet" />
<link href="<spring:url value='/resources/shop/css/jquery.fancybox.css'/>"
	rel="stylesheet" />
<link rel="icon" 
      type="image/png" 
      href="<spring:url value='/resources/shop/img/favicon.ico'/>">
      
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>
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
		<!-- Start Header-->
		<div class="row show-grid">
			<div class="span3 logo">
				<a href="index.html"> <img alt="" src="<spring:url value='/resources/shop/img/logo.jpg'/>" />
				</a>
			</div>
			<div class="span5 offset4">
				<div class="row">
					<div class="span3"></div>
					<div class="span2">
						<div class="cart pull-right">
							<a id ="basket" href="<spring:url value='/shop/basket/show'/>" rel="popover">Basket</a><br />
							<p id="basketTotal">£<c:out value="${basket.total}"/></p>
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
										<li class="dropdown"><a href="#"
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
									<li class="search_form">
											<form:form action="/shop/product/search" cssClass="navbar-search pull-right">
												<input type="text" class="input-medium search-query" placeholder="search..." name="query">
											</form:form>
										</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>