package com.yubi.application.category;

import java.util.List;

public interface CategoryService {
	
	public List<Category> buildProductMenu();
	
	public List<Category> listTopLevelCategories();
}
