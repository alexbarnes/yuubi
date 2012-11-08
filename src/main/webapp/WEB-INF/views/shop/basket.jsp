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
						<li><a href="<spring:url value='/shop'/>"><i class="icon-home"></i></a> <span
							class="divider">/</span></li>
						<li class="active">Your Cart</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="span12">
			<h2 class="title">Your Cart</h2>
			<hr />
			<div class="entry">

				<table class="table table-bordered table-striped">
					<thead>
						<tr>
							<th>Remove</th>
							<th>Image</th>
							<th>Product Name</th>
							<th>Quantity</th>
							<th>Unit Price</th>
							<th>Total</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="center"><input type="checkbox" value="option1"></td>
							<td class="center"><a href="product.html"><img alt=""
									src="img/thumbs/1.jpg"></a></td>
							<td>Fusce id molestie massa</td>
							<td><input type="text" placeholder="1" class="input-mini"></td>
							<td>$2,350.00</td>
							<td>$2,350.00</td>
						</tr>
						<tr>
							<td class="center"><input type="checkbox" value="option1"></td>
							<td class="center"><a href="product.html"><img alt=""
									src="img/thumbs/2.jpg"></a></td>
							<td>Luctus quam ultrices rutrum</td>
							<td><input type="text" placeholder="1" class="input-mini"></td>
							<td>$1,150.00</td>
							<td>$2,450.00</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><strong>$3,600.00</strong></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="row pull-right">
		<div class="span2">
			<button class="btn btn-primary" type="submit">Update</button>
		</div>
		<div class="span2">
			<a href="<spring:url value='/shop/checkout'/>" class="pull-right">
				<img src="https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif"
				align="left" style="margin-right: 7px;">
			</a>
		</div>
	</div>
</div>


<script
	src="<spring:url value='/resources/shop/js/jquery-1.7.2.min.js'/>"></script>
<script src="<spring:url value='/resources/shop/js/bootstrap.min.js'/>"></script>
</html>
