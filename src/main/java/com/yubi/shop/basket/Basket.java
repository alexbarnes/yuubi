package com.yubi.shop.basket;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.yubi.shop.delivery.DeliveryMethod;
import com.yubi.shop.discount.Discount;

public class Basket {
	
	private final Map<String, BasketItem> items = new HashMap<String, BasketItem>();
	
	private DeliveryMethod deliveryMethod;
	
	private Discount discount;
	
	public Map<String, BasketItem> getItems() {
		return items;
	}
	
	public void reset() {
		this.items.clear();
		this.deliveryMethod = null;
		this.discount = null;
	}

	public DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	
	public Discount getDiscount() {
		return discount;
	}
	
	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
	
	public BigDecimal getTotal() {
		BigDecimal total = new BigDecimal(0.00);
		for (Entry<String, BasketItem> item : items.entrySet()) {
			total = total.add(item.getValue().getTotalCost());
		}
		return total;
	}
}