package com.yubi.application.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

	public List<SearchResult> search(String query) {
		List<SearchResult> results = new ArrayList<SearchResult>();
		results.add(new SearchResult(SearchEntity.SUPPLIER, "Buffy's Beads", 1));
		results.add(new SearchResult(SearchEntity.COMPONENT, "Blue Bead", 1));
		return results;
	}

}
