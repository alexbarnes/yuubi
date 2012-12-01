package com.yubi.application.category;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryAccess categoryAccess;

	@Inject
	public CategoryServiceImpl(CategoryAccess categoryAccess) {
		super();
		this.categoryAccess = categoryAccess;
	}

	@Transactional
	public List<Category> buildProductMenu() {
		return categoryAccess.listProductParentCategories();
	}

	@Transactional
	public Category load(long id) {
		return categoryAccess.load(id);
	}

	@Transactional
	public Category loadWithChildren(long id) {
		Category category = categoryAccess.load(id);
		Hibernate.initialize(category.getChildCategories());
		return category;
	}
}
