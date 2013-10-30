package com.yubi.shop.notification;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shop/notification")
public class NotificationController {
	
	private final OutOfStockService outOfStockService;
	
	@Inject
	public NotificationController(OutOfStockService outOfStockService) {
		super();
		this.outOfStockService = outOfStockService;
	}


	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addNotification(String productCode, String email) {
		outOfStockService.saveNewNotification(productCode, email);
		return "redirect:/shop/product/view/" + productCode + "/";
	}
}