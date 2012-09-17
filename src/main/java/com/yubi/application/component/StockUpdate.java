package com.yubi.application.component;

import org.hibernate.validator.constraints.NotEmpty;

public class StockUpdate {
	
	private long componentId;
	
	private int newStockNumber;
	
	@NotEmpty(message = "A narrative must be entered")
	private String narrative;

	public long getComponentId() {
		return componentId;
	}

	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}

	public int getNewStockNumber() {
		return newStockNumber;
	}

	public void setNewStockNumber(int newStockNumber) {
		this.newStockNumber = newStockNumber;
	}

	public String getNarrative() {
		return narrative;
	}

	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
}
