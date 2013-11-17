package com.yubi.shop.controller;

import java.util.Currency;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.yubi.application.category.CategoryService;
import com.yubi.application.product.Product;
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
	
	
	@RequestMapping("/heartbeat")
	public @ResponseBody boolean heartbeat() {
		return true;
	}
	
	
	@RequestMapping("/currency/change")
	public RedirectView changeCurrency(String currencyCode, String url, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		session.setAttribute("currency", Currency.getInstance(currencyCode));
		RedirectView view = new RedirectView(url);
		view.setExposeModelAttributes(false);
		return view;
	}
	
	
	@RequestMapping("/showerror")
	public RedirectView errorHandle() {
		RedirectView view = new RedirectView("/shop/error");
		view.setExposeModelAttributes(false);
		return view;
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
		mav.addObject("products", productAccess.listInUseForCategory(id));
		mav.addObject("category", categoryService.load(id));
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
	public ModelAndView showProduct(@PathVariable("code") String code, HttpSession session, HttpServletRequest request) {
		Product product = productAccess.load(code);
		
		// -- If we try to access a not in use product redirect to the category.
		if(!product.isOnDisplay()) {
			RedirectView redirect = new RedirectView(String.format("/shop/category/view/%s/%s",product.getCategory().getId(), product.getCategory().getUrlName()));
			redirect.setExposeModelAttributes(false);
			return new ModelAndView(redirect);
		}
		ModelAndView mav = new ModelAndView("shop/productdetail");
		mav.addObject("menu", categoryService.buildProductMenu());
		mav.addObject("product", product);
		session.setAttribute("current_url", request.getRequestURI());
		return mav;
	}
	
	
	@RequestMapping(value = "/product/search", method = RequestMethod.POST)
	public RedirectView searchForProducts(String query, RedirectAttributes redirectAttributes, HttpSession session) {
		RedirectView view = new RedirectView("/shop/product/searchresults");
		view.setExposeModelAttributes(false);
		redirectAttributes.addFlashAttribute("active", "Search: " + query);
		redirectAttributes.addFlashAttribute("products", productService.search(query));
		return view;
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
	public ModelAndView sendContactForm(@ModelAttribute("message") @Valid OutboundMailMessage message,  BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("shop/contact");
			mav.addObject("message", message);
			mav.addObject("menu", categoryService.buildProductMenu());
			return mav;
		}
		
		// If we get past the required validation send the message and redirect.
		ModelAndView mav = new ModelAndView();
		message.setSubject("Contact Form Submission [" + message.getFromName() + "] [" + message.getFrom() + "].");
		emailService.sendMailToAdmins(message);
		
		
		RedirectView view = new RedirectView("/shop/contact");
		view.setExposeModelAttributes(false);
		mav.setView(view);
		redirectAttributes.addFlashAttribute("success", "true");
		return mav;
	}
}