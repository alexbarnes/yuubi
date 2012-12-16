package com.yubi.shop.paypal;

import com.yubi.shop.basket.Basket;

public interface PaypalService {
	
	/**
	 * Setup the express checkout flow.
	 */
	public String setupTransaction(Basket basket, String sessionId);
	
	
	/**
	 * @param token
	 */
	public ExpressCheckoutDetail loadTransactionDetail(String token, String sessionId);
	
	
	/**
	 * Complete the flow. This is where the order is written to the database.
	 * 
	 */
	public String completeTransaction(String token, String payerId, String sessionId, Basket basket);
	
	
	/**
	 * Load the details of a prior transaction. This should always be used when accessing the
	 * details of a transaction to avoid storing personal information locally.
	 * 
	 * @param transactionId
	 */
	public void loadTransaction(String transactionId);

}
