package com.yubi.application.product;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yubi.application.component.Component;

@Entity
public class ProductComponent {

	@SuppressWarnings("serial")
	@Embeddable
	public static class Id implements Serializable {

		@ManyToOne
		@JoinColumn(name = "productId")
		private Product product;

		@ManyToOne
		@JoinColumn(name = "componentId")
		private Component component;

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		public Component getComponent() {
			return component;
		}

		public void setComponent(Component component) {
			this.component = component;
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(product.hashCode())
					.append(component.hashCode()).toHashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Id)) {
				return false;
			}
			Id other = (Id) obj;
			return product.equals(other.getProduct())
					&& component.equals(other.getComponent());
		}

	}

	@EmbeddedId
	private final Id id = new Id();

	private int number;

	@Transient
	private long componentId;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Id getId() {
		return id;
	}

	public long getComponentId() {
		return componentId;
	}

	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}
}
