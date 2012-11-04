package com.yubi.shop.notification;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shop/notification")
public class NotificationController {
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addNotification(String productCode, String email) {
		
	}

}
