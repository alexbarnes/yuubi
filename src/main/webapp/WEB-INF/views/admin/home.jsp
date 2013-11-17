<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
			<c:set var="selected" value="home" scope="request"/>
			<jsp:include page="menu.jsp"/>
			<div id="content" class="span8">
				<div class="row-fluid">
					<div class="box span8">
					<h3>Welcome to the Yuubi Admin Pages</h3>
					
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
	<!-- end: JavaScript-->
</body>
</html>