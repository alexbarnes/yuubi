package com.yubi.core.mail;

import java.util.List;

import javax.inject.Inject;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.yubi.application.user.User;
import com.yubi.application.user.UserAccess;

@Service
public class EmailService {
	
	private final JavaMailSender sender;
	private final UserAccess userAccess;
	
	@Inject
	public EmailService(JavaMailSender sender, UserAccess userAccess) {
		super();
		this.sender = sender;
		this.userAccess = userAccess;
	}

	
	public void sendMailToAdmins(OutboundMailMessage message) {
		SimpleMailMessage messageToSend = new SimpleMailMessage();
		
		// -- Get the admin users from the database
		List<String> users = userAccess.selectAdminUsers();
		messageToSend.setTo(users.toArray(new String[users.size()]));
		
		messageToSend.setSubject(message.getSubject());
		messageToSend.setText(message.getText());
		messageToSend.setFrom("info@yuubi-jewellery.co.uk");
		sender.send(messageToSend);
	}
}
