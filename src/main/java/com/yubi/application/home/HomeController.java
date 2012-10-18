package com.yubi.application.home;

import java.util.Enumeration;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yubi.application.component.ComponentService;
import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;
import com.yubi.application.order.OrderService;

@Controller
public class HomeController {

	private ComponentService componentService;

	private OrderService orderService;

	@Inject
	public HomeController(ComponentService componentService,
			OrderService orderService) {
		super();
		this.componentService = componentService;
		this.orderService = orderService;
	}

	@RequestMapping("/home")
	public ModelAndView home() {
		Model model = new Model(ScreenMode.ENQUIRE, "home", "model",
				buildHomeModel());
		return model;
	}

	@RequestMapping("/")
	public ModelAndView home2() {
		Model model = new Model(ScreenMode.ENQUIRE, "home", "model",
				buildHomeModel());
		return model;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/error")
	public String error(HttpSession session) {
		Enumeration attributeNames = session.getAttributeNames();
		
		while(attributeNames.hasMoreElements()) {
			String name = (String) attributeNames.nextElement();
			session.setAttribute(name, null);
		}
		return "error";
	}

	private HomeModel buildHomeModel() {
		HomeModel model = new HomeModel();
		model.getStockAlerts().addAll(componentService.stockAlerts());
		model.getUpcomingDeliveries().addAll(orderService.upcomingDeliveries());
		return model;
	}

}
