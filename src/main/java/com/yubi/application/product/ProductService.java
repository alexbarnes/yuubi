package com.yubi.application.product;

public interface ProductService {
	
	public boolean checkStockExists(String code, int quantity);
	
	public void reduceStockLevel(String code, int quantity);
	
	public void increaseStockLevel(String code, int quantity);
}
