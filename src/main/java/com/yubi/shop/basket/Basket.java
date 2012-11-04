package com.yubi.application.shop;

import java.util.HashMap;
import java.util.Map;

public class Basket {
	
	public final Map<String, BasketItem> items = new HashMap<String, BasketItem>();
	
	public DeliveryMethod deliveryMethod;
	
	public Map<String, BasketItem> getItems() {
		return items;
	}
	
	public void reset() {
		this.items.clear();
		this.deliveryMethod = null;
	}

	public DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
}