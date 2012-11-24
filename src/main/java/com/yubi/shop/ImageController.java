package com.yubi.shop;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yubi.application.category.CategoryAccess;
import com.yubi.application.product.ProductAccess;

@Controller
@RequestMapping("/shop")
public class ImageController {
	
	private final ProductAccess productAccess;
	
	private final CategoryAccess categoryAccess;
	
	@Inject
	public ImageController(
			ProductAccess productAccess,
			CategoryAccess categoryAccess) {
		super();
		this.productAccess = productAccess;
		this.categoryAccess = categoryAccess;
	}
	
	@RequestMapping(value = "/product/image/{id}", produces = "image/png")
	public @ResponseBody byte[] loadImage(@PathVariable("id") long id, HttpServletResponse response) {
		return productAccess.loadImage(id).getImage();
	}
	
	
	@RequestMapping(value = "/product/primaryimage/{code}", produces = "image/png")
	public @ResponseBody byte[] loadProductPrimaryImage(@PathVariable("code") String code, HttpServletResponse response) {
		return productAccess.loadPrimaryImage(code).getImage();
	}
	
	
	@RequestMapping(value = "/category/image/{id}", produces = "image/png")
	public @ResponseBody byte[] loadCategoryImage(@PathVariable("id") long id, HttpServletResponse response) {
		return categoryAccess.loadWithChildren(id).getImage();
	}
}
