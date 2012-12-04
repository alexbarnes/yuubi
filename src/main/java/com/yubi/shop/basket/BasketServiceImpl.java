package com.yubi.shop.basket;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class BasketServiceImpl implements BasketService {

	public BigDecimal getBasketTotal(Basket basket) {
		BigDecimal total = basket.getTotal();

		// Add shipping if selected
		if (basket.getDeliveryMethod() != null) {
			total = total.add(basket.getDeliveryMethod().getCost());
		}

		// Apply any discounts
		if (basket.getDiscount() != null) { 
			switch (basket.getDiscount().getType()) {
			case FREE_SHIPPING:
				total = total.subtract(basket.getDeliveryMethod().getCost());
				break;
	
			case GIFT_VOUCHER:
				total = total.subtract(basket.getDiscount().getAmount());
				break;
	
			case PROMOTION:
				total = total.multiply(
						new BigDecimal(100 - basket.getDiscount().getPercentage()))
						.multiply(new BigDecimal(100));
				break;
	
			default:
				throw new IllegalArgumentException("Unknown discount type ["
						+ basket.getDiscount().getType() + "].");
			}
		}
		return total;
	}

}
