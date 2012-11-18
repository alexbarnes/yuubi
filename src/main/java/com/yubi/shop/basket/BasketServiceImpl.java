package com.yubi.shop.basket;

import java.math.BigDecimal;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.product.Product;
import com.yubi.application.product.ProductAccess;
import com.yubi.shop.discount.Discount;
import com.yubi.shop.discount.DiscountAccess;

@Service
public class BasketServiceImpl implements BasketService {
	
	private final ProductAccess productAccess;
	
	private final DiscountAccess discountAccess;
	
	@Inject
	public BasketServiceImpl(ProductAccess productAccess,
			DiscountAccess discountAccess) {
		super();
		this.productAccess = productAccess;
		this.discountAccess = discountAccess;
	}


	@Transactional
	public BigDecimal getBasketTotal(Basket basket) {
		BigDecimal total = new BigDecimal(0.00);
		
		for (Entry<String, BasketItem> item : basket.getItems().entrySet()) {
			Product product = productAccess.load(item.getValue().getProductCode());
			total = total.add((product.getUnitPrice().multiply(new BigDecimal(item.getValue().getNumber()))));
		}
		
		// Add shipping if selected
		if (basket.getDeliveryMethod() != null) {
			total.add(basket.getDeliveryMethod().getCost());
		}
		
		// Apply any discounts
		for (String item : basket.getDiscounts()) {
			Discount discount = discountAccess.get(item);
			
			switch (discount.getType()) {
			case FREE_SHIPPING:
				total.subtract(basket.getDeliveryMethod().getCost());
				break;
				
			case GIFT_VOUCHER:
				total.subtract(discount.getAmount());
				break;
				
			case PROMOTION:
				total.multiply(new BigDecimal(100-discount.getPercentage())).multiply(new BigDecimal(100));
				break;

			default:
				throw new IllegalArgumentException("Unknown discount type [" + discount.getType() + "].");
			}
		}
		
		
		return total;
	}

}
