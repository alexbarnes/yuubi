package com.yubi.core.country;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class Country {
	
	@Id
	private String code;
	
	private String description;
	
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
