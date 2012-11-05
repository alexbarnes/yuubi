package com.yubi.application.order;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.yubi.shop.discount.Discount;

@Entity
public class OrderDiscount {
	
	@Embeddable
	public static class Id implements Serializable {
		
		@ManyToOne
		@JoinColumn(name = "orderId")
		private ProductOrder order;
		
		@ManyToOne
		@JoinColumn(name = "discountCode")
		private Discount discount;
		
	}
	
	@EmbeddedId
	private Id id = new Id();

}
