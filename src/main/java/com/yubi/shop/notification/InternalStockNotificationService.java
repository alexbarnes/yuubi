package com.yubi.shop.notification;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.product.Product;
import com.yubi.core.mail.MailGateway;

@Service
public class InternalStockNotificationService implements StockNotificationService {
	
	private final OutOfStockService outOfStockService;
	
	private final MailGateway mailGateway;
	
	@Inject
	public InternalStockNotificationService(
			OutOfStockService outOfStockService,
			MailGateway mailGateway) {
		super();
		this.outOfStockService = outOfStockService;
		this.mailGateway = mailGateway;
	}


	@Transactional
	public void notify(Product product) {
		for (OutOfStockNotification notification : 
			outOfStockService.loadNotificationsForProduct(product.getCode())) {
			notification.setSent(true);
		}
	}
}
