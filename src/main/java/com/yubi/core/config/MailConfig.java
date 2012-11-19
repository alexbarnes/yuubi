package com.yubi.core.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;

@Configuration
public class MailConfig {

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("");
		sender.setPort(1);
		sender.setUsername("");
		sender.setPassword("");

		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.starttls.enable", "true");
		mailProperties.put("mail.smtp.auth", "true");
		sender.setJavaMailProperties(mailProperties);

		return sender;
	}

	@Bean
	@Scope(value = "prototype")
	public MimeMailMessage mailMessage() {
		JavaMailSender sender = mailSender();
		MimeMailMessage message = new MimeMailMessage(
				sender.createMimeMessage());
		message.setFrom("");
		message.setReplyTo("");
		return message;
	}

}
