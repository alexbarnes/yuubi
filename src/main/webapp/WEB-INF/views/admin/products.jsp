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
	<div id="under-header"></div>
	<!-- start: Header -->

	<div class="container-fluid">
		<div class="row-fluid">
			<c:set var="selected" value="products" scope="request"/>
			<jsp:include page="menu.jsp"/>
			<div id="content" class="span8">
				<div class="row-fluid">
					<div class="box span8">
					<h3>Products</h3>
						<table class="table table-bordered">
							<tr>
								<th>Code</th>
								<th>Description</th>
								<th>Category</th>
								<th>Stock</th>
								<th>Available</th>
							</tr>
							<c:forEach items="${products}" var="product">
								<tr id="row-${product.code}" class="<c:if test="${!product.onDisplay}">warning</c:if>">
								<td>${product.code}</td>
								<td>${product.title}</td>
								<td>${product.category.description}</td>
								<td>
									<form class="form-inline">
										<div class="input-append">
											<input id="number-${product.code}" type="text" name="number" value="${product.stockLevel}" class="input-mini"></input>
											<button class="btn" id="stock-update-${product.code}" type="button">Update</button>
										</div>
									</form>
								</td>
								<td><div id="switch-${product.code}" class="make-switch switch-small" data-on-label="Yes" data-off-label="No"><input type="checkbox"<c:if test="${product.onDisplay}">checked</c:if>></div></td>
							</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		<hr>
		<jsp:include page="footer.jsp"/>
	</div>
	<script
		src="<spring:url value='/resources/admin/js/jquery-1.9.1.min.js'/>"></script>
	<script src="<spring:url value='/resources/admin/js/bootstrap.js'/>"></script>
	<script
		src="<spring:url value='/resources/admin/js/bootstrap-switch.js'/>"></script>
	<script src="<spring:url value='/resources/admin/js/prettify.js'/>"></script>
	
	<script type="text/javascript">
	<c:forEach items="${products}" var="product">
		$('#switch-${product.code}').on('switch-change', function (e, data) {
			var status = data.value;
			if (status) {
				$.get('<spring:url value='/admin/product/show/${product.code}'/>', function(response) {});
				$( "#row-${product.code}").removeClass("warning");
			} else {
				$.get('<spring:url value='/admin/product/hide/${product.code}'/>', function(response) {});
				$( "#row-${product.code}").addClass("warning");
			}
		});
	</c:forEach>
	</script>
	
	<script type="text/javascript">
		<c:forEach items="${products}" var="product">
			$('#stock-update-${product.code}').on('click', function (e) {
				var number1 = $("#number-${product.code}" ).val();
				$.get('<spring:url value='/admin/stock/update/${product.code}'/>', {number: number1}).done(function(data) {
				  $("#number-${product.code}" ).val(data);
				});
			});
		</c:forEach>
	</script>
	<!-- end: JavaScript-->
</body>
</html>