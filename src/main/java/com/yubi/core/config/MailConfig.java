package com.yubi.core.config;

import java.util.Properties;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import com.yubi.application.product.ProductService;
import com.yubi.shop.basket.BasketCreationListener;
import com.yubi.shop.basket.BasketExpiryListener;

@Configuration
public class MailConfig {
	
	@Inject
	private Environment environment;
	
	@Inject
	private ProductService productService;

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(environment.getProperty("smtp.host"));
		sender.setPort(Integer.valueOf(environment.getProperty("smtp.port")));
		sender.setUsername(environment.getProperty("smtp.username"));
		sender.setPassword(environment.getProperty("smtp.password"));

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
		message.setFrom(environment.getProperty("mail.send.address"));
		message.setReplyTo(environment.getProperty("mail.send.address"));
		return message;
	}

	
	@Bean
	public SpringTemplateEngine templateEngine() {
		FileTemplateResolver resolver = new FileTemplateResolver();
		resolver.setPrefix("src/main/webapp/templates/");
		resolver.setTemplateMode("HTML5");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setOrder(1);
		
		SpringTemplateEngine engine = new
				SpringTemplateEngine();
		engine.setTemplateResolver(resolver);
		return engine;
	}
	
	@Bean
	public BasketCreationListener basketCreationListener() {
		return new BasketCreationListener();
	}

	
	@Bean
	public BasketExpiryListener basketExpiryListener() {
		return new BasketExpiryListener(productService);
	}
}
