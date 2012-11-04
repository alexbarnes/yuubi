<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Required variables for the page and imports -->
<c:set var="readOnly" value="${screenMode == 'ENQUIRE'}" />
<jsp:include page="header.jsp" />

<!-- The main container for the page -->
<div class="container">
<h3>Preferences</h3>
</div>
<jsp:include page="footer.jsp" />