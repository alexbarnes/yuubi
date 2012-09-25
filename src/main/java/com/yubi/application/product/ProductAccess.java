package com.yubi.application.product;

public interface ProductAccess {
	
	public Product load(String code);
	
	public void save(Product product);

}
