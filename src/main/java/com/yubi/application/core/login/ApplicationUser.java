package com.yubi.application.core.login;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

public class ApplicationUser extends User {
	
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private String name;
	
	private boolean administrator;

	public ApplicationUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities, String name, boolean enabled, boolean administrator) {
		super(username, password, authorities);
		this.name = name;
		this.administrator = administrator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isAdministrator() {
		return administrator;
	}

}
