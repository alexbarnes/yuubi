package com.yubi.shop.paypal;

import com.yubi.shop.basket.Basket;

public interface PaypalService {
	
	/**
	 * Setup the express checkout flow.
	 */
	public String setupTransaction(Basket basket);
	
	
	/**
	 * @param token
	 */
	public void loadTransactionDetail(String token);
	
	
	/**
	 * Complete the flow. This is where the order is written to the database.
	 * 
	 */
	public void completeTransaction(String token);

}
