package com.yubi.application.category;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category {
	
	public enum CategoryType {
		COMPONENT,
		PRODUCT;
	}
	
	@Id
	@GeneratedValue
	private long id;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "parentCategoryId", nullable = true)
	private Category parentCategory;
	
	@OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	private List<Category> childCategories;
	
	@Enumerated(EnumType.STRING)
	private CategoryType type;

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

	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}
}
