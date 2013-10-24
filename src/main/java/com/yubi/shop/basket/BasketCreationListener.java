package com.yubi.shop.basket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;

/**
 * Set up a users basket on session creation.
 * 
 * @author Alex Barnes
 *
 */
public class BasketCreationListener implements
		ApplicationListener<HttpSessionCreatedEvent> {
	
	public static final String BASKET_KEY = "basket";
	
	private static final Logger log = LoggerFactory.getLogger(BasketCreationListener.class);

	public void onApplicationEvent(HttpSessionCreatedEvent event) {
	/*	event.getSession().setMaxInactiveInterval(60*10);
		if (event.getSession() != null && event.getSession().getAttribute("basket") == null) {
			log.info("Creating basket for session with id [" + event.getSession().getId() + "].");
			Basket basket = new Basket();
			event.getSession().setAttribute(BASKET_KEY, basket);
		}*/
	}
}
