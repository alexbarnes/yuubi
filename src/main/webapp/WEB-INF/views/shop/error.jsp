<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="header.jsp" />
<html>
<head>
<title>Error page</title>
</head>
<body class="body_error">
	<div class="container">
		<div class="row">
			<div class="span12">
				<div class="navbar navbar-error">
					<div class="navbar-inner-other no-border">
						<div class="container">
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row show-grid">
			<div class="span12 logo">
				<a href="<spring:url value='/shop'/>">
					<img src="<spring:url value='/resources/shop/img/404.jpg'/>" />
				</a>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>