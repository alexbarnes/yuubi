<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:include page="header.jsp" />
<body>
	<div class="container">
		<div class="row" id="top-bar">
			<div class="span9">
				<ul id="links" class="nav nav-pills pull-left">
					<li><a href="<spring:url value='/shop'/>" title="Shop">Home</a></li>
					<li><a href="<spring:url value='/shop'/>" title="Contact">Contact</a></li>
				</ul>
			</div>
		</div>
		<!-- Logo -->
		<div class="row show-grid">
			<div class="span8 logo">
				<a href="<spring:url value='/shop'/>"> <img alt=""
					src="<spring:url value='/resources/shop/img/logo.jpg'/>" />
				</a>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<h2 class="title">Order completed</h2>
				<hr />
				<p>Thank you for placing your order with Y&#36;bi. A
					confirmation e-mail has been sent to the e-mail address provided and
					we are now working to ship your order as quickly as possible.</p>
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
