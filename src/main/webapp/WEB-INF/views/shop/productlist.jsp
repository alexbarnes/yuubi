<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="header.jsp" />
<body>
	<div class="container">
		<jsp:include page="menu.jsp" />
		<div class="row">
			<div class="span12">
				<div class="row">
					<div class="span12">
						<ul class="breadcrumb">
							<li><a href="<spring:url value='/shop'/>"><i
									class="icon-home"></i></a> <span class="divider">/</span></li>
							<li class="active">${active}</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<div class="row">
					<div class="span9">
						<h2 class="title">${active}</h2>
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
											href="<spring:url value='/shop/product/view/${product.code}/${product.urlName}'/>"><h4>${product.title}</h4></a>
										<a
											href="<spring:url value='/shop/product/view/${product.code}/${product.urlName}'/>"><img
											src="<spring:url value='/shop/product/primaryimage/${product.code}'/>" /></a>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
	<script
		src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
	<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>
	<script>
		$(document).ready(function() {
			$('body').on('touchstart.dropdown', '.dropdown-menu', function(e) {
				e.stopPropagation();
			});

		});

		$(document).on('click', '.dropdown-menu a', function() {
			document.location = $(this).attr('href');
		});
	</script>
</body>
</html>