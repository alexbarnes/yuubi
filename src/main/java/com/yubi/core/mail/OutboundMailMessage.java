package com.yubi.core.mail;

public class OutboundMailMessage {

	private final String recipients;
	
	private final String subject;
	
	private final String text;

	public OutboundMailMessage(String recipients, String subject, String text) {
		super();
		this.recipients = recipients;
		this.subject = subject;
		this.text = text;
	}

	public String getRecipients() {
		return recipients;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}
}
