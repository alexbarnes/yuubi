package com.yubi.core.mail;

import org.springframework.integration.annotation.Gateway;

/**
 * @author Alex Barnes
 */
public interface MailGateway {
	
	@Gateway
	public void sendMessage(OutboundMailMessage message);
}
