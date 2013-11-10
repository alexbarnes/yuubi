<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link href='https://fonts.googleapis.com/css?family=Marcellus+SC'
	rel='stylesheet' type='text/css'>
<meta charset="utf-8">
<title>${title}</title>
<meta name="description" content="">
<link href="<spring:url value='/resources/shop/css/bootstrap.min.css'/>"
	rel="stylesheet">
<c:choose>
	<c:when test="${sessionScope.mobile == true}">
		<link href="<spring:url value='/resources/shop/css/body_mobile.css'/>"rel="stylesheet" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="<spring:url value='/resources/shop/css/bootstrap-responsive.min.css'/>"rel="stylesheet" />
	</c:when>
	<c:otherwise>
		<link href="<spring:url value='/resources/shop/css/body_normal.css'/>"rel="stylesheet" />
	</c:otherwise>
</c:choose>
<link href="<spring:url value='/resources/shop/css/main.css'/>"
	rel="stylesheet" />
<link
	href="<spring:url value='/resources/shop/css/jquery.fancybox.css'/>"
	rel="stylesheet" />
<link
	href="<spring:url value='/resources/shop/css/bootstrap-formhelpers.min.css'/>"
	rel="stylesheet" />
<link
	href="<spring:url value='/resources/shop/css/cus-icons.css'/>"
	rel="stylesheet" />
<link rel="icon" type="image/png"
	href="<spring:url value='/resources/shop/img/favicon.ico'/>">

<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>
<script>
	(function(i, s, o, g, r, a, m) {
		i['GoogleAnalyticsObject'] = r;
		i[r] = i[r] || function() {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o), m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
	})(window, document, 'script', '//www.google-analytics.com/analytics.js',
			'ga');

	ga('create', 'UA-45183640-1', 'yuubi-jewellery.com');
	ga('send', 'pageview');
</script>