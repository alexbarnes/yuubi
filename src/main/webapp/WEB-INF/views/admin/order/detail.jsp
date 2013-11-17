<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="row-fluid">
	<div class="box span10 offset4">
	<hr>
		<div class="row-fluid">
			<div class="box">
				<div class="row">
					<form class="form-horizontal">
						<div class="span5">
							<div class="control-group">
								<label class="control-label" for="fromName">Paypal
									Transaction Id</label>
								<div class="controls">
									<span class="input-large uneditable-input">${order.paypalTransactionId}</span>
								</div>

							</div>
							<div class="control-group">
								<label class="control-label" for="fromName">Delivery
									Method</label>
								<div class="controls">
									<span class="input-large uneditable-input">${order.delivery.description}</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="fromName">Discount Code</label>
								<div class="controls">
									<span class="input-large uneditable-input">${order.discount == null ? 'None' : order.discount.description}</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="fromName">Order Created</label>
								<div class="controls">
									<span class="input-medium uneditable-input"><fmt:formatDate value="${order.enteredDate}" pattern="dd/MM/yyyy HH:mm z"/></span>
								</div>
							</div>
							
						</div>
						<div class="span6">
							<div class="control-group">
								<label class="control-label" for="fromName">Currency</label>
								<div class="controls">
									<span class="input-medium uneditable-input">${order.currencyCode}</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="fromName">Payment
									Status</label>
								<div class="controls">
									<span class="input-medium uneditable-input">${paypalDetail.paymentStatus}</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="fromName">Order Status</label>
								<div class="controls">
									<div id="status-control"<c:if test="${order.status == 'ENTERED'}">class="input-append"</c:if>>
										<span class="input-small uneditable-input" id="order-status">${order.status == 'ENTERED' ? 'Entered' : 'Completed'}</span>
										<c:if test="${order.status == 'ENTERED'}"><button class="btn" type="button" id="mark-complete"><i class="icon-ok-sign"></i></button></c:if>
									</div>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="fromName">Payment Pending Reason</label>
								<div class="controls">
									<span class="input-medium uneditable-input">${paypalDetail.pendingReason}</span>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
			<hr>
			<div class="box">
				<div class="row">
					<form class="form-horizontal">
						<div class="span5">
							<div class="control-group">
								<label class="control-label" for="fromName">Customer Name</label>
								<div class="controls">
									<span class="input-large uneditable-input">${paypalDetail.customerName}</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="fromName">Customer Email</label>
								<div class="controls">
									<span class="input-large uneditable-input">${paypalDetail.customerEmail}</span>
								</div>
							</div>
						</div>
						<div class="span5">
						<div class="control-group">
								<label class="control-label" for="fromName">Note</label>
								<div class="controls">
									<span class="input-large uneditable-input">${paypalDetail.note}</span>
								</div>
							</div>
							
							
						</div>
					</form>
				</div>
			</div>
			<hr>
			<div class="box">
				<table class="table table-bordered">
					<tr>
						<th>Item</th>
						<th>Category</th>
						<th>Cost</th>
						<th>Line Total</th>
					</tr>
					<c:forEach items="${order.items}" var="item">
						<tr>
							<td>${item.id.product.title}</td>
							<td>${item.id.product.category.description}</td>
							<td>${item.quantity} @ ${item.id.product.getPriceInCurrencyForCode(order.currencyCode)}</td>
							<td>${item.totalCost}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$('#mark-complete').on('click',function(e) {
		$.get('<spring:url value='/admin/order/sent/${order.id}'/>').done(function(data) {
			if (data) {
				$('#order-status').html('Completed');
				$('#summary-status').html('Completed');
				$('#mark-complete').remove();
				$('#status-control').removeClass('input-append');
			} else {
				
			}
		});
	});
</script>
</div>