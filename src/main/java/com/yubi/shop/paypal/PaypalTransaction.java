package com.yubi.shop.paypal;

public class PaypalTransaction {
	
	private final String paymentStatus;
	private final String customerName;
	private final String customerEmail;
	private final String pendingReason; 
	private final String note;
	
	public PaypalTransaction(String paymentStatus, String customerName,
			String customerEmail, String pendingReason, String note) {
		super();
		this.paymentStatus = paymentStatus;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.pendingReason = pendingReason;
		this.note = note;
	}
	
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}

	public String getPendingReason() {
		return pendingReason;
	}

	public String getNote() {
		return note;
	}
}