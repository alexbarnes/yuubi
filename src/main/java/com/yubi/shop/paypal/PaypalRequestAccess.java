package com.yubi.shop.paypal;

/**
 * @author Alex Barnes
 */
public interface PaypalRequestAccess {
	
	/**
	 * 
	 * 
	 * @param request
	 */
	public void save(PaypalRequest request);
	
	
	/**
	 * @param request
	 */
	public void update(PaypalRequest request);

}
