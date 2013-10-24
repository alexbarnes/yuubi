package com.yubi.shop.basket;

import java.math.BigDecimal;
import java.util.Currency;

import org.springframework.stereotype.Service;

@Service
public class BasketServiceImpl implements BasketService {

	public BigDecimal getBasketTotal(Basket basket, Currency currency) {
		BigDecimal total = basket.getTotal(currency);

		// Apply any discounts
		BigDecimal discountAmount = getDiscountAmount(basket, currency);
		total = total.subtract(discountAmount);
		
		// -- Add on shipping
		if (basket.getDeliveryMethod() != null) {
			total = total.add(basket.getDeliveryMethod().getCost());
		}
		
		return total;
	}

	public BigDecimal getDiscountAmount(Basket basket, Currency currency) {
		if (basket.getDiscount() != null) {
				return basket.getTotal(currency).multiply(new BigDecimal(basket.getDiscount().getPercentage()).divide(new BigDecimal(100)));
		}
		
		return new BigDecimal(0.00);
	}
}
