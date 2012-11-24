package com.yubi.shop.paypal;


public class GetExpressCheckoutDetailsRequest {
	
	private String token;
	
	private String username;
	
	private String password;
	
	private String signature;
	
	public GetExpressCheckoutDetailsRequest(
			String token, 
			String username, 
			String password, 
			String signature) {
		this.token = token;
		this.username = username;
		this.password = password;
		this.signature = signature;
	}
	
	public String createRequest() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("USER=" + username);
		buffer.append("&");
		
		buffer.append("PWD=" + password);
		buffer.append("&");
		
		buffer.append("SIGNATURE=" + signature);
		buffer.append("&");
		
		buffer.append("METHOD=GetExpressCheckoutDetails");
		buffer.append("&");
		
		buffer.append("VERSION=72.0");
		buffer.append("&");
		
		buffer.append("TOKEN=" + token);
		
		return buffer.toString();
	}
}