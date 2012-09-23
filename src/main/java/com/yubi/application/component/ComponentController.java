package com.yubi.application.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yubi.application.component.StockHistory.StockChangeType;
import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;
import com.yubi.application.supplier.SupplierAccess;


@Controller
@RequestMapping("/component")
public class ComponentController {

	private final ComponentAccess componentAccess;
	
	private final SupplierAccess supplierAccess;
	
	private final ComponentService componentService;

	@Inject
	public ComponentController(ComponentAccess componentAccess, SupplierAccess supplierAccess, ComponentService componentService) {
		super();
		this.componentAccess = componentAccess;
		this.supplierAccess = supplierAccess;
		this.componentService = componentService;
	}
	
	@RequestMapping(value = "/add")
	public Model add() {
		Model model = new Model(ScreenMode.CREATE, "component", "component",
				new Component());
		return model;
	}
	
	@RequestMapping("/edit/{id}")
	public Model edit(@PathVariable("id") long id) {
		Model model = new Model(ScreenMode.UPDATE, "component", "component", componentAccess.load(id));
		return model;
	}

	@RequestMapping("/view/{id}")
	public Model view(@PathVariable(value = "id") final long id) {
		Model model =  new Model(ScreenMode.ENQUIRE, "component", "component", componentAccess.load(id));
		
		StockUpdate update = new StockUpdate();
		update.setComponentId(id);
		
		model.addObject("stockupdate", update);
		model.addObject("showpopup", false);
		return model;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Model save(@ModelAttribute("component") @Valid Component component, BindingResult result) {
		
		if (result.hasErrors()) {
			return new Model(ScreenMode.UPDATE, "component", "component", component);
		}
		
		// Set the supplier on the component if we've set one
		if (component.getSupplierId() > 0) 
			component.setSupplier(supplierAccess.load(component.getSupplierId()));
		
		// Save the component
		componentAccess.save(component);
		
		return new Model("redirect:/component/view/" + component.getId());
	}
	
	
	@RequestMapping(value = "/stockupdate", method = RequestMethod.POST)
	public Model updateStock(@ModelAttribute("stockupdate") @Valid StockUpdate update, BindingResult result) {
		
		Component component = componentService.loadComponentWithHistory(update.getComponentId());
		
		if (result.hasErrors()) {
			Model model =  new Model(ScreenMode.ENQUIRE, "component", "component", component);
			model.addObject("stockupdate", update);
			model.addObject("showpopup", true);
			return model;
		}
		
		// If it passes validation add a history
		StockHistory history = component.addHistory();
		history.setType(StockChangeType.MANUAL_CORRECTION);
		history.setDate(new Date());
		history.setNarrative(update.getNarrative());
		history.setOldStockLevel(component.getStock());
		history.setNewStockLevel(update.getNewStockNumber());
		
		// And update the current stock count on the component
		component.setStock(update.getNewStockNumber());
		
		componentAccess.save(component);
		
		// Redirect to the normal view
		return new Model("redirect:/component/view/" + component.getId());
	}
	
	@RequestMapping("/search")
	public @ResponseBody List<ComponentDTO> search(String query) {
		String[] queryStrings = {"*", query, "*"};
		List<Component> components = componentService.search(StringUtils.join(queryStrings));
		List<ComponentDTO> results = new ArrayList<ComponentDTO>();
		
		for (Component component : components) {
			results.add(new ComponentDTO(component.getId(), component.getDescription()));
		}
		
		return results;
	}
}