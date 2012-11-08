package com.yubi.shop;

import javax.inject.Inject;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
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
	
	private final CategoryService categoryService;
	
	private ProductAccess productAccess;
	
	private CategoryAccess categoryAccess;
	
	@Inject
	public ShopController(
			CategoryService categoryService,
			ProductAccess productAccess,
			CategoryAccess categoryAccess) {
		super();
		this.categoryService = categoryService;
		this.productAccess = productAccess;
		this.categoryAccess = categoryAccess;
	}

	
	@RequestMapping
	public ModelAndView showShopHome() {
		ModelAndView model = new ModelAndView("shop/shop");
		model.addObject("menu", categoryService.buildProductMenu());
		return model;
	}
	
	/**
	 * 
	 * List the products for a certain category.
	 * @return
	 */
	@RequestMapping("/category/view/{id}")
	public ModelAndView showCategory(@PathVariable("id") long id) {
		ModelAndView mav =  new ModelAndView("shop/productlist");
		mav.addObject("products", productAccess.listForCategory(id));
		mav.addObject("menu", categoryService.buildProductMenu());
		mav.addObject("category", categoryAccess.loadWithChildren(id));
		return mav;
	}
	
	
	/**
	 * View a specific product detail.
	 */
	@RequestMapping("/product/view/{code}")
	public ModelAndView viewProduct(@PathVariable("code") String code) {
		ModelAndView mav = new ModelAndView("shop/productdetail");
		mav.addObject("product", productAccess.load(code));
		mav.addObject("menu", categoryService.buildProductMenu());
		return mav;
	}
	
	
	@RequestMapping("/about")
	public String showAbout() {
		return "shop/about";
	}
	
	
	@RequestMapping("/deliveryinfo")
	public String showDeliveryInfo() {
		return "shop/deliveryinfo";
	}
	
	
	@RequestMapping("/terms")
	public String showTerms() {
		return "shop/terms";
	}
	
	
	@RequestMapping(value = "/product/image/{id}", produces = "image/png")
	public @ResponseBody byte[] loadImage(@PathVariable("id") long id) {
		return productAccess.loadImage(id).getImage();
	}
	
	
	@RequestMapping(value = "/product/primaryimage/{code}", produces = "image/png")
	public @ResponseBody byte[] loadProductPrimaryImage(@PathVariable("code") String code, ServletResponse response) {
		response.setContentType("");
		return productAccess.loadPrimaryImage(code).getImage();
	}
	
	
	@RequestMapping(value = "/product/thumbnail/{code}", produces = "image/png")
	public @ResponseBody byte[] loadProductThumbnail(@PathVariable("code") String code) {
		return productAccess.load(code).getThumbnail();
	}
	
	
	@RequestMapping(value = "/category/image/{id}", produces = "image/png")
	public @ResponseBody byte[] loadCategoryImage(@PathVariable("id") long id) {
		return categoryAccess.loadWithChildren(id).getImage();
	}
}