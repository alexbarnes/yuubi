package com.yubi.application.category;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;

@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category {
	
	@Id
	private long id;
	
	@Field
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "parentCategoryId", nullable = true)
	private Category parentCategory;
	
	@OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	private List<Category> childCategories;
	
	@Lob
	private byte[] image;
	
	@Lob
	private String categoryText;
	
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

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<Category> getChildCategories() {
		return childCategories;
	}

	public void setChildCategories(List<Category> childCategories) {
		this.childCategories = childCategories;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public String getUrlName() {
		return description.replace(" ", "-").toLowerCase();
	}

	public String getCategoryText() {
		return categoryText;
	}

	public void setCategoryText(String categoryDescription) {
		this.categoryText = categoryDescription;
	}
}
