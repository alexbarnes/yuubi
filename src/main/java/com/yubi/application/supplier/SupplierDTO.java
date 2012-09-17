package com.yubi.application.supplier;

public class SupplierDTO {
	
	private final long id;
	
	private final String name;

	public SupplierDTO(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
