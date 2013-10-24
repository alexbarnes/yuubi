package com.yubi.application.order;

import java.util.Currency;

import com.yubi.shop.basket.Basket;

public interface ProductOrderService {
	
	public long createNewOrder(Basket basket, String transactionId, Currency currency);

}
