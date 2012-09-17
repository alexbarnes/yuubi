package com.yubi.application.home;

import java.util.ArrayList;
import java.util.List;

public class HomeModel {
	
	private List<StockAlert> stockAlerts = new ArrayList<StockAlert>();
	
	private List<UpcomingDelivery> upcomingDeliveries = new ArrayList<UpcomingDelivery>();

	public List<StockAlert> getStockAlerts() {
		return stockAlerts;
	}

	public void setStockAlerts(List<StockAlert> stockAlerts) {
		this.stockAlerts = stockAlerts;
	}

	public List<UpcomingDelivery> getUpcomingDeliveries() {
		return upcomingDeliveries;
	}

	public void setUpcomingDeliveries(List<UpcomingDelivery> upcomingDeliveries) {
		this.upcomingDeliveries = upcomingDeliveries;
	}
}
