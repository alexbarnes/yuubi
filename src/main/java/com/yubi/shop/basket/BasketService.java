package com.yubi.shop.basket;

import java.math.BigDecimal;

public interface BasketService {
	
	public BigDecimal getBasketTotal(Basket basket);
	
	public BigDecimal getDiscountAmount(Basket basket);

}
