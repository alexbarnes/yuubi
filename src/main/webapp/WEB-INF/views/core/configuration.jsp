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
		<div class="span2">
			<div class="well sidebar-nav">
				<ul class="nav nav-list">
					<li class="nav-header">Configuration</li>
					<li class="active"><a href="#">Main</a></li>
					<li class="nav-header">Status</li>
					<li><a href="#">Database</a></li>
					<li><a href="#">Mail</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<jsp:include page="footer.jsp" />