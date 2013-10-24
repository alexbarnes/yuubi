package com.yubi.application.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.yubi.application.product.Product;
import com.yubi.shop.discount.Discount;

@Entity
public class ProductOrder {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private int version;
	
	private String paypalTransactionId;
	
	@OneToMany(mappedBy = "id.order", cascade=CascadeType.ALL, orphanRemoval=true)
	private final List<ProductOrderItem> items = new ArrayList<ProductOrderItem>();
	
	@ManyToOne
	@JoinColumn(name = "discountCode")
	private Discount discount;
	
	@Column(length=3)
	private String currencyCode;

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

	public String getPaypalTransactionId() {
		return paypalTransactionId;
	}

	public void setPaypalTransactionId(String paypalTransactionId) {
		this.paypalTransactionId = paypalTransactionId;
	}

	public List<ProductOrderItem> getItems() {
		return items;
	}

	public Discount getDiscount() {
		return discount;
	}
	
	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
	
	public ProductOrderItem addItem(Product product) {
		ProductOrderItem item = new ProductOrderItem();
		item.getId().setProduct(product);
		item.getId().setOrder(this);
		items.add(item);
		return item;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
