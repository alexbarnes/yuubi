<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Admin Home</title>
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
			<c:set var="selected" value="shop" scope="request" />
			<jsp:include page="menu.jsp" />
			<div id="content" class="span8">
				<div class="row-fluid">
					<div class="box span8">
						<h3>Shop Options</h3>
						<table class="table table-bordered">
							<tr>
								<td>Shop Status</td>
								<td>
									<div id="shop-switch" class="make-switch switch-medium"
										data-on-label="Open" data-off-label="Closed">
										<input type="checkbox" <c:if test="${open}">checked</c:if>>
									</div>
								</td>
							</tr>
							<tr>
								<td>Clear Caches</td>
								<td>
									<button class="btn btn" id="clear-caches" type="button">Clear</button>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div id="content" class="span8">
				<div class="row-fluid">
					<div class="box span8">
						<hr>
						<div class="row-fluid">
							<div class="box">
								<div class="row">
									<div class="form-horizontal">
									<div class="span5">
										<div class="control-group">
											<label class="control-label" for="inputEmail">Closed
												Message</label>
											<div class="controls">
												<textarea rows="3" name="closedMessage" id="closed-message">${closedMessage}</textarea>
											</div>
										</div>
										<div class="control-group">
											<div class="controls">
												<button type="button" class="btn" id="save-message">Save</button>
											</div>
										</div>
										</div>
										<div class="span5">
										<div class="control-group">
											<label class="control-label" for="inputEmail">Closed
												Message</label>
											<div class="controls">
												<textarea rows="3" name="closedMessage" id="closed-message">${closedMessage}</textarea>
											</div>
										</div>
										<div class="control-group">
											<div class="controls">
												<button type="button" class="btn" id="save-message">Save</button>
											</div>
										</div>
										</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		<hr>
		<jsp:include page="footer.jsp" />
	</div>
	<script
		src="<spring:url value='/resources/admin/js/jquery-1.9.1.min.js'/>"></script>
	<script src="<spring:url value='/resources/admin/js/bootstrap.js'/>"></script>
	<script
		src="<spring:url value='/resources/admin/js/bootstrap-switch.js'/>"></script>
	<script src="<spring:url value='/resources/admin/js/prettify.js'/>"></script>
	<script type="text/javascript">
		$('#shop-switch').on(
				'switch-change',
				function(e, data) {
					var status = data.value;
					if (status) {
						$.get('<spring:url value='/admin/shop/open'/>',
								function(response) {
								});
					} else {
						$.get('<spring:url value='/admin/shop/close'/>',
								function(response) {
								});
					}
				});

		$('#clear-caches').on(
				'click',
				function(e) {
					$.get('<spring:url value='/admin/caches/clear'/>').done(
							function(data) {
							});
				});

		$('#save-message').on('click', function(e) {
			var messageText = $('#closed-message').val();
			$.get('<spring:url value='/admin/shop/closedmessage/save'/>', {
				message : messageText
			}).done(function(data) {
				$('#closed-message').val(data);
			});
		});
	</script>
	<!-- end: JavaScript-->
</body>
</html>