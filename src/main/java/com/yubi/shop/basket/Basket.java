package com.yubi.shop.basket;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
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
	
	public BigDecimal getTotal() {
		BigDecimal total = new BigDecimal(0.00);
		for (Entry<String, BasketItem> item : items.entrySet()) {
			total = total.add(item.getValue().getTotalCost());
		}
		return total;
	}
}