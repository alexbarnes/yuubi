package com.yubi.shop.delivery;

import java.math.BigDecimal;
import java.util.Currency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yubi.core.country.Country;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeliveryMethod {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "countryCode")
	private Country country;
	
	@Column(nullable = false)
	private BigDecimal cost;
	
	private BigDecimal costUsd;
	private BigDecimal costEur;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getCostUsd() {
		return costUsd;
	}

	public void setCostUsd(BigDecimal costUsd) {
		this.costUsd = costUsd;
	}

	public BigDecimal getCostEur() {
		return costEur;
	}

	public void setCostEur(BigDecimal costEur) {
		this.costEur = costEur;
	}
	
	public BigDecimal getCostInCurrency(Currency currency) {
		if (currency == Currency.getInstance("GBP")) {
			return cost;
		} else if (currency == Currency.getInstance("USD")) {
			return costUsd;
		} else if (currency == Currency.getInstance("EUR")) {
			return costEur;
		} else {
			throw new IllegalArgumentException("Unsupported currency [" + currency + "].");
		}
	}
}
