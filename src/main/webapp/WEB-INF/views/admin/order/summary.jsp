<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Orders</title>
<link id="bootstrap-style"
	href="<spring:url value='/resources/admin/css/bootstrap.css'/>"
	rel="stylesheet">
<link href="<spring:url value='/resources/admin/img/favicon.png'/>"
	rel="shortcut icon" type="image/x-icon">
<link rel="stylesheet"
	href="<spring:url value='/resources/admin/css/bootstrap-switch.css'/>" />
<!-- end: CSS -->

<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
</head>
<body>
	<!-- start: Header -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
				</a> <a class="brand" href="<spring:url value='/admin/home'/>"><span>Yuubi
						Admin</span></a>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<c:set var="selected" value="orders" scope="request" />
			<jsp:include page="../menu.jsp" />
			<div id="content" class="span8">
				<div class="row-fluid">
					<div class="box span8">
						<h3>Recent Orders</h3>
						<table class="table table-bordered">
							<tr>
								<th>Reference</th>
								<th>Order Total</th>
								<th>Status</th>
								<th />
								<th />
							</tr>
							<c:forEach items="${orders}" var="localOrder">
								<tr id="tr-${localOrder.id}">
									<td>${localOrder.orderReference}</td>
									<td>${localOrder.currencyCode} ${localOrder.orderTotal}</td>
									<td id="summary-status">${localOrder.status == 'ENTERED' ? 'Entered' : 'Completed'}</td>
									<td><button class="btn btn-primary" type="button"
											id="view-${localOrder.id}">View Details</button></td>
									<td><a
										href="${paypalUrl}${localOrder.paypalTransactionId}"
										target="_blank"><button class="btn btn-primary"
												type="button">Open in Paypal</button></a></td>
								</tr>
							</c:forEach>
						</table>
						<div class="btn-group pull-right">
							<button class="btn">Prev</button>
							<button class="btn">Next</button>
						</div>
					</div>
				</div>
			</div>
			<div id="content" class="span8">
				<div id="order"></div>
			</div>
		</div>
		<div class="clearfix"></div>
		<hr>
		<jsp:include page="../footer.jsp" />
	</div>
	<script
		src="<spring:url value='/resources/admin/js/jquery-1.9.1.min.js'/>"></script>
	<script src="<spring:url value='/resources/admin/js/bootstrap.js'/>"></script>
	<script
		src="<spring:url value='/resources/admin/js/bootstrap-switch.js'/>"></script>
	<script src="<spring:url value='/resources/admin/js/prettify.js'/>"></script>

	<script type="text/javascript">
		<c:forEach items="${orders}" var="localOrder">
		$('#view-${localOrder.id}')
				.on(
						'click',
						function(e) {
							$
									.get(
											'<spring:url value='/admin/order/view/${localOrder.orderReference}'/>')
									.done(
											function(data) {
												$('#order').html(data);
												$('tr').removeClass("info");
												$('#tr-${localOrder.id}').addClass("info");
											});
						});
		</c:forEach>
	</script>
</body>
</html>