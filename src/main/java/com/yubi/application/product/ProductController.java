package com.yubi.application.product;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yubi.application.component.ComponentAccess;
import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	private static final String CURRENT_PRODUCT = "current_product";

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
	public Model createProduct(HttpSession session) {
		Product product = new Product();
		session.setAttribute(CURRENT_PRODUCT, product);
		return new Model(ScreenMode.CREATE, "product", "product", product);
	}

	@RequestMapping("/edit/{code}")
	public Model editProduct(@PathVariable(value = "code") String code, HttpSession session) {
		Product product = productAccess.load(code);
		
		// Set up the component ids so that the view knows what to show
		for (ProductComponent component : product.getComponents()) {
			component.setComponentId(component.getId().getComponent().getId());
		}
		
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

	@RequestMapping("/component/add")
	public Model addComponent(@ModelAttribute("product") Product product,
			String screenMode) {

		// Set objects on the product to reflect the ids set from the
		// UI.
		for (ProductComponent component : product.getComponents()) {
			component.getId().setComponent(
					componentAccess.load(component.getComponentId()));
		}

		product.getComponents().add(new ProductComponent());
		return new Model(ScreenMode.valueOf(screenMode), "productform",
				"product", product);
	}

	@RequestMapping("/component/remove/{index}")
	public Model removeComponent(@ModelAttribute("product") Product product,
			@PathVariable(value = "index") int index, String screenMode) {

		product.getComponents().remove(index);

		// For the remaining components set the actual objects
		for (ProductComponent component : product.getComponents()) {
			component.getId().setComponent(
					componentAccess.load(component.getComponentId()));
		}

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
		return new Model("redirect:/product/view/" + sessionProduct.getCode());
	}
	
	@RequestMapping("/cancel")
	public String cancel(HttpSession session, String screenMode, String code) {
		session.setAttribute(CURRENT_PRODUCT, null);
		
		if (ScreenMode.valueOf(screenMode) == ScreenMode.CREATE) {
			return "redirect:/home";
		} else {
			return "redirect:/product/view/" + code;
		}
	}
	
	
	private void mapViewProductToSession(Product sessionProduct, Product viewProduct) {
		sessionProduct.setCurrency(viewProduct.getCurrency());
		sessionProduct.setDescription(viewProduct.getDescription());
		sessionProduct.setUnitPrice(viewProduct.getUnitPrice());
		
		// Handle the components by detatching all and then adding back
		sessionProduct.getComponents().clear();
		
		for (ProductComponent component : viewProduct.getComponents()) {
			ProductComponent addedComponent = sessionProduct.addComponent();
			addedComponent.getId().setComponent(
					componentAccess.load(component.getComponentId()));
			addedComponent.setNumber(component.getNumber());
		}
	}
	
}
