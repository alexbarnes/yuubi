package com.yubi.application.order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class ProductOrder {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private int version;
	
	private String paypalToken;
	
	private String paypalTransactionId;
	
	@OneToMany(mappedBy = "id.order", cascade=CascadeType.ALL, orphanRemoval=true)
	private final List<ProductOrderItem> items = new ArrayList<ProductOrderItem>();
	
	@OneToMany(mappedBy = "id.order")
	private final Set<OrderDiscount> discounts = new HashSet<OrderDiscount>();

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

	public String getPaypalToken() {
		return paypalToken;
	}

	public void setPaypalToken(String paypalToken) {
		this.paypalToken = paypalToken;
	}

	public String getPaypalTransactionId() {
		return paypalTransactionId;
	}

	public void setPaypalTransactionId(String paypalTransactionId) {
		this.paypalTransactionId = paypalTransactionId;
	}

	public List<ProductOrderItem> getItems() {
		return items;
	}

	public Set<OrderDiscount> getDiscounts() {
		return discounts;
	}
}
