package com.yubi.core.mail;

import java.io.Serializable;

public class OutboundMailMessage implements Serializable {

	private static final long serialVersionUID = -1325571591227542819L;

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
