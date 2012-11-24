<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="header.jsp" />
<div class="container">
	<jsp:include page="menu.jsp" />
	<div class="row">
		<div class="row">
			<div class="span12">
				<div class="row">
					<div class="span12">
						<ul class="breadcrumb">
							<li><a href="<spring:url value='/shop'/>"><i
									class="icon-home"></i></a> <span class="divider">/</span></li>
							<li><a
								href="<spring:url value='/shop/category/view/${product.category.id}'/>">${product.category.description}</a><span
								class="divider">/</span></li>
							<li class="active">${product.title}</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="span9">
			<div class="row">
				<div class="span9">
					<h2 class="title">${product.title}</h2>
					<hr />
				</div>
			</div>
			<div class="row">
				<div class="span4">
					<a
						href="<spring:url value='/shop/product/primaryimage/${product.code}'/>"
						class="thumbnail" data-fancybox-group="group1"
						title="Description 1"><img alt=""
						src="<spring:url value='/shop/product/primaryimage/${product.code}'/>"></a>

					<!-- Other pictures go here -->
					<ul class="thumbnails small">
						<c:forEach items="${product.images}" var="image">
							<li class="span1"><a
								href="<spring:url value='/shop/product/image/${image.id}'/>"
								class="thumbnail" data-fancybox-group="group1"
								title="${image.description}"> <img
									src="<spring:url value='/shop/product/image/${image.id}'/>"
									alt=""></a></li>
						</c:forEach>
					</ul>
				</div>
				<div id="stock"></div>
			</div>
		</div>
		<c:if test="${product.set != null}">
			<div class="span9">
				<div class="row">
					<div class="span9">
						<h2 class="title">Matching products</h2>
						<hr />
					</div>
				</div>
				<div class="row">
					<div class="span4">
						<ul class="thumbnails listing-products">
							<c:forEach items="${product.set.items}" var="current">
								<c:if test="${current.code != product.code}">
									<li class="span3">
										<div class="product-box">
											<a
												href="<spring:url value='/shop/product/view/${current.code}'/>"><h4>${current.title}</h4></a>
											<a
												href="<spring:url value='/shop/product/view/${current.code}'/>"><img
												src="<spring:url value='/shop/product/primaryimage/${current.code}'/>" /></a>
											<p>${current.description}</p>
										</div>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	<jsp:include page="footer.jsp" />
</div>
<div class="modal" id="myModal" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h2 class="title" id="myModalLabel">Notify me when this product
			is back in stock</h2>
	</div>
	<form:form action="/shop/notification/add">
		<div class="modal-body">

			<fieldset>
				<label>e-mail</label>
				<div class="div_text">
					<input name="email" type="text" value="" class="span6">
				</div>
				<input type="hidden" name="productCode" value="${product.code}">
			</fieldset>
		</div>
		<div class="modal-footer">
			<button type="submit" class="btn">Confirm</button>
		</div>
	</form:form>
</div>

<script
	src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>
<script
	src="<spring:url value='/resources/shop/js/jquery.fancybox.js'/>">"></script>
<script>
			$(function () {
				$('#myTab a:first').tab('show');
				$('#myTab a').click(function (e) {
					e.preventDefault();
					$(this).tab('show');
				});
			});
			
			$(document).ready(function() {
				$('.thumbnail').fancybox({
					openEffect  : 'none',
					closeEffect : 'none',
					type : 'image'
				});
				
				$('.carousel').carousel({
                    interval: false
                });
				
				// Setup the stock level details
				$.get('<spring:url value='/shop/product/stock/${product.code}'/>', function(data) {
					$('#stock').html(data);
				});
			});
			
			$(function () { 
				$("#basket").popover({placement: 'bottom', content: 'Basket updated', trigger: 'manual'});  
			});
			
			// When the user clicks anywhere destroy the popover and recreate it for next time
			$('html').click(function(e) {
			    $('#basket').popover('destroy');
			    $("#basket").popover({placement: 'bottom', content: 'Basket updated', trigger: 'manual'});
			});
			
			// Handle the addition of a product to the basket. Update the summary and the stock level
			function addItem() {
				$.get('<spring:url value='/shop/basket/add/${product.code}'/>', function(data) {
					if (data == true) {
						// Update the basket total
						$.get('<spring:url value='/shop/basket/total'/>', function(data) {
							var html = '£' + data;
							$('#basketTotal').html(html);
						});
						
						$('#basket').popover('show');
					} 
					
					// Update the displayed stock level in either case
					$.get('<spring:url value='/shop/product/stock/${product.code}'/>', function(data) {
						$('#stock').html(data);
					});
				});
			};
		</script>
</body>
</html>