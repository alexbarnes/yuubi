package com.yubi.application.order;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;

@Controller
@RequestMapping("/order")
public class OrderController {

	@RequestMapping("/add")
	public Model add() {
		return new Model(ScreenMode.CREATE, "order", "order", new ComponentOrder());
	}

	
	@RequestMapping("/view/{id}")
	public Model view(@PathVariable("id") long id) {
		return new Model(ScreenMode.ENQUIRE, "order", "order", new ComponentOrder());
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Model save(@ModelAttribute("order") @Valid ComponentOrder order, BindingResult result) {
		
		if (result.hasErrors()) {
			return new Model(ScreenMode.UPDATE, "order", "order", order);
		}
		return new Model("redirect:/view/" + order.getId());
	}
	
	
	@RequestMapping("/delivery/{id}")
	public Model takeDelivery(@PathVariable("id") long orderId) {
		return new Model(ScreenMode.UPDATE, "delivery", "order", new ComponentOrder());
	}
	
	
	@RequestMapping("/delivered")
	public Model itemDelivered(@ModelAttribute("order") ComponentOrder order) {
		return new Model(ScreenMode.UPDATE, "delivery", "order", new ComponentOrder());
	}
}
