package com.yubi.application.order;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/order")
public class OrderAdminController {
	
	private final ProductOrderService productOrderService;
	
	@Inject
	public OrderAdminController(ProductOrderService productOrderService) {
		super();
		this.productOrderService = productOrderService;
	}


	@RequestMapping("")
	public ModelAndView orderAdmin() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/order/summary");
		mav.addObject("orders", productOrderService.recentOrders());
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
		return mav;
	}

}
