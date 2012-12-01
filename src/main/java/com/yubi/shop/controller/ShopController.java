package com.yubi.shop;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.yubi.application.category.CategoryAccess;
import com.yubi.application.category.CategoryService;
import com.yubi.application.product.ProductAccess;
import com.yubi.application.product.ProductService;
import com.yubi.core.statistics.EventGateway;
import com.yubi.core.statistics.ShopEvent;
import com.yubi.core.statistics.ShopEventType;

@Controller
@RequestMapping("/shop")
public class ShopController {
	
	private final ProductAccess productAccess;
	
	private final CategoryAccess categoryAccess;
	
	private final CategoryService categoryService;
	
	private final EventGateway eventGateway;
	
	private final ProductService productService;
	
	@Inject
	public ShopController(
			ProductAccess productAccess,
			CategoryAccess categoryAccess,
			CategoryService categoryService,
			EventGateway eventGateway,
			ProductService productService) {
		super();
		this.productAccess = productAccess;
		this.categoryAccess = categoryAccess;
		this.categoryService = categoryService;
		this.eventGateway = eventGateway;
		this.productService = productService;
	}

	
	/**
	 * Show the shop home page. 
	 */
	@RequestMapping
	public ModelAndView showShopHome() {
		ModelAndView modelAndView =  new ModelAndView("shop/shop");
		
		modelAndView.addObject("menu", categoryService.buildProductMenu());
		modelAndView.addObject("sets", productAccess.listSets());
		
		return modelAndView;
	}
	
	/**
	 * List the products for a certain category.
	 */
	@RequestMapping("/category/view/{id}")
	public ModelAndView showCategory(@PathVariable("id") long id, HttpSession session) {
		ModelAndView mav =  new ModelAndView("shop/productlist");
		mav.addObject("menu", categoryService.buildProductMenu());
		mav.addObject("products", productAccess.listForCategory(id));
		mav.addObject("active", categoryAccess.load(id).getDescription());
		
		ShopEvent event = new ShopEvent();
		event.setDate(new Date());
		event.setEntityKey(String.valueOf(id));
		event.setSessionId(session.getId());
		event.setType(ShopEventType.CATEGORY_VIEW);
		eventGateway.recordShopEvent(event);
		
		return mav;
	}
	
	
	@RequestMapping("/product/searchresults")
	public ModelAndView showProductList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("shop/productlist");
		if (RequestContextUtils.getInputFlashMap(request) == null ) {
			mav.addObject("active", "No search results to show");
		}
		
		mav.addObject("menu", categoryService.buildProductMenu());
		return mav;
	}
	
	
	/**
	 * View a specific product detail.
	 */
	@RequestMapping("/product/view/{code}")
	public ModelAndView viewProduct(@PathVariable("code") String code, HttpSession session) {
		ModelAndView mav = new ModelAndView("shop/productdetail");
		mav.addObject("menu", categoryService.buildProductMenu());
		mav.addObject("product", productAccess.load(code));
		
		ShopEvent event = new ShopEvent();
		event.setDate(new Date());
		event.setEntityKey(code);
		event.setSessionId(session.getId());
		event.setType(ShopEventType.PRODUCT_VIEW);
		eventGateway.recordShopEvent(event);
		
		return mav;
	}
	
	@RequestMapping(value = "/product/search", method = RequestMethod.POST)
	public ModelAndView searchForProducts(String query, RedirectAttributes redirectAttributes, HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect:/shop/product/searchresults");
		redirectAttributes.addFlashAttribute("active", "Search: " + query);
		redirectAttributes.addFlashAttribute("products", productService.search(query));
		
		ShopEvent event = new ShopEvent();
		event.setDate(new Date());
		event.setEntityKey(StringUtils.substring(query, 0, 20));
		event.setSessionId(session.getId());
		event.setType(ShopEventType.SEARCH);
		eventGateway.recordShopEvent(event);
		
		return mav;
	}
	
	
	/**
	 * Show the about page
	 */
	@RequestMapping("/about")
	public ModelAndView showAbout() {
		ModelAndView mav = new ModelAndView("shop/about");
		mav.addObject("menu", categoryService.buildProductMenu());
		return mav;
	}
	
	
	/**
	 * Show the delivery info page
	 */
	@RequestMapping("/deliveryinfo")
	public ModelAndView showDeliveryInfo() {
		ModelAndView mav = new ModelAndView("shop/deliveryinfo");
		mav.addObject("menu", categoryService.buildProductMenu());
		return mav;
	}
	
	
	/**
	 * Show the terms and conditions page
	 */
	@RequestMapping("/terms")
	public ModelAndView showTerms() {
		ModelAndView mav = new ModelAndView("shop/terms");
		mav.addObject("menu", categoryService.buildProductMenu());
		return mav;
	}
	
	
	/**
	 * Show the product stock section of the product detail page
	 */
	@RequestMapping("/product/stock/{code}")
	public ModelAndView getProductStock(@PathVariable("code") String code) {
		ModelAndView view =  new ModelAndView("shop/stocklevel");
		view.addObject("product", productAccess.load(code));
		return view;
	}
}