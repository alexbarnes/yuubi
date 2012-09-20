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
			<form:form commandName="user" action="/user/save">
				<div class="control-group">
					<label class="control-label" for="userName">Username</label>
					<div class="controls">

						<!-- Can only set the username once -->
						<c:if test="${screenMode == 'CREATE'}">
							<form:input type="text" path="userName" cssClass="input-large" />
						</c:if>
						<c:if test="${screenMode != 'CREATE'}">
							<span class="input-xlarge uneditable-input">${user.userName}</span>
						</c:if>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="name">Name</label>
					<div class="controls">
						<form:input type="text" path="name" cssClass="input-large" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="emailAddress">E-mail
						Address</label>
					<div class="controls">
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
					<input name="screenMode" type="hidden" />
				</c:if>

				<div class="control-group">
					<div class="controls ">
						<!-- We're in edit or create. Also show Save but Cancel can go to home or enquire  -->
						<c:if test="${not readOnly}">
							<button type="submit" class="btn">Save</button>

							<!-- Cancel should either show the component in edit mode or return home -->
							<c:if test="${screenMode == 'UPDATE'}">
								<a href="<spring:url value="/user/view/${user.userName}"/>"
									role="button" class="btn btn-inverse" data-toggle="modal">Cancel</a>
							</c:if>
							<c:if test="${screenMode == 'CREATE'}">
								<a href="<spring:url value="/home"/>" role="button"
									class="btn btn-inverse" data-toggle="modal">Cancel</a>
							</c:if>
						</c:if>

						<!--  Read only mode, we are enquiring and wish to edit -->
						<c:if test="${readOnly}">
							<a href="<spring:url value="/user/edit/${user.userName}"/>"
								role="button" class="btn btn-inverse" data-toggle="modal">Edit</a>
						</c:if>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	$('#userName')
			.blur(
					function() {
						if ($("#userName").val() != '') {
							$
									.ajax({
										type : "GET",
										url : "<spring:url value='/user/checkduplicate'/>"
												+ "/" + $("#userName").val(),
										success : function(msg) {
											if (msg.duplicate == true
													&& $("#usernameError").length == 0) {
												$('#form')
														.prepend(
																'<div class="alert alert-error" id="usernameError">The username you have entered is in use <button type="button" class="close" data-dismiss="alert">×</button></div>');
											}
										}
									});
						}
					});
</script>
</body>