package com.yubi.application.component;

public class ComponentDTO {
	
	private long id;
	
	private String name;

	public ComponentDTO(long id, String description) {
		this.id = id;
		this.name = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
