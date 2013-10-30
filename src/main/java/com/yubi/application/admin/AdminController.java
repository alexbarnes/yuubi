package com.yubi.application.admin;

import javax.inject.Inject;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yubi.application.product.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final ProductService productService;
	private final Environment environment;
	
	@Inject
	public AdminController(ProductService productService, Environment environment) {
		super();
		this.productService = productService;
		this.environment = environment;
	}

	@RequestMapping("/stock/update/{token}/{code}/{number}")
	public String updateStockLevel(@PathVariable("token") String token, @PathVariable("code") String code, @PathVariable("number") int number) {
		if (this.environment.getProperty("application.admin.key").equals(token)) {
			productService.setStockLevel(code, number);
		}
		return "redirect:/shop";
	}

}
