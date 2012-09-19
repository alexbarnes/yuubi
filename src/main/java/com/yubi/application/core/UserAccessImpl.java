package com.yubi.application.core;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserAccessImpl implements UserAccess {
	
	private final SessionFactory sessionFactory;
	
	@Inject
	public UserAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public User loadByUserName(String userName) {
		return (User) sessionFactory.getCurrentSession().get(User.class, userName);
	}

}
