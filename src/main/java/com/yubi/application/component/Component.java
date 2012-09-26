package com.yubi.application.component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.NotEmpty;

import com.yubi.application.supplier.Supplier;

@Entity
@Indexed
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Component {

	@Id
	@GeneratedValue
	@DocumentId
	private long id;

	@Version
	private int version;

	@Field(analyze = Analyze.YES)
	@NotEmpty
	private String description;

	@Field
	private String supplierProductCode;

	@ManyToOne
	@JoinColumn(name = "supplierId", nullable = true)
	private Supplier supplier;

	@ManyToOne(optional = true)
	@JoinColumn(name = "categoryId")
	private ComponentCategory category;

	@Transient
	private long supplierId;

	private int stock;

	private int pendingStock;

	private int stockAlertLimit;

	private BigDecimal cost;

	@OneToMany(mappedBy = "component", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<StockHistory> stockHistory = new ArrayList<StockHistory>();

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSupplierCode() {
		return supplierProductCode;
	}

	public void setSupplierCode(String supplierProductCode) {
		this.supplierProductCode = supplierProductCode;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public long getId() {
		return id;
	}

	public List<StockHistory> getStockHistory() {
		return stockHistory;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getSupplierProductCode() {
		return supplierProductCode;
	}

	public void setSupplierProductCode(String supplierProductCode) {
		this.supplierProductCode = supplierProductCode;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getStockAlertLimit() {
		return stockAlertLimit;
	}

	public void setStockAlertLimit(int stockAlertLimit) {
		this.stockAlertLimit = stockAlertLimit;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSupplierId() {
		// If we haven't set the supplierId and the supplier isn't null
		// set the supplierId
		if (supplierId == 0 && supplier != null) {
			supplierId = supplier.getId();
		}
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public StockHistory addHistory() {
		StockHistory history = new StockHistory();
		history.setComponent(this);
		this.stockHistory.add(history);
		return history;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Component)) {
			return false;
		}

		return ((Component) obj).getId() == this.id;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	public int getPendingStock() {
		return pendingStock;
	}

	public void setPendingStock(int pendingStock) {
		this.pendingStock = pendingStock;
	}

}
