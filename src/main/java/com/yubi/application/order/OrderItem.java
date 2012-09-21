package com.yubi.application.order;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yubi.application.component.Component;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderItem {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private int version;
	
	@ManyToOne
	@JoinColumn(name = "componentId")
	private Component component;
	
	private int number;
	
	private int numberReceived;
	
	private BigDecimal cost;
	
	@ManyToOne
	@JoinColumn(name = "orderId")
	private ComponentOrder order;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
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

	public ComponentOrder getOrder() {
		return order;
	}

	public void setOrder(ComponentOrder order) {
		this.order = order;
	}
}
