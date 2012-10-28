package com.yubi.application.category;

import java.util.List;

import javax.inject.Inject;

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
}
