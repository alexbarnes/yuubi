package com.yubi.shop.basket;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.yubi.shop.discount.Discount.DiscountType;

@Service
public class BasketServiceImpl implements BasketService {

	public BigDecimal getBasketTotal(Basket basket) {
		BigDecimal total = basket.getTotal();

		// Apply any discounts
		if (basket.getDiscount() != null) {
			switch (basket.getDiscount().getType()) {
			case PROMOTION:
				total = total.multiply(
						new BigDecimal(100 - basket.getDiscount()
								.getPercentage()).divide(new BigDecimal(100)));
				break;

			default:
				throw new IllegalArgumentException("Unknown discount type ["
						+ basket.getDiscount().getType() + "].");
			}
		}
		
		// Add shipping if selected and not free
		if (basket.getDeliveryMethod() != null) {
			if(basket.getDiscount() != null && 
					basket.getDiscount().getType() != DiscountType.FREE_SHIPPING) {
				total = total.add(basket.getDeliveryMethod().getCost());
			}
		}
		
		return total;
	}

}
