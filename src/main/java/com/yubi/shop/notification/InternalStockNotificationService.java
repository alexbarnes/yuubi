package com.yubi.shop.notification;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.product.Product;
import com.yubi.core.mail.EmailService;
import com.yubi.core.mail.OutboundMailMessage;

@Service
public class InternalStockNotificationService implements StockNotificationService {
	
	private final OutOfStockService outOfStockService;
	private final EmailService emailService;
	
	@Inject
	public InternalStockNotificationService(
			OutOfStockService outOfStockService,
			EmailService emailService) {
		super();
		this.outOfStockService = outOfStockService;
		this.emailService = emailService;
	}


	/* (non-Javadoc)
	 * @see com.yubi.shop.notification.StockNotificationService#notify(com.yubi.application.product.Product)
	 */
	@Transactional
	public void notify(Product product) {
		List<OutOfStockNotification> list = outOfStockService
				.loadNotificationsForProduct(product.getCode());

		// -- No need for an e-mail if there are no notifications to send.
		if (list.isEmpty()) {
			return;
		}

		String messageText = "The following people need to be notified that product ["
				+ product.getCode() + "] is back in stock. ";
		for (OutOfStockNotification notification : list) {
			notification.setSent(true);
			messageText = messageText + notification.getEmail() + ";";
		}
		
		OutboundMailMessage message = new OutboundMailMessage();
		message.setSubject("Back in Stock Notifications - Send these out.");
		message.setText(messageText);
		message.setFrom("info@yuubi-jewellery.co.uk");
		message.setFromName("Yuubi Admin Notification");
		emailService.sendMailToAdmins(message);
	}
}
