package com.yubi.application.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.yubi.application.product.Product;
import com.yubi.shop.delivery.DeliveryMethod;
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
	
	private String orderReference;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date enteredDate;
	
	private BigDecimal orderTotal;
	
	@ManyToOne
	@JoinColumn(name="deliveryMethodId")
	private DeliveryMethod delivery;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public String getOrderReference() {
		return orderReference;
	}

	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}

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
	
	
	public BigDecimal getProductTotal() {
		BigDecimal total = new BigDecimal("0.00");
		for (ProductOrderItem item : items) {
			total = total.add(item.getTotalCost());
		}
		return total;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public DeliveryMethod getDelivery() {
		return delivery;
	}

	public void setDelivery(DeliveryMethod delivery) {
		this.delivery = delivery;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Date getEnteredDate() {
		return enteredDate;
	}

	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}
}
