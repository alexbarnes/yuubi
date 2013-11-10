package com.yubi.application.order;

import java.util.Currency;
import java.util.List;

import com.yubi.shop.basket.Basket;

public interface ProductOrderService {
	
	public ProductOrder createNewOrder(Basket basket, Currency currency);
	
	public void updateTransactionId(long orderId, String transactionId);
	
	public ProductOrder load(String reference);
	
	public List<ProductOrder> recentOrders();

}
