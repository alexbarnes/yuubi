<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="readOnly" value="${screenMode == 'ENQUIRE'}" />

<jsp:include page="header.jsp" />
<div class="container">
	<hr />
	<footer>
		<p>© Alex Barnes & Yubi Jewellery 2012</p>
	</footer>
</div>
<jsp:include page="footer.jsp" />
