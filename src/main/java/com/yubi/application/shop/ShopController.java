package com.yubi.application.shop;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yubi.application.category.CategoryService;
import com.yubi.application.core.login.BasketCreationListener;
import com.yubi.application.shop.paypal.PaypalConstants;
import com.yubi.application.shop.paypal.PaypalService;

@Controller
@RequestMapping("/shop")
public class ShopController {
	
	private final CategoryService categoryService;
	
	private final PaypalService paypalService;
	
	private final Environment env;
	
	private final DeliveryMethodAccess deliveryMethodAccess;
	
	@Inject
	public ShopController(
			CategoryService categoryService, 
			PaypalService paypalService, 
			Environment env,
			DeliveryMethodAccess deliveryMethodAccess) {
		super();
		this.categoryService = categoryService;
		this.paypalService = paypalService;
		this.env = env;
		this.deliveryMethodAccess = deliveryMethodAccess;
	}

	
	@RequestMapping
	public ModelAndView showShopHome() {
		ModelAndView model = new ModelAndView("shop/shop");
		model.addObject("menu", categoryService.buildProductMenu());
		return model;
	}
	
	
	@RequestMapping("/checkout")
	public String expressCheckout(HttpSession session, long deliveryMethod) {
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		
		// Setup the delivery method on the basket prior to calling Paypal
		basket.setDeliveryMethod(deliveryMethodAccess.get(deliveryMethod));
		
		String token;
		try {
			token = paypalService.setupTransaction(basket);
			session.setAttribute("paypal-token", token);
		} catch (RuntimeException e) {
			// Add an error and return to the basket
			return "shop/basket";
		}
		
		return String.format("redirect:%s?cmd=_express-checkout&useraction=commit&token=%s", env.getProperty(PaypalConstants.PAYPAL_PAYMENT_URL), token);
	}
	
	
	@RequestMapping("/confirmation")
	public ModelAndView confirmPurchase(HttpSession session) {
		ModelAndView model = new ModelAndView("confirmation");
		
		// Call Paypal to get the shipping address and order details
		
		// Add everything to the model
		
		// Reset the basket 
		
		return model;
	}
}

