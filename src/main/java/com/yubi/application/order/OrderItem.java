package com.yubi.application.order;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yubi.application.component.Component;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderItem {

	@Embeddable
	public static class Id implements Serializable {

		private static final long serialVersionUID = -7311451400406106597L;

		@ManyToOne
		@JoinColumn(name = "componentId")
		private Component component;

		@ManyToOne
		@JoinColumn(name = "orderId")
		private ComponentOrder order;

		public Component getComponent() {
			return component;
		}

		public void setComponent(Component component) {
			this.component = component;
		}

		public ComponentOrder getOrder() {
			return order;
		}

		public void setOrder(ComponentOrder order) {
			this.order = order;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;

			if (!(obj instanceof Id)) {
				return false;
			}

			Id other = (Id) obj;

			return other.getComponent().getId() == this.component.getId()
					&& other.getOrder().getId() == this.order.getId();
		}

		@Override
		public int hashCode() {
			return this.component.hashCode() + this.order.hashCode();
		}

	}

	@EmbeddedId
	private Id id = new Id();

	@Min(value = 1, message = "At least one of this component must be entered")
	private int number;

	private int numberReceived;

	private BigDecimal cost;

	@Transient
	@Min(value = 1, message = "The component must be set")
	private long componentId;

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumberReceived() {
		return numberReceived;
	}

	public void setNumberReceived(int numberReceived) {
		this.numberReceived = numberReceived;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public long getComponentId() {
		return componentId;
	}

	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}
}
