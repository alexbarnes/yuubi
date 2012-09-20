package com.yubi.application.user;


public interface UserAccess {
	
	public User loadByUserName(String userName);
	
	public User fetchByEmail(String email);
	
	public void save(User user);

}
