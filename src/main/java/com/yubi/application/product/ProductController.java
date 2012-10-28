package com.yubi.application.product;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
	
	private static final String CURRENT_PRODUCT = "current_product";

	private final ProductAccess productAccess;

	@Inject
	public ProductController(ProductAccess productAccess) {
		super();
		this.productAccess = productAccess;
	}

	@RequestMapping("/add")
	public Model createProduct(HttpSession session) {
		Product product = new Product();
		session.setAttribute(CURRENT_PRODUCT, product);
		return new Model(ScreenMode.CREATE, "product", "product", product);
	}

	@RequestMapping("/edit/{code}")
	public Model editProduct(@PathVariable(value = "code") String code, HttpSession session) {
		Product product = productAccess.load(code);
		
		session.setAttribute(CURRENT_PRODUCT, product);

		return new Model(ScreenMode.UPDATE, "product", "product", product);
	}

	@RequestMapping("/view/{code}")
	public Model viewProduct(@PathVariable(value = "code") String code,
			HttpSession session) {
		Product product = productAccess.load(code);
		
		session.setAttribute(CURRENT_PRODUCT, product);
		
		return new Model(ScreenMode.ENQUIRE, "product", "product", product);
	}

	@RequestMapping("/components")
	public Model showList(String screenMode, HttpSession session) {
		Product product = (Product) session.getAttribute(CURRENT_PRODUCT);

		return new Model(ScreenMode.valueOf(screenMode), "productform",
				"product", product);
	}


	@RequestMapping("/save")
	public Model save(@ModelAttribute("product") @Valid Product product,
			BindingResult result, String screenMode, HttpSession session) {

		// If there are errors return the page
		if (result.hasErrors()) {
			return new Model(ScreenMode.valueOf(screenMode), "product",
					"product", product);
		}
		
		Product sessionProduct = (Product) session.getAttribute(CURRENT_PRODUCT);
		
		mapViewProductToSession(sessionProduct, product);
		
		// Map the code if we're creating. The version should not be messed with.
		if (ScreenMode.valueOf(screenMode) == ScreenMode.CREATE) {
			sessionProduct.setCode(product.getCode());
		}

		productAccess.save(sessionProduct);
		return new Model("redirect:/admin/product/view/" + sessionProduct.getCode());
	}
	
	@RequestMapping("/cancel")
	public String cancel(HttpSession session, String screenMode, String code) {
		session.setAttribute(CURRENT_PRODUCT, null);
		
		if (ScreenMode.valueOf(screenMode) == ScreenMode.CREATE) {
			return "redirect:/admin/home";
		} else {
			return "redirect:/admin/product/view/" + code;
		}
	}
	
	
	private void mapViewProductToSession(Product sessionProduct, Product viewProduct) {
		sessionProduct.setDescription(viewProduct.getDescription());
		sessionProduct.setUnitPrice(viewProduct.getUnitPrice());
	}
}
