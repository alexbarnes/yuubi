package com.yubi.application.order;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
}
