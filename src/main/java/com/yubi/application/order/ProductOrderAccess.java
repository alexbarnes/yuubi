package com.yubi.application.order;

import java.util.List;

public interface ProductOrderAccess {
	
	public long save(ProductOrder order);
	
	public ProductOrder get(long id);
	
	public ProductOrder loadByReference(String ref);
	
	public List<ProductOrder> recentOrders();

}
