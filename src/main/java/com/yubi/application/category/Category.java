package com.yubi.application.component;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Cacheable
public class ComponentCategory {
	
	@Id
	private String code;
	
	private String description;

	public String getCode() {
		return code;
	}

	public void setId(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
