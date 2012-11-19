package com.yubi.core.mail;

import org.springframework.context.annotation.Scope;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

@Component(value="mailTransformer")
@Scope("prototype")
public class MailMessageTransformer {
	
	@Transformer
	public String transform(OutboundMailMessage message) {
		System.out.println("Transformed message");
		return "test";
	}
}
