package com.yubi.shop.basket;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;

import com.yubi.shop.delivery.DeliveryMethod;

public class BasketCreationListener implements
		ApplicationListener<HttpSessionCreatedEvent> {
	
	public static final String BASKET_KEY = "basket";
	
	private static final Logger log = LoggerFactory.getLogger(BasketCreationListener.class);

	public void onApplicationEvent(HttpSessionCreatedEvent event) {
		log.info("Creating basket for session with id [" + event.getSession().getId() + "].");
		
		event.getSession().setMaxInactiveInterval(60);
		if (event.getSession() != null
				&& event.getSession().getAttribute("basket") == null) {
			 // TODO remove me!
			 Basket basket = new Basket();
			 basket.getItems().put("CODE", new BasketItem("CODE", 2));
			 
			 DeliveryMethod method = new DeliveryMethod();
			 method.setCost(new BigDecimal(3.00));
			 method.setDescription("First Class");
			 basket.setDeliveryMethod(method);
			 
			event.getSession().setAttribute(BASKET_KEY, basket);
		}
	}

}
