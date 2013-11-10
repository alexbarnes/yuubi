<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<br>
<div class="row">
	<div class="span2">
		<div class="btn-group" data-toggle="buttons-radio">
			<c:choose>
				<c:when test="${sessionScope.currency.currencyCode == 'USD'}">
					<button type="button" class="btn btn-small active disabled">
						<i class="icon-flag bfh-flag-US"></i>
					</button>
				</c:when>
				<c:otherwise>
				<!--  button type="button" class="btn" id="currUS"-->
					<a class="btn btn-small"
						href="<spring:url value='/shop/currency/change?currencyCode=USD&url=${sessionScope.current_url}'/>"><i
						class="icon-flag bfh-flag-US"></i></a>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${sessionScope.currency.currencyCode == 'GBP'}">
					<button type="button" class="btn btn-small active disabled">
						<i class="icon-flag bfh-flag-GB"></i>
					</button>
				</c:when>
				<c:otherwise>
					<!-- button type="button" class="btn" id="currGB"-->
					<a class="btn btn-small"
						href="<spring:url value='/shop/currency/change?currencyCode=GBP&url=${sessionScope.current_url}'/>"><i class="icon-flag bfh-flag-GB"></i></a>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
			<c:when test="${sessionScope.currency.currencyCode == 'EUR'}">
				<button type="button" class="btn active disabled btn-small">
						<i class="icon-flag bfh-flag-EUR"></i>
					</button>
			</c:when>
			<c:otherwise>
			<!--button type="button" class="btn" id="currEUR"-->
			<a class="btn btn-small"
						href="<spring:url value='/shop/currency/change?currencyCode=EUR&url=${sessionScope.current_url}'/>"><i class="icon-flag bfh-flag-EUR"></i></a>
			</c:otherwise>
			</c:choose>
		</div>
	</div>
	<c:if test="${open}">
		<div class="span1 offset9">
			<div class="cart">
				<a id="basket" href="<spring:url value='/shop/basket/show'/>"
					rel="popover">Basket</a><br />
				<p id="basketTotal">
					<c:choose>
						<c:when test="${empty basket.items}">
							Empty
						</c:when>
						<c:otherwise>
						<c:set var="currency" value="${sessionScope.currency}"/>
						<c:out value="${sessionScope.currency.symbol}"/>
					 		<fmt:formatNumber value="${basket.getTotal(currency)}" />
						</c:otherwise>
					</c:choose>
				</p>
			</div>
		</div>
	</c:if>
</div>
<c:if test="${!open}">
<br>
	<div class="row">
		<div class="span12">
			<div class="alert alert-error">
				<strong>${closedmessage}</strong>
			</div>
		</div>
	</div>
</c:if>

<!-- Logo -->
<div class="row show-grid">
	<div class="span12 logo">
		<a href="<spring:url value='/shop'/>"> <img alt=""
			src="<spring:url value='/resources/shop/img/logo3.jpg'/>" />
		</a>
	</div>
</div>
<!-- End Header-->
<!-- Start Navigation -->
<div class="row hidden-phone">
	<div class="span12">
		<div class="navbar" id="main-menu">
			<div class="navbar-inner-other no-border">
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
									action="/shop/product/search" cssClass="navbar-search">
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