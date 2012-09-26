<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Required variables for the page and imports -->
<c:set var="readOnly" value="${screenMode == 'ENQUIRE'}" />
<jsp:include page="header.jsp" />

<!-- The main container for the page -->
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span8 offset2">
			<!-- The main form backed by the component object - this posts to the save method -->
			<c:url var="url" value="/component/save" />
			<form:form cssClass="form-horizontal" commandName="component"
				action="${url}">
				<div class="row-fluid well">
					<c:choose>
						<c:when test="${screenMode == 'ENQUIRE'}">
							<legend>View component</legend>
						</c:when>
						<c:when test="${screenMode == 'CREATE'}">
							<legend>Add component</legend>
						</c:when>
						<c:when test="${screenMode == 'UPDATE'}">
							<legend>Maintain component</legend>
						</c:when>
					</c:choose>

					<!-- The left hand column of fields -->
					<div class="span6">

						<!-- The component name -->
						<c:if test="${not empty descriptionErrors}">
							<div class="control-group error">
						</c:if>

						<c:if test="${empty descriptionErrors}">
							<div class="control-group">
						</c:if>
						<label class="control-label" for="name">Name</label>
						<div class="controls">
							<!--  Read only - display a span -->
							<c:if test="${readOnly}">
								<span class="input-xlarge uneditable-input">${component.description}</span>
							</c:if>

							<!--  Editable display a field -->
							<c:if test="${not readOnly}">
								<form:input type="text" path="description"
									cssClass="input-xlarge" />
							</c:if>
						</div>
					</div>


					<!-- The supplier name field -->
					<div class="control-group">
						<label class="control-label" for="name">Supplier</label>
						<div class="controls">
							<!--  Read only - display a span -->
							<c:if test="${readOnly}">
								<span class="input-xlarge uneditable-input">${component.supplier.name}</span>
							</c:if>

							<!--  Editable display a field -->
							<c:if test="${not readOnly}">
								<input type="text" class="input-xlarge supplier"
									autocomplete="off" value="${component.supplier.name}" />
								<form:input path="supplierId" type="hidden" />
							</c:if>
						</div>
					</div>

					<!-- Supplier reference for the component -->
					<div class="control-group">
						<label class="control-label" for="name">Supplier Code</label>
						<div class="controls">
							<!--  Read only - display a span -->
							<c:if test="${readOnly}">
								<span class="input-xlarge uneditable-input">${component.supplierProductCode}</span>
							</c:if>

							<!--  Editable - display a field -->
							<c:if test="${not readOnly}">
								<form:input type="text" path="supplierProductCode"
									cssClass="input-xlarge" />
							</c:if>
						</div>
					</div>
				</div>
				<!-- The right hand column of fields -->
				<div class="span6">

					<!-- The current stock level -->
					<div class="control-group">
						<label class="control-label" for="name">Current Stock</label>
						<div class="controls">
							<!--  Read only - display a span -->
							<c:if test="${screenMode != 'CREATE'}">
								<span class="input-small uneditable-input">${component.stock}</span>
								<c:if test="${readOnly}">
									<a href="#myModal" role="button" class="btn btn-inverse"
										data-toggle="modal">Update</a>
								</c:if>
							</c:if>

							<!--  Editable - display a field -->
							<c:if test="${screenMode == 'CREATE'}">
								<form:input type="text" path="stock" cssClass="input-small" />
							</c:if>
						</div>
					</div>

					<!-- The lower stock level - triggers an alert -->
					<div class="control-group">
						<label class="control-label" for="name">Stock Alert Limit</label>
						<div class="controls">
							<!--  Read only - display a span -->
							<c:if test="${readOnly}">
								<span class="input-small uneditable-input">${component.stockAlertLimit}</span>
							</c:if>

							<!--  Editable - display a field -->
							<c:if test="${not readOnly}">
								<form:input type="text" path="stockAlertLimit"
									cssClass="input-small" />
							</c:if>
						</div>
					</div>

					<!-- The supplier cost -->
					<div class="control-group">
						<label class="control-label" for="name">Supplier Cost</label>
						<div class="controls">
							<!--  Read only - display a span -->
							<c:if test="${readOnly}">
								<span class="input-medium uneditable-input">${component.cost}</span>
							</c:if>

							<!--  Editable - display a field -->
							<c:if test="${not readOnly}">
								<form:input type="text" path="cost" cssClass="input-medium" />
							</c:if>
						</div>
					</div>
				</div>

				<!-- Hidden fields which need to be provided to the model. Only required for updates -->
				<c:if test="${screenMode == 'UPDATE'}">
					<form:input type="hidden" path="id" />
					<form:input type="hidden" path="version" />
				</c:if>
		</div>

		<!--  The form controls -->
		<div class="row-fluid well controlContainer" >
			<div class="offset7">
				<!-- The form buttons, these depend on the screen mode -->
				<div class="control-group">
					<div class="controls ">
						<c:if test="${not readOnly}">
							<button type="submit" class="btn">Save</button>
							<!-- Cancel should either show the component in edit mode or return home -->
							<c:if test="${component.id > 0}">
								<a href="<spring:url value="/component/view/${component.id}"/>"
									role="button" class="btn btn-inverse" data-toggle="modal">Cancel</a>
							</c:if>
							<c:if test="${component.id == 0}">
								<a href="<spring:url value="/home"/>" role="button"
									class="btn btn-inverse" data-toggle="modal">Cancel</a>
							</c:if>
						</c:if>
						<c:if test="${readOnly}">
							<a href="<spring:url value="/component/edit/${component.id}"/>"
								role="button" class="btn btn-inverse" data-toggle="modal">Edit</a>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		</form:form>

		<c:url var="supplierurl" value="/supplier/search"></c:url>
		<!-- Javascript for the Typeahead field -->
		<script type="text/javascript">
			$('.supplier').typeahead({
				ajax : {
					url : '${supplierurl}',
					triggerLength : 3,
					method : 'GET'
				},

				itemSelected : function setSupplier(item, val, text) {
					$('input[name=supplierId]').val(val);
				},
			});
		</script>
		<hr />

		<!-- Footer -->
		<footer>
			<p>© Alex Barnes & Yubi Jewellery 2012</p>
		</footer>

		<!-- If we're in enquiry mode we can show the edit stock pop-up -->
		<c:if test="${screenMode == 'ENQUIRE'}">
			<div class="modal hide" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel">Update Stock Level</h3>
				</div>
				<c:url var="stockurl" value="/component/stockupdate"></c:url>
				<form:form cssClass="form-horizontal" commandName="stockupdate"
					action="${stockurl}">
					<div class="modal-body">

						<div class="control-group">
							<label class="control-label" for="name">Current</label>
							<div class="controls">
								<span class="input-small uneditable-input">${component.stock}</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="name">Updated</label>
							<div class="controls">
								<form:input type="text" path="newStockNumber"
									cssClass="input-small" />
							</div>
						</div>

						<c:set var="narrativeErrors">
							<form:errors path="narrative" />
						</c:set>
						<c:if test="${not empty narrativeErrors}">
							<div class="control-group error">
						</c:if>

						<c:if test="${empty narrativeErrors}">
							<div class="control-group">
						</c:if>
						<label class="control-label" for="name">Narrative</label>
						<div class="controls">
							<form:input type="text" path="narrative" cssClass="input-medium" />
						</div>
					</div>
			</div>

			<form:input type="hidden" path="componentId" />

			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
				<button type="submit" class="btn btn-inverse">Apply</button>
			</div>
			</form:form>
	</div>

	<!-- If we have set the showpopup variable to true show the popup on page load -->
	<c:if test="${showpopup}">
		<script type="text/javascript">
		$(window).load(function() {
			$('#myModal').modal('show');
		});
	</script>
	</c:if>
	</c:if>
</div>
<jsp:include page="footer.jsp" />