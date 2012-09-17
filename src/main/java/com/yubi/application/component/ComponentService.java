package com.yubi.application.component;

import java.util.List;

import com.yubi.application.home.StockAlert;

public interface ComponentService {
	
	public List<StockAlert> stockAlerts();
	
	public Component loadComponentWithHistory(long id);

}
