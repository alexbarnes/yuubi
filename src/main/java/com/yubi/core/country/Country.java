package com.yubi.application.core;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yubi.application.shop.DeliveryMethod;

@Entity
public class Country {
	
	@Id
	private String code;
	
	private String description;
	
	@OneToMany(mappedBy = "country")
	private List<DeliveryMethod> deliveryMethods;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DeliveryMethod> getDeliveryMethods() {
		return deliveryMethods;
	}

	public void setDeliveryMethods(List<DeliveryMethod> deliveryMethods) {
		this.deliveryMethods = deliveryMethods;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof Country)) {
			return false;
		}
		
		Country other = (Country)obj;
		return this.code.equals(other.getCode());
	}
	
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.code).toHashCode();
	}
}
