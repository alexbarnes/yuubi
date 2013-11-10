package com.yubi.core.mail;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class OutboundMailMessage implements Serializable {

	private static final long serialVersionUID = -1325571591227542819L;

	private String recipients;
	
	private String subject;
	
	@NotBlank(message="A message must be provided")
	private String text;
	
	@Email(message = "Must be a valid e-mail address")
	@NotBlank(message="An email address must be provided")
	private String from;
	
	@NotBlank(message="A name must be provided")
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
