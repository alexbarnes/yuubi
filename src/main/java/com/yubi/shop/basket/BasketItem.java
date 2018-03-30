package com.yubi.shop.basket;

import java.math.BigDecimal;
import java.util.Currency;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.yubi.application.product.Product;

public class BasketItem {

	private Product product;

	private int number;
	
	private boolean notEnoughStock;

	public BasketItem() {
	}

	public BasketItem(Product product, int number) {
		super();
		this.product = product;
		this.number = number;
	}

	public Product getProduct() {
		return product;
	}

	public int getNumber() {
		return number;
	}

	public String getProductDescription() {
		return product.getTitle();
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public BigDecimal getItemCost(Currency currency) {
		return product.getPriceInCurrency(currency);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (!(other instanceof BasketItem)) {
			return false;
		}

		BasketItem item = (BasketItem) other;

		return new EqualsBuilder().append(item.getProduct(),
				this.product).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.product).toHashCode();
	}

	public String getProductUrlDescription() {
		return product.getUrlName();
	}
	
	
	public BigDecimal getLineCost(Currency currency) {
		return product.getPriceInCurrency(currency).multiply(new BigDecimal(number));
	}

	public boolean isNotEnoughStock() {
		return notEnoughStock;
	}

	public void setNotEnoughStock(boolean notEnoughStock) {
		this.notEnoughStock = notEnoughStock;
	}
}
