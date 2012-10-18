package com.yubi.application.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryAccess categoryAccess;
	
	@Inject
	public CategoryServiceImpl(CategoryAccess categoryAccess) {
		super();
		this.categoryAccess = categoryAccess;
	}

	@Transactional
	public Map<String, Integer> buildProductMenu() {
		List<Category> categories = categoryAccess.listProductParentCategories();
		
		List<Map<Integer, String>> menu = new ArrayList<Map<Integer, String>>();
		
		for (Category parent : categories) {
		}
	}

}
