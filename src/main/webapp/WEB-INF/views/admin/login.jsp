<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Login</title>
<!-- Styles -->
<link rel="stylesheet" type="text/css"
	href="<spring:url value='/resources/admin/css/bootstrap.css'/>" />
<link href="<spring:url value='/resources/admin/img/favicon.png'/>"
	rel="shortcut icon" type="image/x-icon">
<style type="text/css">
body {
	padding-top: 120px;
	padding-bottom: 40px;
}
</style>

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
	<div class="row-fluid">
		<div class="span4 offset4">
			<form name='f' action="<c:url value='j_spring_security_check'/>" method='POST' class="well" id="login-form">
				<legend>Login</legend>
				<c:if test="${not empty error}">
					<div class="alert alert-error">Your username and/or password
						is incorrect. Try again.</div>
				</c:if>
				<label class="offset2"></label> <label class="offset2">Username</label>
				<input type="text" id="j_username" name="j_username"
					class="input-large offset2 initial-focus"> <label
					class="offset2">Password</label> <input type="password"
					id="j_password" name="j_password" class="input-large offset2">
				<button type="submit" class="btn btn-inverse offset7">Login</button>

				<c:if test="${not empty error}">
					<div class="row-fluid">
						<div class="span12">
							<a href="<spring:url value='/admin/forgotpassword'/>"><i
								class="icon-user"></i> Forgot Password</a>
						</div>
					</div>
				</c:if>
			</form>

			<hr />
			<footer>
				<p>© Alex Barnes & Yubi Jewellery 2012</p>
			</footer>
		</div>
	</div>
	<script type="text/javascript">
        $(document).ready(function() {
                $("#login-form :input:visible:enabled:first").focus(); // choose first just in case
        });
</script>
</body>
</html>