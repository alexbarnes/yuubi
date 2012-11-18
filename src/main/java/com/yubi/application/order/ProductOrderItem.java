package com.yubi.application.order;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yubi.application.product.Product;

@Entity
public class ProductOrderItem {
	
	@Embeddable
	public static class Id implements Serializable {
		
		private static final long serialVersionUID = 2352606926259877161L;

		@ManyToOne
		@JoinColumn(name = "productCode")
		private Product product;
		
		@ManyToOne
		@JoinColumn(name = "orderId")
		private ProductOrder order;

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		public ProductOrder getOrder() {
			return order;
		}

		public void setOrder(ProductOrder order) {
			this.order = order;
		}
		
		@Override
		public int hashCode() {
			return new HashCodeBuilder()
				.append(product.getCode())
				.append(order.getId())
				.toHashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			
			if (!(obj instanceof Id)) {
				return false;
			}
			
			Id id = (Id)obj;
			
			return new EqualsBuilder()
				.append(id.getOrder().getId(), order.getId())
				.append(id.getProduct().getCode(), product.getCode())
				.isEquals();
		}
	}
	
	@EmbeddedId
	private final Id id = new Id();
	
	private int quantity;
	
	private BigDecimal totalCost;

	public Id getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof ProductOrderItem)) {
			return false;
		}
		ProductOrderItem other = (ProductOrderItem)obj;
		return new EqualsBuilder().append(other.getId(), this.getId()).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.id).toHashCode();
	}
}