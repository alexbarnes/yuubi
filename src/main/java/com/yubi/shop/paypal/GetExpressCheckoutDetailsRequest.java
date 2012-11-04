package com.yubi.application.shop.paypal;

import java.net.URLEncoder;

public class GetExpressCheckoutDetailsRequest {
	
	private String token;
	
	public GetExpressCheckoutDetailsRequest() {
		this.token = token;
	}
	
	public String createRequest() {
		return "TOKEN=" + URLEncoder.encode(token)
	}

}
