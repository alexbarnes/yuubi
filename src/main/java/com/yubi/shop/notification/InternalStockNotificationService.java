package com.yubi.shop.notification;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.product.Product;

@Service
public class InternalStockNotificationService implements StockNotificationService {
	
	private final OutOfStockService outOfStockService;
	
	@Inject
	public InternalStockNotificationService(
			OutOfStockService outOfStockService) {
		super();
		this.outOfStockService = outOfStockService;
	}


	@Transactional
	public void notify(Product product) {
		for (OutOfStockNotification notification : 
			outOfStockService.loadNotificationsForProduct(product.getCode())) {
			
			// TODO Send the notifications here.
			notification.setSent(true);
		}
	}
}
