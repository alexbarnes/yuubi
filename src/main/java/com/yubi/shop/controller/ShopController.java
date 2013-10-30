package com.yubi.shop.controller;

import java.util.Currency;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.yubi.application.category.CategoryService;
import com.yubi.application.product.ProductAccess;
import com.yubi.application.product.ProductService;
import com.yubi.core.mail.EmailService;
import com.yubi.core.mail.OutboundMailMessage;

@Controller
@RequestMapping("/shop")
public class ShopController {
	
	private final ProductAccess productAccess;
	private final CategoryService categoryService;
	private final ProductService productService;
	private final EmailService emailService;
	
	@Inject
	public ShopController(
			ProductAccess productAccess,
			CategoryService categoryService,
			ProductService productService,
			EmailService emailService) {
		super();
		this.productAccess = productAccess;
		this.categoryService = categoryService;
		this.productService = productService;
		this.emailService = emailService;
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
	public String changeCurrency(String currencyCode, String url, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		session.setAttribute("currency", Currency.getInstance(currencyCode));
		Cookie cookie = new Cookie("currency", currencyCode);
		cookie.setMaxAge(18144000);
		response.addCookie(cookie);
		return "redirect:" + url;
	}
	
	
	@RequestMapping("/showerror")
	public String errorHandle() {
		return "redirect:/shop/error";
	}
	
	
	@RequestMapping("/error")
	public String displayError() {
		return "/shop/error";
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
		return mav;
	}
	
	
	@RequestMapping("/product/searchresults")
	public ModelAndView showProductList(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("shop/productlist");
		if (RequestContextUtils.getInputFlashMap(request) == null) {
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
		return mav;
	}
	
	
	@RequestMapping(value = "/product/search", method = RequestMethod.POST)
	public ModelAndView searchForProducts(String query, RedirectAttributes redirectAttributes, HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect:/shop/product/searchresults");
		redirectAttributes.addFlashAttribute("active", "Search: " + query);
		redirectAttributes.addFlashAttribute("products", productService.search(query));
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
	
	
	@RequestMapping("/contact")
	public ModelAndView showContact(HttpSession session, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("shop/contact");
		mav.addObject("menu", categoryService.buildProductMenu());
		mav.addObject("message", new OutboundMailMessage());
		session.setAttribute("current_url", request.getRequestURI());
		return mav;
	}
	
	
	@RequestMapping(value = "/contact/send", method = RequestMethod.POST)
	public ModelAndView sendContactForm(OutboundMailMessage message, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("shop/contact");
		if (StringUtils.isEmpty(message.getFrom())) {
			mav.addObject("error-from", "true");
			return mav;
		}
		
		if (StringUtils.isEmpty(message.getText())) {
			mav.addObject("error-text", "true");
			return mav;
		}
		
		if (StringUtils.isEmpty(message.getFromName())) {
			mav.addObject("error-from-name", "true");
			return mav;
		}
		message.setSubject("Contact Form Submission [" + message.getFromName() + "]");
		emailService.sendMailToAdmins(message);
		mav.setViewName("redirect:/shop/contact");
		redirectAttributes.addFlashAttribute("success", "true");
		return mav;
	}
}