<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="row" id="top-bar">
	<div class="span9">
		<ul id="links" class="nav nav-pills pull-left">
			<li><a href="<spring:url value='/shop'/>" title="Bitsy Shop">Home</a></li>
			<li><a href="<spring:url value='/shop'/>" title="All specials">Gallery</a></li>
			<li><a href="<spring:url value='/shop'/>" title="Contact">Contact</a></li>
		</ul>
	</div>
</div>
<!-- Logo -->
<div class="row show-grid">
	<div class="span3 logo">
		<a href="<spring:url value='/shop'/>"> <img alt=""
			src="<spring:url value='/resources/shop/img/logo.jpg'/>" />
		</a>
	</div>
	
	<!-- Basket -->
	<div class="span5 offset4">
		<div class="row">
			<div class="span3"></div>
			<div class="span2">
				<div class="cart pull-right">
					<a id="basket" href="<spring:url value='/shop/basket/show'/>"
						rel="popover">Basket</a><br />
					<p id="basketTotal">
						£<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${basket.total}"/>
					</p>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- End Header-->
<!-- Start Navigation -->
<div class="row">
	<div class="span12">
		<div class="navbar" id="main-menu">
			<div class="navbar-inner no-border">
				<div class="container">
					<div class="nav-collapse">
						<ul class="nav pull-left" id="nav">
							<c:forEach items="${menu}" var="current">
								<li class="dropdown"><a href="#" class="dropdown-toggle"
									data-toggle="dropdown">${current.description}<b
										class="caret"></b></a>
									<ul class="dropdown-menu">
										<c:forEach items="${current.childCategories}" var="child"
											varStatus="status">
											<li><a
												href="<spring:url value='/shop/category/view/${child.id}/${child.urlName}'/>">${child.description}</a></li>
											<c:if test="${status.last == false}">
												<li class="divider"></li>
											</c:if>
										</c:forEach>
									</ul></li>
							</c:forEach>
							<li class="search_form"><form:form
									action="/shop/product/search"
									cssClass="navbar-search pull-right">
									<input type="text" class="input-medium search-query"
										placeholder="search..." name="query">
								</form:form></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>