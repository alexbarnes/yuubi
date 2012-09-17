package com.yubi.application.home;

public class StockAlert {

	private final long stockItem;

	private final int number;

	private final String itemDescription;

	public StockAlert(long stockItem, int number, String itemDescription) {
		super();
		this.stockItem = stockItem;
		this.number = number;
		this.itemDescription = itemDescription;
	}

	public long getStockItem() {
		return stockItem;
	}

	public int getNumber() {
		return number;
	}

	public String getItemDescription() {
		return itemDescription;
	}

}
