package com.yubi.application.supplier;

public interface SupplierAccess {
	
	public Supplier load(long id);
	
	public void save(Supplier supplier);
	
	public void delete(Supplier supplier);

}
