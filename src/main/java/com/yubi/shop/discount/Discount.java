package com.yubi.shop.discount;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Version;

import org.joda.time.LocalDate;

@Entity
public class Discount {
	
	public enum DiscountType {
		FREE_SHIPPING,
		PROMOTION;
	}
	
	@Id
	private String code;
	
	private String description;
	
	@Version
	private int version;
	
	private BigDecimal amount;
	
	private int percentage;
	
	private LocalDate validFrom;
	
	private LocalDate validTo;
	
	private BigDecimal minimumSpend; 
	
	@Enumerated(EnumType.STRING)
	private DiscountType type;

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDate getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}

	public BigDecimal getMinimumSpend() {
		return minimumSpend;
	}

	public void setMinimumSpend(BigDecimal minimumSpend) {
		this.minimumSpend = minimumSpend;
	}

	public DiscountType getType() {
		return type;
	}

	public void setType(DiscountType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
