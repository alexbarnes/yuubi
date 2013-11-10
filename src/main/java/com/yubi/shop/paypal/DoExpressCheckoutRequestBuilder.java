package com.yubi.shop.paypal;

import java.math.BigDecimal;

public class DoExpressCheckoutRequestBuilder {
	
	private String token;
	
	private String payerId;
	
	private String username;
	
	private String password;
	
	private String signature;
	
	private BigDecimal orderTotal;
	
	private String currencyCode;
	
	private String orderReference;
	
	public DoExpressCheckoutRequestBuilder(String username, String password,
			String signature) {
		super();
		this.username = username;
		this.password = password;
		this.signature = signature;
	}
	
	public DoExpressCheckoutRequestBuilder withPayerId(String payerId) {
		this.payerId = payerId;
		return this;
	}
	
	public DoExpressCheckoutRequestBuilder withToken(String token) {
		this.token = token;
		return this;
	}
	
	public DoExpressCheckoutRequestBuilder withOrderTotal(BigDecimal total) {
		this.orderTotal = total;
		return this;
	}
	
	public DoExpressCheckoutRequestBuilder inCurrency(String currencyCode) {
		this.currencyCode = currencyCode;
		return this;
	}
	
	public DoExpressCheckoutRequestBuilder withOrderReference(String orderReference) {
		this.orderReference = orderReference;
		return this;
	}


	public String buildRequest() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("USER=" + username);
		buffer.append("&");
		
		buffer.append("PWD=" + password);
		buffer.append("&");
		
		buffer.append("SIGNATURE=" + signature);
		buffer.append("&");
		
		buffer.append("METHOD=DoExpressCheckoutPayment");
		buffer.append("&");
		
		buffer.append("VERSION=72.0");
		buffer.append("&");
		
		buffer.append("TOKEN=" + token);
		buffer.append("&");
		
		buffer.append("PAYERID=" + payerId);
		buffer.append("&");
		
		buffer.append("PAYMENTREQUEST_0_PAYMENTACTION=Sale");
		buffer.append("&");
		
		buffer.append("PAYMENTREQUEST_0_AMT=" + orderTotal.toString());
		buffer.append("&");
		
		buffer.append("PAYMENTREQUEST_0_CURRENCYCODE=" + currencyCode);
		buffer.append("&");
		
		buffer.append("PAYMENTREQUEST_n_INVNUM=" + orderReference);
		
		return buffer.toString();
	}

}
