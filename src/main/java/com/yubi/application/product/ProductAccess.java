package com.yubi.application.product;

import java.util.List;

public interface ProductAccess {
	
	public Product load(String code);
	
	public List<Product> listForCategory(long categoryCode);
	
	public List<ProductSet> listSets();
	
	public void save(Product product);
	
	public int getStockCount(String code);
	
	public void deleteImage(long id);
	
	public ProductImage loadImage(long id);
	
	public ProductImage loadPrimaryImage(String code);

}
