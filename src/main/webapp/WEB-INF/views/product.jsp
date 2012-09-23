<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Set some variables -->
<c:set var="readOnly" value="${screenMode == 'ENQUIRE'}" />

<jsp:include page="header.jsp" />
<div class="container">
	<div id="form"></div>
	<hr />
	<footer>
		<p>© Alex Barnes & Yubi Jewellery 2012</p>
	</footer>
</div>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$
								.ajax({
									type : "GET",
									url : "http://localhost:8080/product/components",
									data: ({screenMode : "${screenMode}", code : "${product.code}"}),
									success : function(response) {
										$("#form").html(response);
									}
								});
					});

	function addComponent() {
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/product/component/add?screenMode=${screenMode}",
			data : $("#productForm").serialize(),
			success : function(response) {
				$("#form").html(response);
			}
		});
	}

	function removeComponent(index) {
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/product/component/remove/" + index + "?screenMode=${screenMode}",
			data : $("#productForm").serialize(),
			success : function(response) {
				$("#form").html(response);
			}
		});
	}
</script>
<jsp:include page="footer.jsp" />