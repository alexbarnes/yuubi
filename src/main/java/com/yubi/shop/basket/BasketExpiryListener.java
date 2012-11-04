package com.yubi.application.core.login;

import java.util.Map.Entry;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

import com.yubi.application.product.ProductService;
import com.yubi.application.shop.Basket;
import com.yubi.application.shop.BasketItem;

public class BasketExpiryListener implements ApplicationListener<HttpSessionDestroyedEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(BasketExpiryListener.class);
	
	private final ProductService productService;
	
	@Inject
	public BasketExpiryListener(ProductService productService) {
		super();
		this.productService = productService;
	}
	
	
	public void onApplicationEvent(HttpSessionDestroyedEvent event) {
		log.info("Expiring basket for session [" + event.getId() + "].");
		HttpSession session = event.getSession();
		
		// If we have a basket we need to replace the stock
		// since it wasn't purchased.
		if (session.getAttribute("basket") != null) {
			Basket basket = (Basket) session.getAttribute("basket");
			
			// Loop over the basket contents and update the stock levels depending
			// on the basket contents
			for (Entry<String, BasketItem> item : basket.getItems().entrySet()) {
				productService.increaseStockLevel(item.getKey(), item.getValue().getNumber());
			}
		}
		
		// Remove the session basket, just to be sure.
		session.setAttribute("basket", null);
	}

}
