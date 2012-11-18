package com.yubi.shop;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yubi.application.category.CategoryAccess;
import com.yubi.application.category.CategoryService;
import com.yubi.application.product.ProductAccess;

@Controller
@RequestMapping("/shop")
public class ShopController {
	
	private final ProductAccess productAccess;
	
	private final CategoryAccess categoryAccess;
	
	private final CategoryService categoryService;
	
	@Inject
	public ShopController(
			ProductAccess productAccess,
			CategoryAccess categoryAccess,
			CategoryService categoryService) {
		super();
		this.productAccess = productAccess;
		this.categoryAccess = categoryAccess;
		this.categoryService = categoryService;
	}

	
	@RequestMapping
	public ModelAndView showShopHome() {
		ModelAndView modelAndView =  new ModelAndView("shop/shop");
		
		modelAndView.addObject("menu", categoryService.buildProductMenu());
		modelAndView.addObject("sets", productAccess.listSets());
		
		return modelAndView;
	}
	
	/**
	 * 
	 * List the products for a certain category.
	 * @return
	 */
	@RequestMapping("/category/view/{id}")
	public ModelAndView showCategory(@PathVariable("id") long id) {
		ModelAndView mav =  new ModelAndView("shop/productlist");
		mav.addObject("menu", categoryService.buildProductMenu());
		mav.addObject("products", productAccess.listForCategory(id));
		mav.addObject("category", categoryAccess.loadWithChildren(id));
		return mav;
	}
	
	
	/**
	 * View a specific product detail.
	 */
	@RequestMapping("/product/view/{code}")
	public ModelAndView viewProduct(@PathVariable("code") String code) {
		ModelAndView mav = new ModelAndView("shop/productdetail");
		mav.addObject("menu", categoryService.buildProductMenu());
		mav.addObject("product", productAccess.load(code));
		return mav;
	}
	
	
	@RequestMapping("/about")
	public ModelAndView showAbout() {
		ModelAndView mav = new ModelAndView("shop/about");
		mav.addObject("menu", categoryService.buildProductMenu());
		return mav;
	}
	
	
	@RequestMapping("/deliveryinfo")
	public ModelAndView showDeliveryInfo() {
		ModelAndView mav = new ModelAndView("shop/deliveryinfo");
		mav.addObject("menu", categoryService.buildProductMenu());
		return mav;
	}
	
	
	@RequestMapping("/terms")
	public ModelAndView showTerms() {
		ModelAndView mav = new ModelAndView("shop/terms");
		mav.addObject("menu", categoryService.buildProductMenu());
		return mav;
	}
	
	@RequestMapping("/product/stock/{code}")
	public ModelAndView getProductStock(@PathVariable("code") String code) {
		ModelAndView view =  new ModelAndView("shop/stocklevel");
		view.addObject("product", productAccess.load(code));
		return view;
	}
}