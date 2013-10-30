<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<footer>
	<div class="row">
		<div class="span3">
			<div class="company_info">
				<h4 class="logo-title">
					<span>Y&#362;bi</span>Jewellery
				</h4>
			</div>
		</div>
		<div class="span3">
			<h4>Information</h4>
			<ul>
				<li><a href='<spring:url value="/shop/about"></spring:url>'>About
						Us</a></li>
				<li><a href="<spring:url value="/shop/deliveryinfo"/>">Delivery
						Information</a></li>
				<li><a href="<spring:url value="/shop/terms"/>">Terms &amp;
						Conditions</a></li>
			</ul>
		</div>
		<div class="span3">
			<h4>Connect with Y&#362;bi</h4>
			<a href="https://www.facebook.com/yuubijewellery"> <img alt="Facebook" src="<spring:url value='/resources/shop/img/facebook_active.png'/>" /></a>
			<a href="https://www.twitter.com"><img alt="Facebook" src="<spring:url value='/resources/shop/img/twitter_active.png'/>" /></a> 
		</div>
	</div>
</footer>