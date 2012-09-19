package com.yubi.application.core.login;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.core.User;
import com.yubi.application.core.UserAccess;

@Service("userDetailsService")
public class HibernateUserDetailsService implements UserDetailsService {
	
	private final UserAccess userAccess;
	
	@Inject
	public HibernateUserDetailsService(UserAccess userAccess) {
		super();
		this.userAccess = userAccess;
	}

	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		User internalUser = userAccess.loadByUserName(username);
		
		if (internalUser != null) {
			return new org.springframework.security.core.userdetails.User(
							internalUser.getUserName(), 
							internalUser.getPassword(), 
					null);
		}
		
		throw new UsernameNotFoundException("User + " + username + " not found.");
	}
	

}
