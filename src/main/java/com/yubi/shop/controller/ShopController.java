package com.yubi.shop.controller;

import java.util.Currency;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.yubi.application.category.CategoryService;
import com.yubi.application.product.ProductAccess;
import com.yubi.application.product.ProductService;
import com.yubi.core.statistics.EventGateway;
import com.yubi.core.statistics.ShopEvent;
import com.yubi.core.statistics.ShopEventType;
import com.yubi.shop.basket.Basket;
import com.yubi.shop.basket.BasketCreationListener;

@Controller
@RequestMapping("/shop")
public class ShopController {
	
	private final ProductAccess productAccess;
	
	private final CategoryService categoryService;
	
	private final EventGateway eventGateway;
	
	private final ProductService productService;
	
	@Inject
	public ShopController(
			ProductAccess productAccess,
			CategoryService categoryService,
			EventGateway eventGateway,
			ProductService productService) {
		super();
		this.productAccess = productAccess;
		this.categoryService = categoryService;
		this.eventGateway = eventGateway;
		this.productService = productService;
	}

	
	/**
	 * Show the shop home page. 
	 */
	@RequestMapping
	public ModelAndView showShopHome(HttpSession session, HttpServletRequest request) {
		session.setAttribute("current_url", request.getRequestURI());
		ModelAndView modelAndView =  new ModelAndView("shop/shop");
		
		modelAndView.addObject("menu", categoryService.buildProductMenu());
		modelAndView.addObject("sets", productAccess.listSets());
		
		return modelAndView;
	}
	
	
	@RequestMapping("/currency/change")
	public String changeCurrency(String currencyCode, String url, HttpSession session, HttpServletRequest request) {
		session.setAttribute("currency", Currency.getInstance(currencyCode));
		return "redirect:" + url;
	}
	
	/**
	 * List the products for a certain category.
	 */
	@RequestMapping("/category/view/{id}/*")
	public ModelAndView showCategory(@PathVariable("id") long id, HttpSession session, HttpServletRequest request) {
		ModelAndView mav =  new ModelAndView("shop/productlist");
		mav.addObject("menu", categoryService.buildProductMenu());
		mav.addObject("products", productAccess.listForCategory(id));
		mav.addObject("active", categoryService.load(id).getDescription());
		session.setAttribute("current_url", request.getRequestURI());
		
		ShopEvent event = new ShopEvent();
		event.setDate(new Date());
		event.setEntityKey(String.valueOf(id));
		event.setSessionId(session.getId());
		event.setType(ShopEventType.CATEGORY_VIEW);
		eventGateway.recordShopEvent(event);
		
		return mav;
	}
	
	
	@RequestMapping("/product/searchresults")
	public ModelAndView showProductList(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("shop/productlist");
		if (RequestContextUtils.getInputFlashMap(request) == null ) {
			mav.addObject("active", "No search results to show");
		}
		session.setAttribute("current_url", request.getRequestURI());
		
		mav.addObject("menu", categoryService.buildProductMenu());
		return mav;
	}
	
	
	/**
	 * View a specific product detail.
	 */
	@RequestMapping("/product/view/{code}/*")
	public ModelAndView viewProduct(@PathVariable("code") String code, HttpSession session, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("shop/productdetail");
		mav.addObject("menu", categoryService.buildProductMenu());
		mav.addObject("product", productAccess.load(code));
		session.setAttribute("current_url", request.getRequestURI());
		
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
	public ModelAndView showAbout(HttpSession session, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("shop/about");
		mav.addObject("menu", categoryService.buildProductMenu());
		session.setAttribute("current_url", request.getRequestURI());
		return mav;
	}
	
	
	/**
	 * Show the delivery info page
	 */
	@RequestMapping("/deliveryinfo")
	public ModelAndView showDeliveryInfo(HttpSession session, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("shop/deliveryinfo");
		mav.addObject("menu", categoryService.buildProductMenu());
		session.setAttribute("current_url", request.getRequestURI());
		return mav;
	}
	
	
	/**
	 * Show the terms and conditions page
	 */
	@RequestMapping("/terms")
	public ModelAndView showTerms(HttpSession session, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("shop/terms");
		mav.addObject("menu", categoryService.buildProductMenu());
		session.setAttribute("current_url", request.getRequestURI());
		return mav;
	}
}