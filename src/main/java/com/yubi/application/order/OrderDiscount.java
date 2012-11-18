package com.yubi.application.order;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yubi.shop.discount.Discount;

@Entity
public class OrderDiscount {
	
	@Embeddable
	public static class Id implements Serializable {
		
		private static final long serialVersionUID = 6063960752993418043L;

		@ManyToOne
		@JoinColumn(name = "orderId")
		private ProductOrder order;
		
		@ManyToOne
		@JoinColumn(name = "discountCode")
		private Discount discount;
		
		public Discount getDiscount() {
			return discount;
		}
		
		public ProductOrder getOrder() {
			return order;
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
				.append(discount.getCode(), id.getDiscount().getCode())
				.append(id.getOrder().getId(), order.getId())
				.isEquals();
		}
		
		@Override
		public int hashCode() {
			return new HashCodeBuilder()
				.append(order.getId())
				.append(discount.getCode())
				.toHashCode();
		}
	}
	
	@EmbeddedId
	private Id id = new Id();
	
	public Id getId() {
		return id;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (!(obj instanceof OrderDiscount)) {
			return false;
		}
		
		OrderDiscount other = (OrderDiscount)obj;
		
		return new EqualsBuilder().append(other.id, this.id).isEquals();
	}
	
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.id).toHashCode();
	}
}