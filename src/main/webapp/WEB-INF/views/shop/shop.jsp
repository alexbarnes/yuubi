<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<c:set var="title" scope="request" value="Y&#362;BI - Shop Home"></c:set>
<jsp:include page="header.jsp" />
<body>
	<div class="container">
		<jsp:include page="menu.jsp" />
		<div class="row hidden-phone">
			<div class="span12">
				<div id="myCarousel" class="carousel slide home">
					<div class="carousel-inner">
						<div class="item active">
							<a href="<spring:url value='/shop/category/view/7/short-drop'/>"> <img alt="Short Drop"
								src="<spring:url value='/resources/shop/img/home1.png'/>" /></a>
						</div>
						<div class="item">
							<a href="<spring:url value='/shop/category/view/11/pearl'/>">
								<img alt=""
								src="<spring:url value='/resources/shop/img/home2.png'/>" />
							</a>
						</div>
						<div class="item">
							<img alt="" src="<spring:url value='/resources/shop/img/home3.png'/>" />
						</div>
						<div class="item">
							<a href="<spring:url value='/shop/category/view/9/long-drop'/>"><img
								alt="" src="<spring:url value='/resources/shop/img/home4.png'/>" /></a>
						</div>
						<div class="item">
							<a href="<spring:url value='/shop/category/view/16/tama'/>"><img
								alt="" src="<spring:url value='/resources/shop/img/home5.png'/>" /></a>
						</div>
						<div class="item">
							<a href="<spring:url value='/shop/category/view/8/medium-drop'/>"><img
								alt="" src="<spring:url value='/resources/shop/img/home6.png'/>" /></a>
						</div>
						<div class="item">
							<a href="<spring:url value='/shop/category/view/18/sakura'/>"><img
								alt="" src="<spring:url value='/resources/shop/img/home7.png'/>" /></a>
						</div>
						<div class="item">
							<a href="<spring:url value='/shop/category/view/17/nami'/>"><img
								alt="" src="<spring:url value='/resources/shop/img/home8.png'/>" /></a>
						</div>
						<div class="item">
							<a href="<spring:url value='/shop/category/view/19/rose-garden'/>"><img
								alt="" src="<spring:url value='/resources/shop/img/home9.png'/>" /></a>
						</div>
					</div>
					<a class="left carousel-control" href="#myCarousel"
						data-slide="prev">‹</a> <a class="right carousel-control"
						href="#myCarousel" data-slide="next">›</a>
				</div>
				<div class="bg_slider"></div>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<ul class="nav nav-tabs home" id="myTab">
					<c:forEach items="${menu}" var="current">
						<li><a href="#tab${current.id}">${current.description}</a></li>
					</c:forEach>
				</ul>
				<div class="tab-content">
					<c:forEach items="${menu}" var="current">
						<div class="tab-pane active" id="tab${current.id}">
							<div class="row">
								<div class="span12">
									<ul class="thumbnails listing-products">
										<c:forEach items="${current.childCategories}" var="child"
											varStatus="status">
											<li class="span3">
												<div class="product-box">
													<a href="<spring:url value='/shop/category/view/${child.id}/${child.urlName}'/>"><h4>${child.description}</h4></a>
													<a href="<spring:url value='/shop/category/view/${child.id}/${child.urlName}'/>">
													<img src="<spring:url value='/image/category/${child.id}'/>" /></a>
												</div>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</c:forEach>

				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
	<script src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
	<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('.carousel').carousel({
				interval : 4000
			})

			$('#myTab a:first').tab('show');
			$('#myTab a').click(function(e) {
				e.preventDefault();
				$(this).tab('show');
			})
		});
	</script>
	<script>
$(document).ready(
		function() {
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
