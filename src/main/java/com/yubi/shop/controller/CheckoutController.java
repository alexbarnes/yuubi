package com.yubi.shop.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.yubi.application.category.CategoryService;
import com.yubi.application.order.ProductOrderService;
import com.yubi.core.country.CountryAccess;
import com.yubi.core.statistics.EventGateway;
import com.yubi.core.statistics.ShopEvent;
import com.yubi.core.statistics.ShopEventType;
import com.yubi.shop.basket.Basket;
import com.yubi.shop.basket.BasketCreationListener;
import com.yubi.shop.basket.BasketService;
import com.yubi.shop.checkout.DiscountApplicationResult;
import com.yubi.shop.delivery.DeliveryMethodAccess;
import com.yubi.shop.discount.Discount;
import com.yubi.shop.discount.DiscountAccess;
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
	
	private final ProductOrderService productOrderService;
	
	private final EventGateway eventGateway;
	
	private final CategoryService categoryService;
	
	private final DiscountAccess discountAccess;
	@Inject
	public CheckoutController(
			DeliveryMethodAccess deliveryMethodAccess,
			CountryAccess countryAccess,
			PaypalService paypalService,
			Environment env,
			BasketService basketService,
			ProductOrderService productOrderService,
			EventGateway eventGateway,
			CategoryService categoryService,
			DiscountAccess discountAccess) {
		super();
		this.deliveryMethodAccess = deliveryMethodAccess;
		this.countryAccess = countryAccess;
		this.paypalService = paypalService;
		this.env = env;
		this.basketService = basketService;
		this.productOrderService = productOrderService;
		this.eventGateway = eventGateway;
		this.categoryService = categoryService;
		this.discountAccess = discountAccess;
	}

	/**
	 * Show the checkout page. This should allow a user to select a delivery method
	 *  as well as enter any discount codes.
	 */
	@RequestMapping
	public ModelAndView checkout(HttpSession session) {
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		
		if (basket.getItems().size() == 0) {
			return new ModelAndView("redirect:/");
		}
		
		ModelAndView mav = new ModelAndView("shop/checkout", "countries", countryAccess.listAll());
		mav.addObject("total", basketService.getBasketTotal(basket));
		mav.addObject("menu", categoryService.buildProductMenu());
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
	@RequestMapping("/applydiscount/{code}")
	public @ResponseBody DiscountApplicationResult applyDiscount(@PathVariable("code") String code, HttpSession session) {
		
		Discount discount = discountAccess.get(code);
		DiscountApplicationResult result = new DiscountApplicationResult();
		
		if (discount == null) {
			result.valid = false;
			return result;
		}
		
		result.valid = true;
		result.discount = discount;
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		basket.setDiscount(discount);
		
		if (basket.getDeliveryMethod() != null) {
			result.deliveryCost = basket.getDeliveryMethod().getCost().setScale(2, RoundingMode.HALF_UP).toString();
		} else {
			result.deliveryCost = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP).toString();
		}
		
		result.newTotal = basketService.getBasketTotal(basket).setScale(2, RoundingMode.HALF_UP).toString();
		return result;
	}
	
	@RequestMapping("/removediscount")
	public @ResponseBody DiscountApplicationResult removeDiscount(HttpSession session) {
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		basket.setDiscount(null);
		
		DiscountApplicationResult result = new DiscountApplicationResult();
		result.newTotal = basketService.getBasketTotal(basket).setScale(2, RoundingMode.HALF_UP).toString();
		
		if (basket.getDeliveryMethod() != null) {
			result.deliveryCost = basket.getDeliveryMethod().getCost().setScale(2, RoundingMode.HALF_UP).toString();
		} else {
			result.deliveryCost = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP).toString();
		}
		
		return result;
	}
	
	/**
	 * This method will send the user off to Paypal to setup the transaction.
	 * 
	 */
	@RequestMapping("/setuppayment")
	public ModelAndView expressCheckout(HttpSession session, RedirectAttributes redirect) {
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		ModelAndView result = new ModelAndView();
		
		// First check for a valid delivery method
		if (basket.getDeliveryMethod() == null) {
			result.setViewName("redirect:/shop/checkout");
			redirect.addFlashAttribute("deliveryMethod", false);
			return result;
		}
		
		String token;
		try {
			token = paypalService.setupTransaction(basket, session.getId());
			session.setAttribute("paypal-token", token);
		} catch (RuntimeException e) {
			logger.error("Error setting up paypal transaction.", e);
			result.setViewName("redirect:/shop/checkout");
			redirect.addFlashAttribute("error", true);
			
			// Need to sent an e-mail to the admin if this happens. We have
			// a problem.
			return result;
	}
		// If all went to plan, send the user off to Paypal to authenticate
		result.setViewName(String.format("redirect:%s?cmd=_express-checkout&token=%s", env.getProperty(PaypalConstants.PAYPAL_PAYMENT_URL), token));
		return result;
	}
	
	
	/**
	 * This page should show a summary of the order once it's been setup in Paypal. There should
	 * be a complete order button and a return to basket action. Checkout should complete the order
	 * with Paypal.
	 */
	@RequestMapping("/confirm")
	public ModelAndView confirmOrder(HttpSession session, String token, @RequestParam("PayerID") String payerId) {
		ModelAndView result = new ModelAndView();
		String sessionToken = (String) session.getAttribute("paypal-token");
		
		// If we came here without going to Paypal there will be no token. We need to go back to
		// checkout so that the user can complete properly via paypal.
		if (sessionToken == null || !sessionToken.equals(token)) {
			result.setViewName("redirect:/shop/checkout");
			return result;
		}
		
		// Record the payer id for later.
		session.setAttribute("payer-id", payerId);
		
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		
		result.addObject("basket", basket);
		result.addObject("total", basketService.getBasketTotal(basket));
		
		// If we get to here we have setup the transaction. We can ask Paypal about it now.
		result.addObject("transaction", paypalService.loadTransactionDetail(sessionToken, session.getId()));
		result.setViewName("shop/confirmation");
		
		return result;
	}
	
	
	/**
	 * This should call Paypal to complete the payment, it should also update any
	 * gift vouchers and write an order using the details obtained from Paypal.
	 * 
	 */
	@RequestMapping("/complete")
	public String completeOrder(HttpSession session, RedirectAttributes redirect) {
		
		Basket basket = (Basket) session.getAttribute(BasketCreationListener.BASKET_KEY);
		String token = (String) session.getAttribute("paypal-token");
		String payerId = (String) session.getAttribute("payer-id");
		
		// Complete the payment with Paypal
		String transactionId;
		try {
			transactionId = 
				paypalService.completeTransaction(token, payerId, session.getId(), basket);
		} catch (RuntimeException e) {
			redirect.addFlashAttribute("error", true);
			return "redirect:/shop/checkout";
		}
		
		// Write the details of the order here. Link it to the paypal order
		long orderId = productOrderService.createNewOrder(basket, transactionId);
		
		// Record the event
		ShopEvent event = new ShopEvent();
		event.setDate(new Date());
		event.setEntityKey(String.valueOf(orderId));
		event.setSessionId(session.getId());
		event.setType(ShopEventType.ORDER_COMPLETE);
		eventGateway.recordShopEvent(event);
		
		// Clear the basket once the transaction is complete. Don't invalidate the session.
		basket.reset();
		
		// Send the user off to the thank you page
		return "shop/thankyou";
	}
}