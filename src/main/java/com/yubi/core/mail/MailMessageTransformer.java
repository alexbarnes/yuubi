package com.yubi.core.mail;

import javax.inject.Inject;
import javax.mail.MessagingException;

import org.springframework.context.annotation.Scope;
import org.springframework.integration.annotation.Transformer;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Component;

@Component(value="mailTransformer")
@Scope("prototype")
public class MailMessageTransformer {
	
	private final MimeMailMessage mailMessage;

	@Inject
	public MailMessageTransformer(MimeMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}
	
	@Transformer
	public MimeMailMessage transform(OutboundMailMessage message) throws MessagingException {
		mailMessage.getMimeMessageHelper().setText(message.getText(), true);
		mailMessage.setTo(message.getRecipients());
	    mailMessage.setSubject(message.getSubject());
		return mailMessage;
	}
}
