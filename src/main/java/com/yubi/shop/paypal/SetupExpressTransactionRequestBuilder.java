package com.yubi.shop.paypal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SetupExpressTransactionRequestBuilder {
	
	private String username;
	
	private String password;
	
	private String signature;
	
	private String returnURL;
	
	private String cancelURL;
	
	private BigDecimal shippingCost;
	
	private BigDecimal orderTotal;
	
	private final List<ExpressTransactionItem> items = new ArrayList<ExpressTransactionItem>();
	
	public SetupExpressTransactionRequestBuilder(String username,
			String password, String signature) {
		super();
		this.username = username;
		this.password = password;
		this.signature = signature;
	}
	
	
	public SetupExpressTransactionRequestBuilder addItem(ExpressTransactionItem item) {
		this.items.add(item);
		return this;
	}
	
	
	public SetupExpressTransactionRequestBuilder withReturnURL(String url) {
		this.returnURL = url;
		return this;
	}
	
	
	public SetupExpressTransactionRequestBuilder withCancelURL(String url) {
		this.cancelURL = url;
		return this;
	}
	
	
	public SetupExpressTransactionRequestBuilder withShippingCost(BigDecimal cost) {
		this.shippingCost = cost;
		return this;
	}
	
	public SetupExpressTransactionRequestBuilder withTotalCost(BigDecimal cost) {
		this.orderTotal = cost;
		return this;
	}
		
	public String createRequest() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("USER=" + username);
		buffer.append("&");
		
		buffer.append("PWD=" + password);
		buffer.append("&");
		
		buffer.append("SIGNATURE=" + signature);
		buffer.append("&");
		
		buffer.append("METHOD=SetExpressCheckout");
		buffer.append("&");
		
		buffer.append("VERSION=72.0");
		buffer.append("&");
		
		buffer.append("PAYMENTREQUEST_0_PAYMENTACTION=Sale");
		buffer.append("&");
		
		buffer.append("SOLUTIONTYPE=Sole");
		buffer.append("&");
		
		buffer.append("CANCELURL=" + cancelURL);
		buffer.append("&");
		
		buffer.append("RETURNURL=" + returnURL);
		buffer.append("&");
		
		buffer.append("PAYMENTREQUEST_0_CURRENCYCODE=GBP");
		buffer.append("&");
		
		buffer.append("PAYMENTREQUEST_0_SHIPPINGAMT=" + shippingCost.toString());
		buffer.append("&");
		
		BigDecimal productTotal = new BigDecimal(0.00);
		int i =0;
		for (ExpressTransactionItem item : items) {
			buffer.append(item.createRequest(i));
			productTotal = productTotal.add(item.getTotalCost());
			i++;
		}
		
		buffer.append("PAYMENTREQUEST_0_ITEMAMT=" + productTotal.toString());
		buffer.append("&");
				
		buffer.append("PAYMENTREQUEST_0_AMT=" + orderTotal.toString());
		
		return buffer.toString();
	}
	

}
