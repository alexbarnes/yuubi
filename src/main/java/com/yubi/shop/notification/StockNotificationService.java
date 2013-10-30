package com.yubi.shop.notification;

import com.yubi.application.product.Product;

public interface StockNotificationService {
	
	/**
	 * Notify all registered users that the product they registered an interest in is back in stock.
	 */
	public void notify(Product product);
	
}
