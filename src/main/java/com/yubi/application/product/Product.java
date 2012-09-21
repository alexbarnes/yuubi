package com.yubi.application.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private int version;
	
	private String description;
	
	private BigDecimal unitPrice;
	
	 @OneToMany(cascade = CascadeType.ALL, mappedBy = "id.product", orphanRemoval = true)
	private List<ProductComponent> components = new ArrayList<ProductComponent>();

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ProductComponent> getComponents() {
		return components;
	}

	public void setComponents(List<ProductComponent> components) {
		this.components = components;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
}
