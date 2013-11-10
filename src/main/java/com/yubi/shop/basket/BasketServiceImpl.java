package com.yubi.shop.basket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.product.Product;
import com.yubi.application.product.ProductAccess;
import com.yubi.shop.basket.Basket.BasketKey;

@Service
public class BasketServiceImpl implements BasketService {
	
	private final ProductAccess productAccess;
	
	@Inject
	public BasketServiceImpl(ProductAccess productAccess) {
		super();
		this.productAccess = productAccess;
	}

	public BigDecimal getBasketTotal(Basket basket, Currency currency) {
		BigDecimal total = basket.getTotal(currency);

		// Apply any discounts
		BigDecimal discountAmount = getDiscountAmount(basket, currency);
		total = total.subtract(discountAmount);
		
		// -- Add on shipping
		if (basket.getDeliveryMethod() != null) {
			total = total.add(basket.getDeliveryMethod().getCost());
		}
		
		total = total.setScale(2, RoundingMode.HALF_UP);
		return total;
	}

	public BigDecimal getDiscountAmount(Basket basket, Currency currency) {
		if (basket.getDiscount() != null) {
				BigDecimal amount = basket.getTotal(currency).multiply(new BigDecimal(basket.getDiscount().getPercentage()).divide(new BigDecimal(100)));
				return amount.setScale(2, RoundingMode.HALF_UP);
		}
		
		return new BigDecimal("0.00");
	}

	
	@Transactional
	public boolean checkStockLevels(Basket basket) {
		boolean notEnoughStock = false;
		for (Entry<BasketKey, BasketItem> item : basket.getItems().entrySet()) {
			Product product = productAccess.load(item.getKey().getCode());
			
			if (item.getValue().getNumber() > product.getStockLevel()) {
				item.getValue().setNotEnoughStock(true);
				notEnoughStock = true;
			}
		}
		
		return notEnoughStock;
	}
}
