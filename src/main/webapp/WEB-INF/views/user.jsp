<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!-- Required variables for the page and imports -->
<c:set var="readOnly" value="${screenMode == 'ENQUIRE'}" />
<jsp:include page="header.jsp" />

<!-- The main container for the page -->
<div class="container">
	<div class="row-fluid">
		<div class="span6 offset3" id="form">
			<c:choose>
				<c:when test="${screenMode == 'ENQUIRE'}">
					<h2>View user</h2>
				</c:when>
				<c:when test="${screenMode == 'CREATE'}">
					<h2>Add user</h2>
				</c:when>
				<c:when test="${screenMode == 'UPDATE'}">
					<h2>Maintain user</h2>
				</c:when>
			</c:choose>
			<hr />
			<c:url var="url" value="/admin/user/save" />
			<form:form commandName="user" action="${url}">
				<div class="control-group" id="userNameGroup">
					<label class="control-label" for="userName">Username</label>
					<div class="controls" id="userNameControls">

						<!-- Can only set the username once -->
						<c:if test="${screenMode == 'CREATE'}">
							<form:input type="text" path="userName" cssClass="input-large" />
						</c:if>
						<c:if test="${screenMode != 'CREATE'}">
							<span class="input-large uneditable-input">${user.userName}</span>
						</c:if>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="name">Name</label>
					<div class="controls">
						<form:input type="text" path="name" cssClass="input-large" />
					</div>
				</div>

				<div class="control-group" id="emailGroup">
					<label class="control-label" for="emailAddress">E-mail
						Address</label>
					<div class="controls" id="emailControls">
						<form:input type="text" path="emailAddress" cssClass="input-large" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="name">Password</label>
					<div class="controls">
						<form:input type="password" path="password" cssClass="input-large" />
					</div>
				</div>


				<div class="control-group">
					<div class="controls">
						<label class="checkbox"> <form:checkbox path="enabled" />
							Enabled
						</label>
					</div>
				</div>

				<div class="control-group">
					<div class="controls">
						<label class="checkbox"><form:checkbox
								path="administrator" /> Administrator </label>
					</div>
				</div>

				<!-- Hidden fields which need to be provided to the model. Only required for updates -->
				<c:if test="${screenMode == 'UPDATE'}">
					<form:input type="hidden" path="userName" />
					<form:input type="hidden" path="version" />
				</c:if>

				<c:if test="${not readOnly}">
					<input name="screenMode" type="hidden" value="${screenMode}" />
				</c:if>

				<div class="control-group">
					<div class="controls ">
						<!-- We're in edit or create. Also show Save but Cancel can go to home or enquire  -->
						<c:if test="${not readOnly}">
							<button type="submit" class="btn">Save</button>

							<!-- Cancel should either show the component in edit mode or return home -->
							<c:if test="${screenMode == 'UPDATE'}">
								<a href="<spring:url value="/admin/user/view/${user.userName}"/>"
									role="button" class="btn btn-inverse" data-toggle="modal">Cancel</a>
							</c:if>
							<c:if test="${screenMode == 'CREATE'}">
								<a href="<spring:url value="/admin/home"/>" role="button"
									class="btn btn-inverse" data-toggle="modal">Cancel</a>
							</c:if>
						</c:if>

						<!--  Read only mode, we are enquiring and wish to edit -->
						<c:if test="${readOnly}">
							<a href="<spring:url value="/admin/user/edit/${user.userName}"/>"
								role="button" class="btn btn-inverse" data-toggle="modal">Edit</a>
						</c:if>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	<hr />
	<footer>
		<p>© Alex Barnes & Yubi Jewellery 2012</p>
	</footer>
</div>
</div>

<script type="text/javascript">
	$('#userName').blur(
			function() {
				if ($("#userName").val() != '') {
					$.ajax({
						type : "GET",
						url : "<spring:url value='/admin/user/checkduplicate'/>"
								+ "/"
								+ encodeURIComponent($("#userName").val()),
						success : function(msg) {

							// If the username is a dupe and the error is not showing - show it
							if (msg.duplicate == true) {
								$('#userNameGroup').removeClass('success');
								$('#userNameGroup').addClass('error');
							}

							// If the username is not a dupe and the error is showing - remove it
							if (msg.duplicate == false) {
								$('#userNameGroup').addClass('success');
							}
						}
					});
				}

				// If we have a blank user name and the error is showing - remove it
				if ($("#usernameError").length > 0
						&& $("#userName").val() == '') {
					$('#usernameError').remove();
				}
			});

	// Do the same for e-mail
	$('#emailAddress').blur(
			function() {
				if ($("#emailAddress").val() != '') {
					$.ajax({
						type : "GET",
						url : "<spring:url value='/admin/user/checkduplicateemail'/>"
								+ "?address="
								+ encodeURIComponent($("#emailAddress").val()),
						success : function(msg) {

							// If the username is a dupe and the error is not showing - show it
							if (msg.duplicate == true) {
								$('#emailGroup').removeClass('success');
								$('#emailGroup').addClass('error');
							}

							// If the username is not a dupe and the error is showing - remove it
							if (msg.duplicate == false) {
								$('#emailGroup').addClass('success');
							}
						}
					});
				}

				// If we have a blank user name and the error is showing - remove it
				if ($("#emailError").length > 0
						&& $("#emailAddress").val() == '') {
					$('#emailError').remove();
				}
			});
</script>
</body>