package com.yubi.application.supplier;

import java.util.ArrayList;
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

import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;

@Controller
@RequestMapping(value = "/admin/supplier")
public class SupplierController {
	
	private final SupplierAccess supplierAccess;
	
	private final SupplierService supplierService;
	
	@Inject
	public SupplierController(SupplierAccess supplierAccess, SupplierService supplierService) {
		this.supplierAccess = supplierAccess;
		this.supplierService = supplierService;
	}

	@RequestMapping("/add")
	public Model add() {
		Model model = new Model("supplier");
		model.addObject("screenMode", ScreenMode.CREATE);
		model.addObject(new Supplier());
		return model;
	}

	@RequestMapping(value = "/view/{id}")
	public Model viewSupplier(@PathVariable(value = "id") final long id) {
		Model model = new Model("supplier");
		model.addObject("screenMode", ScreenMode.ENQUIRE);

		// Load the supplier for enquiry purposes using the id
		model.addObject("supplier", supplierAccess.load(id));
		return model;
	}

	@RequestMapping(value = "/edit/{id}")
	public Model editSupplier(@PathVariable(value = "id") final long id) {
		Model model = new Model("supplier");
		model.addObject("screenMode", ScreenMode.UPDATE);

		// Load the supplier for editing using the id
		model.addObject("supplier", supplierAccess.load(id));
		return model;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Model save(@ModelAttribute("supplier") @Valid Supplier supplier, BindingResult result) {
		
		// If there any errors return the model to the view
		if (result.hasErrors()) {
			return new Model(ScreenMode.UPDATE, "supplier", "supplier", supplier);
		}
		
		// Save the new or updated supplier
		supplierAccess.save(supplier);
		
		// On success show the supplier in enquiry mode
		return new Model("redirect:/admin/supplier/view/" + supplier.getId());
	}
	
	
	@RequestMapping("/search")
	public @ResponseBody List<SupplierDTO> listSuppliers(String query) {
		String[] queryStrings = {"*", query, "*"};
		List<Supplier> suppliers = supplierService.search(StringUtils.join(queryStrings));
		List<SupplierDTO> results = new ArrayList<SupplierDTO>();
		
		for (Supplier supplier : suppliers) {
			results.add(new SupplierDTO(supplier.getId(), supplier.getName()));
		}
		
		return results;
	}
}
