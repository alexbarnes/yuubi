package com.yubi.application.shop;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yubi.application.category.CategoryService;
import com.yubi.application.core.CountryAccess;
import com.yubi.application.core.login.BasketCreationListener;
import com.yubi.application.product.ProductService;

@Controller
@RequestMapping("/shop/basket")
public class BasketController {
	
	private final ProductService productService;
	
	private final CategoryService categoryService;
	
	private final CountryAccess countryAccess;
	
	private final DeliveryMethodAccess deliveryMethodAccess;
	
	@Inject
	public BasketController(
			ProductService productService, 
			CategoryService categoryService,
			CountryAccess countryAccess,
			DeliveryMethodAccess deliveryMethodAccess) {
		super();
		this.productService = productService;
		this.categoryService = categoryService;
		this.countryAccess = countryAccess;
		this.deliveryMethodAccess = deliveryMethodAccess;
	}

	@RequestMapping("/add/{code}")
	public String addToBasket(
			@PathVariable("code") String code, 
			int quantity,
			HttpSession session) {
		
		// Check the product is still available - someone else might have 
		// taken it in the meantime. Ensure that there are still enough of them.
		if (!productService.checkStockExists(code, quantity)) {
			
			// TODO If we're out of stock at this stage, give the user
			// the opportunity fill out a pop up with a question. It might be
			// possible to make one etc.
		} else {
			
			// Add the item to the basket
			Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
			basket.getItems().put(code, new BasketItem(code,quantity));
			
			// Update the stock level. This will
			// be undone if the user doesn't actually buy it at the
			// point that their session expires.
			productService.reduceStockLevel(code, quantity);
		}
		return "shop/shop";
	}
	
	
	@RequestMapping("/remove/{code}")
	public String removeFromBasket(@PathVariable("code") String code,
			HttpSession session) {
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		int number = basket.getItems().get(code).getNumber();
		basket.getItems().remove(code);
		
		// Also put the stock back
		productService.increaseStockLevel(code, number);
		return "shop/shop";
	}
	
	
	@RequestMapping("/show")
	public ModelAndView showBasket(HttpSession session) {
		ModelAndView model = new ModelAndView("shop/basket");
		model.addObject("menu", categoryService.buildProductMenu());
		model.addObject("basket", session.getAttribute(BasketCreationListener.BASKET_KEY));
		model.addObject("countries", countryAccess.listAll());
		return model;
	}
	
	@RequestMapping("/listdeliverymethods/{code}")
	public @ResponseBody List<DeliveryMethod> getDeliveryMethodsForCountry(@PathVariable("code" )String code) {
		return deliveryMethodAccess.loadForCountry(code);
	}
}
