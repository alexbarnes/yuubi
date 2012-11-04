package com.yubi.application.shop;

import java.util.List;

public interface DeliveryMethodAccess {
	
	public DeliveryMethod get(long id);
	
	public List<DeliveryMethod> loadForCountry(String code);

}
