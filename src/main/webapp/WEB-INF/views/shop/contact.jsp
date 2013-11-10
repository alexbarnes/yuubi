<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title" scope="request" value="Y&#362;BI - Contact Us"></c:set>
<jsp:include page="header.jsp" />
<script
	src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
<body>
	<div class="container">
		<jsp:include page="menu.jsp" />
		<div class="row">
			<div class="span12">
				<div class="row">
					<div class="span12">
						<ul class="breadcrumb">
							<li><a href='<spring:url value="/shop"/>'><i
									class="icon-home"></i></a> <span class="divider">/</span></li>
							<li class="active">Contact</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<c:choose>
					<c:when test="${success}">
					<div class="alert alert-success">Thank you for contacting us. We'll be in touch soon.</div>
					</c:when>
					<c:otherwise>
					<h2 class="title">Contact</h2>
				<hr />
				<p>Please use the following form to get in touch with us here at
					Yuubi. We'd love to hear fom you. If you're contacting us about an
					order please include the order number in your message.</p>
				<p>We look forward to hearing from you!</p>
				<hr>
						<form:form action="/shop/contact/send" cssClass="form-horizontal"
					commandName="message">
					<div class="control-group">
						<label class="control-label" for="fromName">Name</label> <i
							class='icon-asterisk'></i>
						<div class="controls">
							<form:input type="text" id="fromName" path="fromName" />  <form:errors path="fromName" cssClass="text-error"/>
						</div>
						<form:errors path="fromName">
							<script type="text/javascript">
								$("#fromName").parent().parent().addClass(
										"error");
							</script>
						</form:errors>
					</div>
					<div class="control-group">
						<label class="control-label" for="from">Email</label> <i
							class='icon-asterisk'></i>
						<div class="controls">
							<form:input type="text" id="from" path="from" />  <form:errors path="from" cssClass="text-error"/>
						</div>
						<form:errors path="from">
							<script type="text/javascript">
								$("#from").parent().parent().addClass("error");
							</script>
						</form:errors>
					</div>
					<div class="control-group">
						<label class="control-label" for="text">Message</label> <i
							class='icon-asterisk'></i>
						<div class="controls">
							<form:textarea rows="5" id="text" path="text" />  <form:errors path="text" cssClass="text-error"/>
						</div>
						<form:errors path="text">
							<script type="text/javascript">
								$("#text").parent().parent().addClass("error");
							</script>
						</form:errors>
					</div>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn">Send</button>
						</div>
					</div>
				</form:form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
	<script
		src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
	<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>
	<script>
		$(document).ready(function() {
			$('body').on('touchstart.dropdown', '.dropdown-menu', function(e) {
				e.stopPropagation();
			});

		});

		$(document).on('click', '.dropdown-menu a', function() {
			document.location = $(this).attr('href');
		});
	</script>
</body>
</html>