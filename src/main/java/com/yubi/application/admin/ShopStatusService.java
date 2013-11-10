package com.yubi.application.admin;

public interface ShopStatusService {
	
	public boolean isOpen();
	
	public void updateStatus(boolean status);
	
	public String getClosedMessage();

}
