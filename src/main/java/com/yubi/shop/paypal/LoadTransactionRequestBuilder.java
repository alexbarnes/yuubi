package com.yubi.shop.paypal;

public class LoadTransactionRequestBuilder {
	
	private final String username;
	private final String password;
	private final String signature;
	private String transactionId;
	
	public LoadTransactionRequestBuilder(String username, String password,
			String signature) {
		super();
		this.username = username;
		this.password = password;
		this.signature = signature;
	}
	
	public LoadTransactionRequestBuilder withTransactionId(String id) {
		this.transactionId = id;
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
		
		buffer.append("METHOD=GetTransactionDetails");
		buffer.append("&");
		
		buffer.append("VERSION=72.0");
		buffer.append("&");
		
		buffer.append("TRANSACTIONID=" + transactionId);
		return buffer.toString();
	}

}
