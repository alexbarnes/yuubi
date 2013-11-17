package com.yubi.application.order;

import javax.inject.Inject;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yubi.shop.paypal.PaypalService;

@Controller
@RequestMapping("/admin/order")
public class OrderAdminController {
	
	private final ProductOrderService productOrderService;
	private final Environment environment;
	private final PaypalService paypalService;
	
	@Inject
	public OrderAdminController(
			ProductOrderService productOrderService,
			Environment environment,
			PaypalService paypalService) {
		super();
		this.productOrderService = productOrderService;
		this.environment = environment;
		this.paypalService = paypalService;
	}


	@RequestMapping("")
	public ModelAndView orderAdmin() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/order/summary");
		mav.addObject("orders", productOrderService.recentOrders());
		mav.addObject("paypalUrl", environment.getProperty("paypal.transaction.link.id"));
		return mav;
	}
	
	
	@RequestMapping("/view/{reference}")
	public ModelAndView viewOrder(@PathVariable("reference") String reference) {
		ModelAndView mav = new ModelAndView();
		
		ProductOrder order = productOrderService.load(reference);
		if (order == null) {
			mav.setViewName("/admin/order/notfound");
		}
		mav.setViewName("/admin/order/detail");
		mav.addObject("order", order);
		mav.addObject("paypalDetail", paypalService.loadTransaction(order.getPaypalTransactionId()));
		return mav;
	}
	
	
	@RequestMapping("/sent/{id}")
	public @ResponseBody boolean markSent(@PathVariable("id") long id) {
		return productOrderService.orderSent(id);
	}

}
