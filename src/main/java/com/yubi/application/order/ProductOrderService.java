package com.yubi.application.order;

import com.yubi.shop.basket.Basket;

public interface ProductOrderService {
	
	public void createNewOrder(Basket basket, String transactionId);

}
