package com.yubi.application.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product {

	@Id
	@NotEmpty
	private String code;

	@Version
	private int version;

	@NotEmpty
	private String description;

	private BigDecimal unitPrice;

	private Currency currency;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id.product", orphanRemoval = true)
	private List<ProductComponent> components = new ArrayList<ProductComponent>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) 
			return true;
		
		if (!(obj instanceof Product))
			return false;

		Product other = (Product) obj;
		return new EqualsBuilder().append(this.code, other.code).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(code).toHashCode();
	}

	public ProductComponent addComponent() {
		ProductComponent component = new ProductComponent();
		component.getId().setProduct(this);
		components.add(component);
		return component;

	}
}
