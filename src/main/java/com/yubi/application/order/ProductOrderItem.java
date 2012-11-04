package com.yubi.application.shop.order;

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
	public static class Id {
		
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
	private Id id;
	
	private int quantity;
	
	private BigDecimal totalCost;

}
