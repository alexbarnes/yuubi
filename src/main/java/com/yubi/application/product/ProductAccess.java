package com.yubi.application.product;

public interface ProductAccess {
	
	public Product load(String code);
	
	public void save(Product product);
	
	public void deleteImage(long id);
	
	public ProductImage loadImage(long id);

}
