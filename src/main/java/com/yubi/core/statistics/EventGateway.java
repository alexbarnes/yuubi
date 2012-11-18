package com.yubi.core.statistics;

import org.springframework.integration.annotation.Gateway;

public interface EventGateway {
	
	@Gateway
	public void recordShopEvent(ShopEvent event);

}
