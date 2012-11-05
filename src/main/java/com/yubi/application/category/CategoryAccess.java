package com.yubi.application.category;

import java.util.List;

public interface CategoryAccess {
	
	public Category loadWithChildren(long id);
	
	public List<Category> listProductParentCategories();
}
