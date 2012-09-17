package com.yubi.application.home;

import java.util.Date;

public class UpcomingDelivery {

	private final long orderId;

	private final String externalOrderNumber;

	private final String supplierName;

	private final Date deliveryDate;

	private final String description;

	public UpcomingDelivery(long orderId, String externalOrderNumber,
			String supplierName, Date deliveryDate, String description) {
		super();
		this.orderId = orderId;
		this.externalOrderNumber = externalOrderNumber;
		this.supplierName = supplierName;
		this.deliveryDate = deliveryDate;
		this.description = description;
	}

	public String getOrderNumber() {
		return externalOrderNumber;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public String getDescription() {
		return description;
	}

	public long getOrderId() {
		return orderId;
	}

}
