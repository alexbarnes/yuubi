package com.yubi.shop.paypal;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GetExpressCheckoutDetailsRequest {
	
	private String token;
	
	public GetExpressCheckoutDetailsRequest(String token) {
		this.token = token;
	}
	
	public String createRequest() {
		try {
			return "METHOD=GetExpressCheckoutDetails&TOKEN=" + URLEncoder.encode(token, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Failed to encode token [" + token + "] using [UTF-8]");
		}
	}

}
