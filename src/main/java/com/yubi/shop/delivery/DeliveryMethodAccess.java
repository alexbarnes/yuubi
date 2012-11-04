package com.yubi.shop.delivery;

import java.util.List;

public interface DeliveryMethodAccess {
	
	public DeliveryMethod get(long id);
	
	public List<DeliveryMethod> loadForCountry(String code);

}
