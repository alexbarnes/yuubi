package com.yubi.application.shop;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yubi.application.product.ProductService;

@Controller
@RequestMapping("/shop")
public class ShopController {
	
	private final ProductService productService;
	
	private CategoryService categoryService;
	
	@Inject
	public ShopController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@RequestMapping
	public String showShopHome() {
		return "shop/shop";
	}
	
	@RequestMapping("/menu")
	public Map<String, Long> showMenu() {
		
	}
	
	@RequestMapping("/basket/add/{code}")
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
			Basket basket = (Basket) session.getAttribute("basket");
			basket.getItems().put(code, new BasketItem(code,quantity));
			
			// Update the stock level. This will
			// be undone if the user doesn't actually buy it at the
			// point that their session expires.
			productService.reduceStockLevel(code, quantity);
		}
		
		return "shop/shop";
	}
	
	@RequestMapping("/basket/remove/{code}")
	public String removeFromBasket(@PathVariable("code") String code,
			HttpSession session) {
		Basket basket = (Basket) session.getAttribute("basket");
		int number = basket.getItems().get(code).getNumber();
		basket.getItems().remove(code);
		
		// Also put the stock back
		productService.increaseStockLevel(code, number);
		
		return "shop/shop";
	}
	
	@RequestMapping("/checkout")
	public ModelAndView checkout() {
		ModelAndView model = new ModelAndView();
		model.setViewName("shop/checkout");
		return model;
	}
	
	@RequestMapping("/basket/show")
	public void showBasket() {
		
	}
}
