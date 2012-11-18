package com.yubi.shop.basket;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class BasketItem {

	private String productCode;

	private String productDescription;

	private BigDecimal itemCost;

	private int number;

	public BasketItem() {
	}

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

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public BigDecimal getTotalCost() {
		return itemCost.multiply(new BigDecimal(number));
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public BigDecimal getItemCost() {
		return itemCost;
	}
	
	public void setItemCost(BigDecimal itemCost) {
		this.itemCost = itemCost;
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
