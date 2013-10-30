<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="header.jsp" />
<html>
<head>
<title>Error page</title>
</head>
<body>
	<div class="container">
	<div class="row" id="top-bar">
	<div class="span9">
		<ul id="links" class="nav nav-pills pull-left">
			<li><a href="<spring:url value='/shop'/>" title="Shop">Home</a></li>
			<li><a href="<spring:url value='/shop/contact'/>" title="Contact">Contact</a></li>
		</ul>
	</div>
</div>
		<div class="row">
			<div class="span12">
				<div class="navbar">
					<div class="navbar-inner-other no-border">
						<div class="container">
							<form id="command" class="navbar-search"
								action="/shop/product/search" method="post">
								<input type="text" class="input-large search-query"
									placeholder="search..." name="query">
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row show-grid">
			<div class="span12 logo">
				<img src="<spring:url value='/resources/shop/img/404.jpg'/>" />
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>