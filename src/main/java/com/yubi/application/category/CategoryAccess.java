package com.yubi.application.category;

import java.util.List;

interface CategoryAccess {
	
	public List<Category> listProductParentCategories();
	
	public Category load(long id);
}
