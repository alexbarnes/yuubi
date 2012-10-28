package com.yubi.application.category;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
	
	private final CategoryAccess categoryAccess;
	
	@Inject
	public CategoryController(CategoryAccess categoryAccess) {
		super();
		this.categoryAccess = categoryAccess;
	}

	
	@RequestMapping("/view/{id}")
	public Model viewCategory(@PathVariable("id") long id) {
		return new Model(ScreenMode.ENQUIRE, "category", "category", categoryAccess.loadWithChildren(id));
	}
	
	
	@RequestMapping("/edit/{id}")
	public Model editCategory(@PathVariable("id") long id) {
		return new Model(ScreenMode.ENQUIRE, "category", "category", categoryAccess.loadWithChildren(id));
	}

}
