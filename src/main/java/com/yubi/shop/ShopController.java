package com.yubi.shop;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yubi.application.category.CategoryService;
import com.yubi.shop.basket.Basket;
import com.yubi.shop.basket.BasketCreationListener;
import com.yubi.shop.paypal.PaypalConstants;
import com.yubi.shop.paypal.PaypalService;

@Controller
@RequestMapping("/shop")
public class ShopController {
	
	private final CategoryService categoryService;
	
	private final PaypalService paypalService;
	
	private final Environment env;
	
	@Inject
	public ShopController(
			CategoryService categoryService, 
			PaypalService paypalService, 
			Environment env) {
		super();
		this.categoryService = categoryService;
		this.paypalService = paypalService;
		this.env = env;
	}

	
	@RequestMapping
	public ModelAndView showShopHome() {
		ModelAndView model = new ModelAndView("shop/shop");
		model.addObject("menu", categoryService.buildProductMenu());
		return model;
	}
	
	
	@RequestMapping("/checkout")
	public String expressCheckout(HttpSession session) {
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		
		// Setup the delivery method on the basket prior to calling Paypal
		//basket.setDeliveryMethod(deliveryMethodAccess.get(1));
		
		String token;
		//try {
			token = paypalService.setupTransaction(basket);
			session.setAttribute("paypal-token", token);
		//} catch (RuntimeException e) {
			// Add an error and return to the basket
			//return "shop/basket";
	//}
		
		return String.format("redirect:%s?cmd=_express-checkout&useraction=commit&token=%s", env.getProperty(PaypalConstants.PAYPAL_PAYMENT_URL), token);
	}
	
	@RequestMapping("/product/view/{code}")
	public String viewProduct(@PathVariable("code") String code) {
		return "shop/productdetail";
	}
	
	
	@RequestMapping("/confirmation")
	public ModelAndView confirmPurchase(HttpSession session, String token) {
		
		// First check the returned token matches that in the session
		String sessionToken = (String) session.getAttribute("paypal-token");
		
		//TODO How should be deal with this?
		if (!sessionToken.equals(token))
			throw new IllegalStateException();
		
		ModelAndView model = new ModelAndView("confirmation");
		
		// Call Paypal to get the shipping address and order details
		paypalService.completeTransaction(sessionToken);
		
		// Add everything to the model by loading the order from the database
		
		
		// Reset the basket once we're done here
		
		
		return model;
	}
}

