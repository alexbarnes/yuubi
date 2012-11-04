package com.yubi.shop.paypal;

import com.yubi.shop.basket.Basket;

public interface PaypalService {
	
	/**
	 * Setup the express checkout flow.
	 */
	public String setupTransaction(Basket basket);
	
	
	/**
	 * Complete the flow. This is where the order is written to the database. We 
	 * also obtain the shipping information from here.
	 */
	public void completeTransaction(String token);

}
