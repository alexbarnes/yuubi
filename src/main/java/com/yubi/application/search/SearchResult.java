package com.yubi.application.search;


public class SearchResult {

	private final String description;

	private final SearchEntity entityType;

	private final long id;

	public SearchResult(SearchEntity entityType, String description, long id) {
		this.description = description;
		this.id = id;
		this.entityType = entityType;
	}

	public String getDescription() {
		return description;
	}

	public long getId() {
		return id;
	}

	public SearchEntity getEntityType() {
		return entityType;
	}
}