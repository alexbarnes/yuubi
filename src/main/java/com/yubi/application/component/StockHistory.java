package com.yubi.application.component;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class StockHistory {

	public enum StockChangeType {
		CREATE_PRODUCT, DELIVERY, MANUAL_CORRECTION;
	}

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(name = "componentId")
	private Component component;

	private StockChangeType type;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	private String narrative;
	
	private int newStockLevel;
	
	private int oldStockLevel;

	public StockChangeType getType() {
		return type;
	}

	public void setType(StockChangeType type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public String getNarrative() {
		return narrative;
	}

	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}

	public int getNewStockLevel() {
		return newStockLevel;
	}

	public void setNewStockLevel(int newStockLevel) {
		this.newStockLevel = newStockLevel;
	}

	public int getOldStockLevel() {
		return oldStockLevel;
	}

	public void setOldStockLevel(int oldStockLevel) {
		this.oldStockLevel = oldStockLevel;
	}

	public void setId(long id) {
		this.id = id;
	}
}
