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
		<div class="row-fluid">
			<div class="span4 offset4">
				<c:set var="showForm" value="${success != 'true'}" />
				<c:if test="${notFound}">
					<div class="alert alert-error">Your e-mail address was not
						found</div>
				</c:if>
				<c:if test="${notSupplied}">
					<div class="alert alert-error">Please supply a valid e-mail
						address</div>
				</c:if>
				<c:if test="${success}">
					<div class="alert alert-success">Instructions have been sent
						to the supplied address</div>
				</c:if>
				<c:if test="${showForm}">
					<form action="<c:url value='/admin/forgotpassword' />" method='POST' class="well">
						<label class="offset2"></label> <label class="offset2">E-mail</label>
						<input type="text" id="email" name="email"
							class="input-large offset2">
						<button type="submit" class="btn btn-inverse offset7">Reset</button>
					</form>
				</c:if>
				<hr />
				<jsp:include page="footer.jsp"/>
			</div>
		</div>
</body>
</html>