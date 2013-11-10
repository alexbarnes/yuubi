<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="title" scope="request" value="Y&#362;BI - Thank You"></c:set>
<jsp:include page="header.jsp" />
<body>
	<div class="container">
		<div class="row show-grid">
			<div class="span12 logo">
				<a href="<spring:url value='/shop'/>"> <img alt=""
					src="<spring:url value='/resources/shop/img/logo3.jpg'/>" />
				</a>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<h2 class="title">Order completed</h2>
				<hr />
				<p>Thank you for placing your order with Y&#362;BI. We are now working to ship your order as quickly as possible.</p>
				<p>If you need to contact us about this order for any reason please use the order reference ${order}.</p>
			</div>
		</div>
		<hr>
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
