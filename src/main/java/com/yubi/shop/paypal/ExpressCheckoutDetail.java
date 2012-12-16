package com.yubi.shop.paypal;

import com.yubi.shop.checkout.Customer;


public class ExpressCheckoutDetail {
	
	private String message;
	
	public ExpressCheckoutDetail() {
		this.customer = new Customer();
	}
	
	private Customer customer;
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
