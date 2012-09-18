package com.yubi.application.order;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.home.UpcomingDelivery;

@Service
public class OrderServiceImpl implements OrderService {
	
	private OrderAccess orderAccess;
	
	@Inject
	public OrderServiceImpl(OrderAccess orderAccess) {
		super();
		this.orderAccess = orderAccess;
	}

	@Transactional(readOnly = true)
	public List<UpcomingDelivery> upcomingDeliveries() {
		
		List<UpcomingDelivery> deliveries = new ArrayList<UpcomingDelivery>();
		
		List<ComponentOrder> orders = orderAccess.listDeliveriesBeforeOrOn(new LocalDate().plusDays(3));
		
		for (ComponentOrder order : orders) {
			deliveries.add(new UpcomingDelivery(
					order.getId(),
					order.getSupplierOrderNumber(), 
					order.getSupplier().getName(),
					order.getDeliveryDate(), 
					order.getDescription()));
		}
		
		return deliveries;
	}

}
