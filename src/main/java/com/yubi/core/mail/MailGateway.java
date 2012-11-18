package com.yubi.core.mail;

import org.springframework.integration.annotation.Gateway;

public interface MailGateway {
	
	@Gateway
	public void sendMessage();

}
