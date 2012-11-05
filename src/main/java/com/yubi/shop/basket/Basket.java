package com.yubi.shop.basket;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.yubi.shop.delivery.DeliveryMethod;

public class Basket {
	
	private final Map<String, BasketItem> items = new HashMap<String, BasketItem>();
	
	private DeliveryMethod deliveryMethod;
	
	private final Set<String> discounts = new HashSet<String>();
	
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
	
	public Set<String> getDiscounts() {
		return discounts;
	}
}