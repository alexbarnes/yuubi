<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>User </title>
<link id="bootstrap-style"
	href="<spring:url value='/resources/admin/css/bootstrap.css'/>"
	rel="stylesheet">
<link id="base-style"
	href="<spring:url value='/resources/admin/css/style.css'/>"
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
			<div class="span3">
				<div class="well sidebar-nav">
					<ul class="nav nav-list">
						<li><a href="<spring:url value='/admin/home'/>"><span> Home</span></a></li>
						<li><a href="<spring:url value='/admin/shop'/>"><span> Shop</span></a></li>
						<li><a href="<spring:url value='/admin/products'/>"><span> Products</span></a></li>
						<li class="active"><a href="<spring:url value='/admin/user'/>"><span> User</span></a></li>
						<li class="divider"></li>
						<li><a href="<spring:url value='/admin/logout'/>"><span> Logout</span></a></li>
					</ul>
				</div>
			</div>
			<div id="content" class="span8">
				<div class="row-fluid">
					<h1>User: <sec:authentication property="principal.name" /></h1>
					<hr>
					<h2>Change Password</h2>
					<form:form class="form-horizontal" action="/admin/user/password/change" commandName="password">
					<div class="control-group">
    					<label class="control-label" for="password1">Password</label>
    					<div class="controls">
      						<form:input path="password1"/>
    					</div>
  					</div>
  					<div class="control-group">
    					<label class="control-label" for="password2">Password</label>
    					<div class="controls">
      						<form:input path="password2"/>
    					</div>
  					</div>
					<form:hidden path="username"/>
					<div class="control-group">
    					<div class="controls">
      						<button type="submit" class="btn">Update</button>
    					</div>
  					</div>
					</form:form>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		<hr>

		<footer>
			
		</footer>

	</div>
	<script
		src="<spring:url value='/resources/admin/js/jquery-1.9.1.min.js'/>"></script>
	<script src="<spring:url value='/resources/admin/js/bootstrap.js'/>"></script>
	<script
		src="<spring:url value='/resources/admin/js/bootstrap-switch.js'/>"></script>
	<script src="<spring:url value='/resources/admin/js/prettify.js'/>"></script>
	<script type="text/javascript">
	$('#shop-switch').on('switch-change', function (e, data) {
		var status = data.value;
		if (status) {
			$.get('<spring:url value='/admin/shop/open'/>', function(response) {});
		} else {
			$.get('<spring:url value='/admin/shop/close'/>', function(response) {});
		}
	});
	</script>
	<!-- end: JavaScript-->
</body>
</html>