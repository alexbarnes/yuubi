<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp"/>

<div class="container">
	<div class="row-fluid">
		<div class="span6">
			<div class="alert alert-success">
				<h3>Upcoming Deliveries</h3>
			</div>
			<div class="well">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Order Number</th>
							<th>Supplier</th>
							<th>Date</th>
							<th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${model.upcomingDeliveries}" var="current">
							<tr>
								<td>${current.orderNumber}</td>
								<td>${current.supplierName}</td>
								<td>${current.deliveryDate}</td>
								<td><a class="btn btn-small" href="#"><i
										class="icon-eye-open"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="span6">
			<div class="alert">
				<h3>Stock Alerts</h3>
			</div>
			<div class="well">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Item</th>
							<th>Quantity</th>
							<th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${model.stockAlerts}" var="current">
							<tr>
								<td>${current.itemDescription}</td>
								<td>${current.number}</td>
								<td><a class="btn btn-small" href="#"><i
										class="icon-eye-open"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<hr />
	<footer>
		<p>© Alex Barnes & Yubi Jewellery 2012</p>
	</footer>
</div>
<jsp:include page="footer.jsp"/>