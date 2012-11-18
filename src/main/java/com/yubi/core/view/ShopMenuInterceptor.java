package com.yubi.core.view;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yubi.application.category.CategoryService;
import com.yubi.application.product.ProductAccess;

public class ShopMenuInterceptor extends HandlerInterceptorAdapter {
	
	private final CategoryService categoryService;
	
	private final ProductAccess productAccess;
	
	@Inject
	public ShopMenuInterceptor(
			CategoryService categoryService,
			ProductAccess productAccess) {
		super();
		this.categoryService = categoryService;
		this.productAccess = productAccess;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		modelAndView.addObject("menu", categoryService.buildProductMenu());
		modelAndView.addObject("sets", productAccess.listSets());
	}
}