package com.yubi.application.product;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yubi.application.component.ComponentAccess;
import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;

/**
 * @author alex
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController {

	private final ProductAccess productAccess;

	private final ComponentAccess componentAccess;

	@Inject
	public ProductController(ProductAccess productAccess,
			ComponentAccess componentAccess) {
		super();
		this.productAccess = productAccess;
		this.componentAccess = componentAccess;
	}

	@RequestMapping("/add")
	public Model createProduct() {
		Product product = new Product();
		return new Model(ScreenMode.CREATE, "product", "product", product);
	}

	@RequestMapping("/edit/{code}")
	public Model editProduct(@PathVariable(value = "code") String code) {
		Product product = productAccess.load(code);

		if (product == null) {
			throw new IllegalArgumentException("Cannot load product");
		}

		return new Model(ScreenMode.UPDATE, "product", "product", product);
	}

	@RequestMapping("/view/{code}")
	public Model viewProduct(@PathVariable(value = "code") String code,
			HttpSession session) {
		Product product = productAccess.load(code);
		return new Model(ScreenMode.ENQUIRE, "product", "product", product);
	}

	
	@RequestMapping("/components")
	public Model showList(String screenMode, String code) {
		Product product = new Product();
		
		// Load the existing one for editing if we were passed a code
		if (StringUtils.isNotEmpty(code)) {
			product = productAccess.load(code);
		}
		
		return new Model(ScreenMode.valueOf(screenMode), "productform", "product", product);
	}

	@RequestMapping("/component/add")
	public Model addComponent(
			@ModelAttribute("product") Product product, String screenMode) {

		// Set objects on the product to reflect the ids set from the
		// UI.
		for (ProductComponent component : product.getComponents()) {
			component.getId().setComponent(
					componentAccess.load(component.getComponentId()));
		}

		product.getComponents().add(new ProductComponent());
		return new Model(ScreenMode.valueOf(screenMode), "productform", "product", product);
	}

	
	@RequestMapping("/component/remove/{index}")
	public Model removeComponent(
			@ModelAttribute("product") Product product,
			@PathVariable(value = "index") int index, String screenMode) {
		
		product.getComponents().remove(index);

		// For the remaining components set the actual objects
		for (ProductComponent component : product.getComponents()) {
			component.getId().setComponent(
					componentAccess.load(component.getComponentId()));
		}

		return new Model(ScreenMode.valueOf(screenMode), "productform", "product", product);
	}
}
