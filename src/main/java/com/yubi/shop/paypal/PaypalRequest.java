package com.yubi.shop.paypal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class PaypalRequest {
	
	@Id
	@GeneratedValue
	protected long id;
	
	@Lob
	private String request;
	
	@Lob
	private String response;
	
	private String ack;
	
	private String token;
	
	private String sessionId;
	
	private String transactionId;
	
	private String timeStampString;
	
	private String requestType;

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getAck() {
		return ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTimeStampString() {
		return timeStampString;
	}

	public void setTimeStampString(String timeStampString) {
		this.timeStampString = timeStampString;
	}	
}