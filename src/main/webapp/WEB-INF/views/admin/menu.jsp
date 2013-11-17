<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="span3">
	<div class="well sidebar-nav">
		<ul class="nav nav-list">
			<li <c:if test="${selected == 'home'}">class="active"</c:if>><a href="<spring:url value='/admin/home'/>"><span>
						Home</span></a></li>
			<li <c:if test="${selected == 'shop'}">class="active"</c:if>><a href="<spring:url value='/admin/shop'/>"><span>
						Shop</span></a></li>
			<li <c:if test="${selected == 'products'}">class="active"</c:if>><a href="<spring:url value='/admin/products'/>"><span>
						Products</span></a></li>
			<li <c:if test="${selected == 'orders'}">class="active"</c:if>><a href="<spring:url value='/admin/order'/>"><span>
						Orders</span></a></li>
			<li <c:if test="${selected == 'user'}">class="active"</c:if>><a href="<spring:url value='/admin/user'/>"><span>
						User</span></a></li>
			<li class="divider"></li>
			<li><a href="<spring:url value='/admin/logout'/>"><span>
						Logout</span></a></li>
		</ul>
	</div>
</div>