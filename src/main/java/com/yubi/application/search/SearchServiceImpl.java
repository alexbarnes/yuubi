package com.yubi.application.search;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yubi.application.component.Component;
import com.yubi.application.component.ComponentService;
import com.yubi.application.supplier.Supplier;
import com.yubi.application.supplier.SupplierService;

@Service
public class SearchServiceImpl implements SearchService {
	
	private final SupplierService supplierService;
	
	private final ComponentService componentService;
	
	@Inject
	public SearchServiceImpl(SupplierService supplierService,
			ComponentService componentService) {
		super();
		this.supplierService = supplierService;
		this.componentService = componentService;
	}
	
	
	public List<SearchResult> search(String query) {
		
		query = StringUtils.lowerCase(query);
		
		List<SearchResult> results = new ArrayList<SearchResult>();
		
		for (Supplier supplier : supplierService.search(query)) {
			results.add(new SearchResult(SearchEntity.SUPPLIER, supplier.getName(), supplier.getId()));
		}
		
		for (Component component : componentService.search(query)) {
			results.add(new SearchResult(SearchEntity.COMPONENT, component.getDescription(), component.getId()));
		}
		
		return results;
	}
}
