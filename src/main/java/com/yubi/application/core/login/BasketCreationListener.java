package com.yubi.application.core.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;

import com.yubi.application.shop.Basket;

public class BasketCreationListener implements
		ApplicationListener<HttpSessionCreatedEvent> {
	
	public static final String BASKET_KEY = "basket";
	
	private static final Logger log = LoggerFactory.getLogger(BasketCreationListener.class);

	public void onApplicationEvent(HttpSessionCreatedEvent event) {
		log.info("Creating basket for session with id [" + event.getSession().getId() + "].");
		
		event.getSession().setMaxInactiveInterval(60);
		if (event.getSession() != null
				&& event.getSession().getAttribute("basket") == null) {
			event.getSession().setAttribute(BASKET_KEY, new Basket());
		}
	}

}
