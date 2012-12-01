package com.yubi.application.category;

import java.util.List;

public interface CategoryService {
	
	public List<Category> buildProductMenu();
	
	public Category loadWithChildren(long id);
	
	public Category load(long id);
}
