<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="header.jsp" />
<div class="container">
	<div class="row">
		<div class="span12">
			<div class="row">
				<div class="span12">
					<ul class="breadcrumb">
						<li><a href="<spring:url value='/shop'/>"><i
								class="icon-home"></i></a> <span class="divider">/</span></li>
						<li class="active">Your Basket</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="span12">
			<h2 class="title">Your Basket</h2>
			<hr />
			<div id="contents"></div>
		</div>
	</div>
	<div class="row pull-right">
		<div class="span2">
			<button class="btn">Checkout</button>
		</div>
	</div>
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
			$.get('<spring:url value='/shop/basket/contents'/>', function(data) {
				$('#contents').html(data);
				
				// Update the total of the basket
				$.get('<spring:url value='/shop/basket/total'/>', function(data) {
					var html = '£' + data;
					$('#basketTotal').html(html)
				});
			});
		});
	}
</script>
</html>
