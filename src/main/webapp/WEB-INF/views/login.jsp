<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Styles -->
<link rel="stylesheet" type="text/css" href="<spring:url value='/resources/css/bootstrap.css'/>" />
<link href="<spring:url value='/resources/img/favicon.png'/>" rel="shortcut icon" type="image/x-icon">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
	<div class="row">
		<div class="span6 offset3">
			<div class="well">
				<form name='f' action="<c:url value='j_spring_security_check' />" method='POST' class="form-horizontal">
					<div class="control-group">
						<label class="control-label" for="j_username">Username</label>
						<div class="controls">
							<input type="text" id="j_username" name="j_username">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="j_password">Password</label>
						<div class="controls">
							<input type="password" id="j_password" name="j_password">
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="_spring_security_remember_me">Remember me</label>
						<div class="controls">
							<input type="checkbox" name="_spring_security_remember_me">
						</div> 
					</div>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn btn-inverse" name="submit">Sign in</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>