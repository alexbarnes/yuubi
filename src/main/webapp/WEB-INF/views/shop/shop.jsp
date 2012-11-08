<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="header.jsp" />
<div class="row">
	<div class="span12">
		<div id="myCarousel" class="carousel slide home">
			<div class="carousel-inner">
				<div class="item active">

					<img alt="" src="<spring:url value='/resources/shop/img/1.jpg'/>" />
					<div class="carousel-caption">
						<h4>Slide Image 1</h4>
						<p></p>
					</div>
				</div>
				<div class="item">
					<img alt="" src="<spring:url value='/resources/shop/img/2.jpg'/>" />
					<div class="carousel-caption">
						<h4>Slide Image 2</h4>
						<p></p>
					</div>
				</div>
			</div>
			<a class="left carousel-control" href="#myCarousel" data-slide="prev">‹</a>
			<a class="right carousel-control" href="#myCarousel"
				data-slide="next">›</a>
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
											<a
												href="<spring:url value='/shop/category/view/${child.id}'/>"><h4>${child.description}</h4></a>
											<a
												href="<spring:url value='/shop/category/view/${child.id}'/>"><img
												src="<spring:url value='/shop/category/image/${child.id}'/>" /></a>
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
<jsp:include page="footer.jsp"/>

<script
	src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>
<script type="text/javascript">
		$(document).ready(function() {
			$('.carousel').carousel({
				interval : 2000
			})

			$('#myTab a:first').tab('show');
			$('#myTab a').click(function(e) {
				e.preventDefault();
				$(this).tab('show');
			})
		});
	</script>
</body>
</html>