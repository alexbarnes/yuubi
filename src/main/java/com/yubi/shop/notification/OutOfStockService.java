package com.yubi.shop.notification;


public interface OutOfStockService {

	public void saveNewNotification(String productCode, String email);

}