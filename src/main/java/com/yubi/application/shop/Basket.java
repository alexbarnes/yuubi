package com.yubi.application.shop;

import java.util.HashMap;
import java.util.Map;

public class Basket {
	
	public final Map<String, BasketItem> items = new HashMap<String, BasketItem>();
	
	public Map<String, BasketItem> getItems() {
		return items;
	}
}