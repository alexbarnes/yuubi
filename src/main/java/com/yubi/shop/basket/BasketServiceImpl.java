package com.yubi.shop.basket;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class BasketServiceImpl implements BasketService {

	public BigDecimal getBasketTotal(Basket basket) {
		BigDecimal total = basket.getTotal();

		// Apply any discounts
		if (basket.getDiscount() != null) {
				total = total.multiply(
						new BigDecimal(100 - basket.getDiscount()
								.getPercentage()).divide(new BigDecimal(100)));

		}
		
		if (basket.getDeliveryMethod() != null) {
			total = total.add(basket.getDeliveryMethod().getCost());
		}
		
		return total;
	}

	public BigDecimal getDiscountAmount(Basket basket) {
		if (basket.getDiscount() != null) {
				return basket.getTotal().multiply(new BigDecimal(basket.getDiscount().getPercentage()).divide(new BigDecimal(100)));
		}
		
		return new BigDecimal(0.00);
	}
}
