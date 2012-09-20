package com.yubi.application.core;

public interface UserAccess {
	
	public User loadByUserName(String userName);
	
	public User fetchByEmail(String email);

}
