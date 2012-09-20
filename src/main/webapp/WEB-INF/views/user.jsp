<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Required variables for the page and imports -->
<c:set var="readOnly" value="${screenMode == 'ENQUIRE'}" />
<jsp:include page="header.jsp" />

<!-- The main container for the page -->
<div class="container">
	<div class="row-fluid">
		<div class="span6 offset3" id="form">
			<form:form commandName="user" action="/user/save">
				<div class="control-group">
					<label class="control-label" for="name">Username</label>
					<div class="controls">
						<form:input type="text" path="userName" cssClass="input-large" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="name">Username</label>
					<div class="controls">
						<form:input type="text" path="userName" cssClass="input-large" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="name">E-mail Address</label>
					<div class="controls">
						<form:input type="text" path="emailAddress" cssClass="input-large" />
					</div>
				</div>

			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	$('#userName').blur(
			function() {
				if ($("#userName").val() != '') {
					$.ajax({
						type : "GET",
						url : "<spring:url value='/user/checkduplicate'/>"
								+ "/" + $("#userName").val(),
						success : function(msg) {
							if (msg.duplicate == true && $("#usernameError").length == 0) {
								$('#form').prepend('<div class="alert alert-error" id="usernameError">The username you have entered is in use <button type="button" class="close" data-dismiss="alert">×</button></div>');
							}
						}
					});
				}
			});
</script>
</body>