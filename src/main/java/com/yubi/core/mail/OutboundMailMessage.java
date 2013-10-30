package com.yubi.core.mail;

import java.io.Serializable;

public class OutboundMailMessage implements Serializable {

	private static final long serialVersionUID = -1325571591227542819L;

	private String recipients;
	
	private String subject;
	
	private String text;
	
	private String from;
	
	private String fromName;
	
	public OutboundMailMessage(){}

	public String getRecipients() {
		return recipients;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
}
