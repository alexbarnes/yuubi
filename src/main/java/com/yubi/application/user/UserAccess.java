package com.yubi.application.user;

import java.util.List;


public interface UserAccess {
	
	public User loadByUserName(String userName);
	
	public User fetchByEmail(String email);
	
	public void save(User user);
	
	public List<String> selectAdminUsers();

}
