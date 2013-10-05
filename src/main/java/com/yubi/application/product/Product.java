package com.yubi.application.product;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.constraints.NotEmpty;

import com.yubi.application.category.Category;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Indexed
public class Product {

	@Id
	private String code;

	@Version
	private int version;
	
	@Field(analyze = Analyze.YES)
	private String title;

	@NotEmpty
	@Field(analyze = Analyze.YES)
	private String description;

	@Field
	private BigDecimal unitPrice;

	private int stockLevel;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "setCode", nullable = true)
	private ProductSet set;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private final Set<ProductImage> images = new HashSet<ProductImage>();

	@ManyToOne(optional = true)
	@JoinColumn(name = "categoryId")
	@IndexedEmbedded
	private Category category;
	
	@Lob
	@Field
	private String productDescription;
	
	private boolean allowUpgradedWires; 
	
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

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
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


	public ProductImage addImage() {
		ProductImage image = new ProductImage();
		image.setProduct(this);
		images.add(image);
		return image;
	}

	public int getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(int stockLevel) {
		this.stockLevel = stockLevel;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ProductSet getSet() {
		return set;
	}
	
	public String getUrlName() {
		return title.replace(" ", "_").toLowerCase();
	}
	
	public String getProductDescription() {
		return productDescription;
	}
	
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	public boolean isAllowUpgradedWires() {
		return allowUpgradedWires;
	}
	
	public void setAllowUpgradedWires(boolean allowUpgradedWires) {
		this.allowUpgradedWires = allowUpgradedWires;
	}
}
