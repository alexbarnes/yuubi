package com.yubi.shop.basket;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class BasketItem {

	private String productCode;

	private int number;

	public BasketItem() {}

	public BasketItem(String productCode, int number) {
		super();
		this.productCode = productCode;
		this.number = number;
	}

	public String getProductCode() {
		return productCode;
	}

	

	public int getNumber() {
		return number;
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

		return new EqualsBuilder().append(item.getProductCode(),
				this.productCode).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.productCode).toHashCode();
	}
}
