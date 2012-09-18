package com.yubi.application.search;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yubi.application.component.Component;
import com.yubi.application.component.ComponentService;
import com.yubi.application.core.Model;
import com.yubi.application.supplier.Supplier;
import com.yubi.application.supplier.SupplierService;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	private final SupplierService supplierService;
	
	private final SearchService searchService;
	
	private final ComponentService componentService;
	
	@Inject
	public SearchController(
			SupplierService supplierService, 
			SearchService searchService,
			ComponentService componentService) {
		this.supplierService = supplierService;
		this.searchService = searchService;
		this.componentService = componentService;
	}
	
	@RequestMapping(value = "/quicksearch", method = RequestMethod.POST)
	public String quickSearch(final SearchParameter parameter,
			RedirectAttributes redirectAttrs) {

		redirectAttrs.addFlashAttribute("results", searchService.search(parameter.getSearchString()));

		return "redirect:/search/results";
	}

	@RequestMapping(value = "/suppliersearch", method = RequestMethod.POST)
	public String supplierSearch(final SearchParameter parameter,
			RedirectAttributes redirectAttrs) {
		List<SearchResult> results = new ArrayList<SearchResult>();
		List<Supplier> suppliers = supplierService.search(parameter.getSearchString());
		
		for (Supplier supplier : suppliers) {
			results.add(new SearchResult(SearchEntity.SUPPLIER, supplier
					.getName(), supplier.getId()));
		}


		redirectAttrs.addFlashAttribute("results", results);
		return "redirect:/search/results";
	}

	@RequestMapping(value = "/componentsearch", method = RequestMethod.POST)
	public String componentSearch(final SearchParameter parameter,
			RedirectAttributes redirectAttrs) {
		List<SearchResult> results = new ArrayList<SearchResult>();
		
		List<Component> components = componentService.search(parameter.getSearchString());
		
		for (Component component : components) {
			results.add(new SearchResult(SearchEntity.COMPONENT, component.getDescription(), component.getId()));
		}

		redirectAttrs.addFlashAttribute("results", results);

		return "redirect:/search/results";
	}

	@RequestMapping(value = "/ordersearch", method = RequestMethod.POST)
	public String orderSearch(final SearchParameter parameter,
			RedirectAttributes redirectAttrs) {
		List<SearchResult> results = new ArrayList<SearchResult>();
		results.add(new SearchResult(SearchEntity.ORDER,
				"Buffy's Beads - 12/09/2012", 1));

		redirectAttrs.addFlashAttribute("results", results);

		return "redirect:/search/results";
	}

	@RequestMapping(value = "/supplier")
	public ModelAndView showSupplierSearch() {
		Model model = new Model("search");
		model.addObject("type", SearchEntity.SUPPLIER);
		return model;
	}

	@RequestMapping(value = "/component")
	public ModelAndView showComponentSearch() {
		Model model = new Model("search");
		model.addObject("type", SearchEntity.COMPONENT);
		return model;
	}

	@RequestMapping(value = "/order")
	public ModelAndView showOrderSearch() {
		Model model = new Model("search");
		model.addObject("type", SearchEntity.ORDER);
		return model;
	}

	@RequestMapping("/results")
	public Model showResults(RedirectAttributes redirectAttrs) {
		return new Model("searchresults");
	}
}
