<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<c:set var="title" scope="request" value="Y&#362;BI - ${product.title}"></c:set>
<jsp:include page="header.jsp" />
<body>
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
								<li><a href="<spring:url value='/shop/category/view/${product.category.id}/${product.category.urlName}'/>">${product.category.description}</a><span
									class="divider">/</span></li>
								<li class="active">${product.title}</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="span12">
				<div class="row">
					<div class="span12">
						<h2 class="title">${product.title}</h2>
						<hr />
					</div>
				</div>
				<div class="row">
					<div class="span4">
						<a
							href="<spring:url value='/image/product/primary/${product.code}'/>"
							class="thumbnail" data-fancybox-group="group1"
							title="${product.title}"><img
							src="<spring:url value='/image/product/primary/${product.code}'/>"></a>

						<!-- Other pictures go here -->
						<ul class="thumbnails small">
							<c:forEach items="${product.images}" var="image">
								<c:if test="${image.primaryImage == false}">
									<li class="span1"><a
										href="<spring:url value='/image/product/${image.id}'/>"
										class="thumbnail" data-fancybox-group="group1"
										title="${image.description}"> <img
											src="<spring:url value='/image/product/${image.id}'/>"
											alt=""></a></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					<div class="span8"><p>${product.productDescription}</p></div>
					<c:if test="${product.goldFilledWires}">
					<div class="span8">
					<p>*Gold-filled earwires contain 1/20 gold content equating to an approximately x100 thicker coating of gold than gold-plated. 
					The difference can be seen visually but also results in substantially greater durability, and are recommended for those with metal allergies.</p>
					</div>
					</c:if>
					<div class="span8"><hr></div>
					<c:if test="${product.stockLevel  == 0}">
					<div class="span8">
						<h5>Out Of Stock</h5>
						<a href="#myModal" role="button"
							data-toggle="modal"><button class="btn" id="addToBasket" rel="popover"><i class="icon-envelope"></i> Notify Me</button></a>
							</div>
					</c:if>
					<c:if test="${product.stockLevel > 0}">
						<div class="span8">
						<h5>
							<c:set var="currency" value="${sessionScope.currency}"/>
							<strong><c:out value="${sessionScope.currency.symbol}"></c:out> <fmt:formatNumber value="${product.getPriceInCurrency(currency)}" /></strong>
						</h5>
						<c:if test="${open}">
							<button class="btn" id="addToBasket" onclick="addItem()"
								rel="popover">
								<i class="icon-shopping-cart"></i> Buy
							</button>
						</c:if>
						</div>
						<br>
					</c:if>
				</div>
			</div>
			<c:if test="${product.set != null}">
				<div class="span9">
					<div class="row">
						<div class="span9">
							<h2 class="title">Matching products:
								${product.set.description}</h2>
							<hr />
						</div>
					</div>
					<div class="row">
						<div class="span12">
							<ul class="thumbnails listing-products">
								<c:forEach items="${product.set.items}" var="current">
									<c:if test="${current.code != product.code and current.onDisplay}">
										<li class="span3">
											<div class="product-box">
												<a
													href="<spring:url value='/shop/product/view/${current.code}/${current.urlName}'/>"><h4>${current.title}</h4></a>
												<a
													href="<spring:url value='/shop/product/view/${current.code}/${current.urlName}'/>"><img
													src="<spring:url value='/image/product/primary/${current.code}'/>" /></a>
												<p>${current.title}</p>
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
				<hr>
				<p>Your email address will only be used for this one time notification. You will not be subscribed
				 to any additional mailing lists.</p>
		</div>
		<form:form action="/shop/notification/add">
			<div class="modal-body">
				<div class="control-group">
					<label class="control-label" for="inputIcon">Email address</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-envelope"></i></span> <input
								class="span6" id="inputIcon" type="text" value="" name="email">
							<input type="hidden" name="productCode" value="${product.code}">
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok-circle icon-white"></i> Confirm
				</button>
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
			});
			
			$(function () { 
				$("#basket").popover({placement: 'bottom', content: 'Basket updated', trigger: 'manual'});  
			});
			
			// When the user clicks anywhere destroy the popover and recreate it for next time
			$('html').click(function(e) {
			    $('#basket').popover('destroy');
			    $("#basket").popover({placement: 'bottom', content: 'Basket updated', trigger: 'manual'});
			});
			
			// Handle the addition of a product to the basket.
			function addItem() {
				$.get('<spring:url value='/shop/basket/add/${product.code}'/>', function(data) {
					if (data == true) {
						// Update the basket total
						$.get('<spring:url value='/shop/basket/total'/>', function(data) {
							var html = '<c:out value="${sessionScope.currency.symbol}"/>' + data;
							$('#basketTotal').html(html);
						});
						
						$('#basket').popover('show');
					} 
				});
			};
		</script>
	<script>
		$(document).ready(function() {
			$('body').on('touchstart.dropdown', '.dropdown-menu', function(e) {
				e.stopPropagation();
			});

		});

		$(document).on('click', '.dropdown-menu a', function() {
			document.location = $(this).attr('href');
		});
	</script>
</body>
</html>