package com.yubi.application.admin;

import java.security.Principal;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yubi.application.category.CategoryService;
import com.yubi.application.product.Product;
import com.yubi.application.product.ProductService;
import com.yubi.application.user.PasswordChange;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	Logger log = LoggerFactory.getLogger(AdminController.class);
	
	private final ProductService productService;
	private final ShopStatusService shopStatusService;
	private final CategoryService categoryService;
	
	@Inject
	public AdminController(ProductService productService, ShopStatusService shopStatusService, CategoryService categoryService) {
		super();
		this.productService = productService;
		this.shopStatusService = shopStatusService;
		this.categoryService = categoryService;
		
	}
	
	@RequestMapping({"/home",""})
	public String home() {
		return "admin/home";
	}
	
	
	@RequestMapping("/shop")
	public ModelAndView shopAdmin() {
		ModelAndView mav = new ModelAndView("/admin/shop");
		mav.addObject("open", shopStatusService.isOpen());
		return mav;
	}
	
	
	@RequestMapping("/user")
	public ModelAndView userAdmin(Principal principal) {
		ModelAndView mav = new ModelAndView("/admin/user");
		mav.addObject("password", new PasswordChange(principal.getName()));
		return mav;
	}
	
	
	@RequestMapping("/products")
	public ModelAndView productAdmin() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/products");
		mav.addObject("categories", categoryService.buildProductMenu());
		mav.addObject("products", productService.listAll());
		return mav;
	}
	
	// -------------------------------------------------------------

	
	@RequestMapping(value="/stock/update/{code}")
	public @ResponseBody int updateStockLevel(@PathVariable("code") String code, int number) {
		productService.setStockLevel(code, number);
		return number;
	}
	
	
	@RequestMapping("/shop/close")
	public @ResponseBody boolean closeShop() {
		if (shopStatusService.isOpen()) {
			log.info("Shop is now closed");
			shopStatusService.updateStatus(false);
			return true;
		}
		
		return false;
	}
	
	
	@RequestMapping("/shop/open")
	public @ResponseBody boolean openShop() {
		if (!shopStatusService.isOpen()) {
			log.info("Shop is now open");
			shopStatusService.updateStatus(true);
			return true;
		}
		return false;
	}
	
	
	@RequestMapping("/product/hide/{code}")
	public @ResponseBody boolean hideProduct(@PathVariable("code") String code) {
		Product product = productService.load(code);
		product.setOnDisplay(false);
		productService.save(product);
		log.info("Product [" + code + "] marked as not available.");
		return true;
	}
	
	
	@RequestMapping("/product/show/{code}")
	public @ResponseBody boolean showProduct(@PathVariable("code") String code) {
		Product product = productService.load(code);
		product.setOnDisplay(true);
		productService.save(product);
		log.info("Product [" + code + "] marked as available.");
		return true;
	}
	
}
