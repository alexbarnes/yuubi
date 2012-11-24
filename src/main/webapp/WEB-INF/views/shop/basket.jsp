<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="header.jsp" />
<div class="container">
	<jsp:include page="menu.jsp" />
	<div class="row">
		<div class="span12">
			<div class="row">
				<div class="span12">
					<ul class="breadcrumb">
						<li><a href="<spring:url value='/shop'/>"><i
								class="icon-home"></i></a> <span class="divider">/</span></li>
						<li class="active"><i class="icon-shopping-cart"></i> Your
							Basket</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div id="contents"></div>
</div>


<script
	src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>

<script type="text/javascript">
	$(document).ready(function() {

		$.get('<spring:url value='/shop/basket/contents'/>', function(data) {
			$('#contents').html(data);
		});

	});

	function changeItemCount(url) {
		$.get(url, function(data) {
			// Refresh basket contents
			$.get('<spring:url value='/shop/basket/contents'/>',
					function(data) {
						$('#contents').html(data);

						// Update the total of the basket
						$.get('<spring:url value='/shop/basket/total'/>',
								function(data) {
									var html = '£' + data;
									$('#basketTotal').html(html);
								});
					});
		});
	}
</script>
</html>
