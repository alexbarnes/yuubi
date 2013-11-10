package com.yubi.application.user;

import org.hibernate.validator.constraints.NotEmpty;

public class PasswordChange {
	
	@NotEmpty
	private String username;
	
	@NotEmpty(message="Password must be entered")
	private String password1;
	
	@NotEmpty(message="Password must be entered")
	private String password2;
	
	public PasswordChange() {
		super();
	}
	
	public PasswordChange(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
	

}
