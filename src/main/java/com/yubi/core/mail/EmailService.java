package com.yubi.core.mail;

import javax.inject.Inject;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	private final JavaMailSender sender;
	private final Environment environment;
	
	@Inject
	public EmailService(JavaMailSender sender, Environment environment) {
		super();
		this.sender = sender;
		this.environment = environment;
	}

	
	public void sendMailToAdmins(OutboundMailMessage message) {
		SimpleMailMessage messageToSend = new SimpleMailMessage();
		messageToSend.setTo(environment.getProperty("admin.emails").split(","));
		messageToSend.setSubject(message.getSubject());
		messageToSend.setText(message.getText());
		messageToSend.setFrom("info@yuubi-jewellery.co.uk");
		sender.send(messageToSend);
	}
}
