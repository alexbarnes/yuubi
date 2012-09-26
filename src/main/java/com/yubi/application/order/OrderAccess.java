package com.yubi.application.order;

import java.util.List;

import org.joda.time.LocalDate;

public interface OrderAccess {
	
	public List<ComponentOrder> listDeliveriesBeforeOrOn(LocalDate date);
	
	public ComponentOrder load(long id);
	
	public void save(ComponentOrder order);

}
