package com.yubi.shop.basket;

import java.math.BigDecimal;
import java.util.Currency;

public interface BasketService {
	
	public BigDecimal getBasketTotal(Basket basket, Currency currency);
	
	public BigDecimal getDiscountAmount(Basket basket, Currency currency);

}
