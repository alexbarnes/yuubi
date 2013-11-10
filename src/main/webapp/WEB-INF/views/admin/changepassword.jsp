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
		<form:form class="form-horizontal" action="/admin/password/change" commandName="password">
		<legend>Change Password</legend>
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