package com.yubi.shop.basket;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yubi.shop.delivery.DeliveryMethod;
import com.yubi.shop.discount.Discount;

public class Basket {
	
	private final Map<BasketKey, BasketItem> items = new HashMap<BasketKey, BasketItem>();
	
	private DeliveryMethod deliveryMethod;
	
	private Discount discount;
	
	public Map<BasketKey, BasketItem> getItems() {
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
	
	public BigDecimal getTotal(Currency currency) {
		BigDecimal total = new BigDecimal(0.00);
		
		for (Entry<BasketKey, BasketItem> item : items.entrySet()) {
			total = total.add(item.getValue().getLineCost(currency));
		}
		return total;
	}
	
	public static class BasketKey {
		private String code;
		
		public BasketKey() {}
		
		public BasketKey(String code){
			this.code = code;
		}
		
		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(code).toHashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			return new EqualsBuilder().append(code, ((BasketKey)obj).code).isEquals();
		}
		
		public String getCode() {
			return code;
		}
		
		public void setCode(String code) {
			this.code = code;
		}
	}
}