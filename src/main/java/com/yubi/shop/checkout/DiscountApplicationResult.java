package com.yubi.shop.checkout;

import com.yubi.shop.delivery.DeliveryMethodUpdateResult;
import com.yubi.shop.discount.Discount;

public class DiscountApplicationResult extends DeliveryMethodUpdateResult {
	
	public boolean valid;
	
	public Discount discount;
	
	public String amount;
}
