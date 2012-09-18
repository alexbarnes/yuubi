package com.yubi.application.order;

import java.util.List;

import com.yubi.application.home.UpcomingDelivery;

public interface OrderService {
	
	public List<UpcomingDelivery> upcomingDeliveries();

}
