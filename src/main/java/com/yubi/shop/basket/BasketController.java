package com.yubi.shop.basket;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yubi.application.category.CategoryService;
import com.yubi.application.product.Product;
import com.yubi.application.product.ProductAccess;
import com.yubi.application.product.ProductService;

@Controller
@RequestMapping("/shop/basket")
public class BasketController {
	
	private final ProductService productService;
	
	private final CategoryService categoryService;
	
	private final BasketService basketService;
	
	private final ProductAccess productAccess;
	
	
	@Inject
	public BasketController(
			ProductService productService, 
			CategoryService categoryService,
			BasketService basketService,
			ProductAccess productAccess) {
		super();
		this.productService = productService;
		this.categoryService = categoryService;
		this.basketService = basketService;
		this.productAccess = productAccess;
	}

	@RequestMapping("/add/{code}")
	public @ResponseBody boolean addToBasket(
			@PathVariable("code") String code, 
			HttpSession session) {
		
		// Check the product is still available - someone else might have 
		// taken it in the meantime. Ensure that there are still enough of them.
		if (!productService.checkStockExists(code, 1)) {
			return false;
		} else {
			
			// Add the item to the basket. If we already have one in there just increase the number
			Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
			
			// Check for the presence of this item in the basket
			BasketItem item = basket.getItems().get(code);
			if (item == null) {
				Product toAdd = productAccess.load(code);
				
				BasketItem newItem = new BasketItem();
				newItem.setItemCost(toAdd.getUnitPrice());
				newItem.setNumber(1);
				newItem.setProductCode(code);
				newItem.setProductDescription(toAdd.getTitle());
				
				basket.getItems().put(code, newItem);
			} else {
				item.setNumber(item.getNumber() + 1);
			}
			
			// Update the stock level. This will
			// be undone if the user doesn't actually buy it at the
			// point that their session expires.
			productService.reduceStockLevel(code, 1);
			
			return true;
		}
	}
	
	
	@RequestMapping("/remove/{code}/{number}")
	public String removeFromBasket(
			@PathVariable("code") String code,
			@PathVariable("number") int number,
			HttpSession session) {
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		
		BasketItem item = basket.getItems().get(code);
		
		// If we're removing all the items then remove the basket entry
		if (item.getNumber() == number) {
			basket.getItems().remove(code);
		} else {
			item.setNumber(item.getNumber() - number);
		}
		
		// Also put the stock back
		productService.increaseStockLevel(code, number);
		return "shop/shop";
	}
	
	
	@RequestMapping("/show")
	public ModelAndView showBasket() {
		ModelAndView model = new ModelAndView("shop/basket");
		model.addObject("menu", categoryService.buildProductMenu());
		return model;
	}
	
	
	@RequestMapping("/contents")
	public ModelAndView showBasketContents(HttpSession session) {
		ModelAndView model = new ModelAndView("shop/basketcontent");
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		model.addObject("basket", basket);
		model.addObject("total", basketService.getBasketTotal(basket));
		return model;
	}
	
	@RequestMapping("/total")
	public @ResponseBody BigDecimal getBasketTotal(HttpSession session) {
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		return basket.getTotal();
	}
}
