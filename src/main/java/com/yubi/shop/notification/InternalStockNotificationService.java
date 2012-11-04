package com.yubi.shop.notification;

import org.springframework.stereotype.Service;

import com.yubi.application.product.Product;

@Service
public class InternalStockNotificationService implements StockNotificationService {

	public void notify(Product product) {
		// Load the notifications for this product
		
		
		// Send each notification and mark it as sent
		
	}
}
