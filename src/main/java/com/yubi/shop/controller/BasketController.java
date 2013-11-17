package com.yubi.shop.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yubi.application.category.CategoryService;
import com.yubi.application.product.Product;
import com.yubi.application.product.ProductAccess;
import com.yubi.shop.basket.Basket;
import com.yubi.shop.basket.Basket.BasketKey;
import com.yubi.shop.basket.BasketItem;
import com.yubi.shop.basket.BasketService;
import com.yubi.shop.delivery.DeliveryMethod;
import com.yubi.shop.delivery.DeliveryMethodAccess;
import com.yubi.shop.delivery.DeliveryMethodUpdateResult;

/**
 * Methods to handle changes to the contents of a users basket.
 * 
 * @author Alex Barnes
 *
 */
@Controller
@RequestMapping("/shop/basket")
public class BasketController {
	
	private final CategoryService categoryService;
	
	private final BasketService basketService;
	
	private final ProductAccess productAccess;
	
	private final DeliveryMethodAccess deliveryMethodAccess;
	
	
	@Inject
	public BasketController(
			CategoryService categoryService,
			BasketService basketService,
			ProductAccess productAccess,
			DeliveryMethodAccess deliveryMethodAccess) {
		super();
		this.categoryService = categoryService;
		this.basketService = basketService;
		this.productAccess = productAccess;
		this.deliveryMethodAccess = deliveryMethodAccess;
	}

	@RequestMapping("/add/{code}")
	public @ResponseBody boolean addToBasket(@PathVariable("code") String code, HttpSession session) {
		
		Basket basket = Basket.getBasketFromSession(session);
		
		// -- Check for the presence of this item in the basket
		BasketKey key = new BasketKey(code);
		BasketItem item = basket.getItems().get(key);
		
		// -- Add the item to the basket. If we already have one in there just increase the number.
		if (item == null) {
			Product toAdd = productAccess.load(code);
			basket.getItems().put(key, new BasketItem(toAdd, 1));
		} else {
			item.setNumber(item.getNumber() + 1);
		}
		return true;
	}
	
	
	@RequestMapping("/remove/{code}/{number}")
	public String removeFromBasket(
			@PathVariable("code") String code,
			@PathVariable("number") int number,
			HttpSession session) {
		Basket basket = Basket.getBasketFromSession(session);
		BasketKey key = new BasketKey(code);
		
		BasketItem item = basket.getItems().get(key);
		
		// If we're removing all the items then remove the basket entry
		if (item.getNumber() == number) {
			basket.getItems().remove(key);
		} else {
			item.setNumber(item.getNumber() - number);
		}
		return "shop/shop";
	}
	
	
	@RequestMapping("/show")
	public ModelAndView showBasket(HttpSession session, HttpServletRequest request) {
		Basket basket = Basket.getBasketFromSession(session);
		// -- Clear any out of stock errors in case we're returning from the checkout
		for (Entry<BasketKey, BasketItem> item : basket.getItems().entrySet()) {
			item.getValue().setNotEnoughStock(false);
		}
				
		session.setAttribute("current_url", request.getRequestURI());
		ModelAndView model = new ModelAndView("shop/basket");
		model.addObject("menu", categoryService.buildProductMenu());
		return model;
	}
	
	
	@RequestMapping("/contents")
	public ModelAndView showBasketContents(HttpSession session) {
		ModelAndView model = new ModelAndView("shop/basketcontent");
		Basket basket = Basket.getBasketFromSession(session);
		model.addObject("basket", basket);
		return model;
	}
	
	
	@RequestMapping("/total")
	public @ResponseBody BigDecimal getBasketTotal(HttpSession session) {
		Basket basket = Basket.getBasketFromSession(session);
		return basket.getTotal((Currency) session.getAttribute("currency")).setScale(0);
	}
	
	
	@RequestMapping("/setdeliverymethod")
	public @ResponseBody DeliveryMethodUpdateResult setDeliveryMethod(HttpSession session, Long id) {
		Basket basket = Basket.getBasketFromSession(session);
		DeliveryMethodUpdateResult result = new DeliveryMethodUpdateResult();
		
		// Set the delivery method
		if (id != null) {
			DeliveryMethod method = deliveryMethodAccess.get(id);
			basket.setDeliveryMethod(method);
			result.deliveryCost = method.getCostInCurrency((Currency) session.getAttribute("currency")).setScale(2, RoundingMode.HALF_UP).toString();
		} else {
			basket.setDeliveryMethod(null);
			result.deliveryCost = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP).toString();
		}
		result.newTotal = basketService.getBasketTotal(basket, (Currency) session.getAttribute("currency")).setScale(2, RoundingMode.HALF_UP).toString();
		return result;
	}
}
