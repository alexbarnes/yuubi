package com.yubi.shop.checkout;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.yubi.core.country.CountryAccess;
import com.yubi.shop.basket.Basket;
import com.yubi.shop.basket.BasketCreationListener;
import com.yubi.shop.basket.BasketService;
import com.yubi.shop.delivery.DeliveryMethodAccess;
import com.yubi.shop.paypal.PaypalConstants;
import com.yubi.shop.paypal.PaypalService;

@Controller
@RequestMapping("/shop/checkout")
public class CheckoutController {
	
	private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);
	
	private final DeliveryMethodAccess deliveryMethodAccess;
	
	private final CountryAccess countryAccess;
	
	private final PaypalService paypalService;
	
	private final Environment env;
	
	private final BasketService basketService;

	@Inject
	public CheckoutController(
			DeliveryMethodAccess deliveryMethodAccess,
			CountryAccess countryAccess,
			PaypalService paypalService,
			Environment env,
			BasketService basketService) {
		super();
		this.deliveryMethodAccess = deliveryMethodAccess;
		this.countryAccess = countryAccess;
		this.paypalService = paypalService;
		this.env = env;
		this.basketService = basketService;
	}

	/**
	 * Show the checkout page. This should allow a user to select a delivery method
	 *  as well as enter any discount codes.
	 */
	@RequestMapping("/")
	public ModelAndView checkout(HttpSession session) {
		ModelAndView mav = new ModelAndView("shop/checkout", "countries", countryAccess.listAll());
		
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		
		mav.addObject("total", basketService.getBasketTotal(basket));
		return mav;
	}
	
	
	/**
	 * This method should be called via Ajax to list the available delivery
	 * methods for a selected country.
	 */
	@RequestMapping("/listdeliverymethods/{code}")
	public @ResponseBody ModelAndView getDeliveryMethodsForCountry(@PathVariable("code" )String code) {
		return new ModelAndView(new MappingJacksonJsonView(), "deliverymethods", deliveryMethodAccess.loadForCountry(code));
	}
	
	/**
	 * This is used to apply a discount code to an order. The screen should be updated to reflect
	 * the discount.
	 * 
	 */
	public ModelAndView applyDiscount(String code) {
		return new ModelAndView();
	}
	
	/**
	 * This method will send the user off to Paypal to setup the transaction.
	 * 
	 */
	@RequestMapping("/setuppayment")
	public String expressCheckout(HttpSession session) {
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		
		String token;
		try {
			token = paypalService.setupTransaction(basket);
			session.setAttribute("paypal-token", token);
		} catch (RuntimeException e) {
			logger.error("Error setting up paypal transaction", e);
			return "shop/basket";
	}
		return String.format("redirect:%s?cmd=_express-checkout&useraction=commit&token=%s", env.getProperty(PaypalConstants.PAYPAL_PAYMENT_URL), token);
	}
	
	
	/**
	 * This page should show a summary of the order once its been setup in Paypal. There should
	 * be a checkout button and a return to basket action. Checkout should complete the order
	 * with Paypal.
	 */
	@RequestMapping("/confirm")
	public String confirmOrder() {
		return "confirmation";
	}
	
	
	/**
	 * This should call Paypal to complete the payment, it should also update any
	 * gift vouchers and write an order using the details obtained from Paypal.
	 * 
	 */
	@RequestMapping("/complete")
	public String completeOrder() {
		return "complete";
		
	}
	
	/**
	 * This is the final page, at this point the order is complete and the customer
	 * can then return to the shop.
	 * 
	 * @return
	 */
	@RequestMapping("/thankyou")
	public String thankYou() {
		return "thankyou";
		
	}
}