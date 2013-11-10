package com.yubi.application.product;

import java.util.List;

public interface ProductService {
	
	public boolean checkStockExists(String code, int quantity);
	
	public void reduceStockLevel(String code, int quantity);
	
	public void increaseStockLevel(String code, int quantity);
	
	public void setStockLevel(String  code, int quantity);
	
	public Product load(String code);
	
	public List<Product> search(String query);
	
	public void save(Product toSave);
	
	public List<Product> listAll();
}
