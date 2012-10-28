package com.yubi.application.shop.paypal;

import java.math.BigDecimal;

public class ExpressTransactionItem {
	
	private final String description;
	
	private final String name;
	
	private final int quantity;
	
	private final BigDecimal unitCost;
	
	public ExpressTransactionItem(String name, String description, int quantity,
			BigDecimal unitCost) {
		super();
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.unitCost = unitCost;
	}

	public String createRequest(int lineNumber) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("L_PAYMENTREQUEST_0_NAME" + lineNumber +"=" + name);
		buffer.append("&");
		
		buffer.append("L_PAYMENTREQUEST_0_DESC" + lineNumber +"=" + description);
		buffer.append("&");
		
		buffer.append("L_PAYMENTREQUEST_0_AMT0" + lineNumber +"=" + unitCost.toString());
		buffer.append("&");
		
		buffer.append("L_PAYMENTREQUEST_0_QTY" + lineNumber +"=" + quantity);
		buffer.append("&");
		
		return buffer.toString();
	}
	
	public BigDecimal getTotalCost() {
		return unitCost.multiply(new BigDecimal(quantity));
	}
}
