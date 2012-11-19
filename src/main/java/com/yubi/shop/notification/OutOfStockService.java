package com.yubi.shop.notification;

import java.util.List;


public interface OutOfStockService {

	public void saveNewNotification(String productCode, String email);
	
	public List<OutOfStockNotification> loadNotificationsForProduct(String code);

}