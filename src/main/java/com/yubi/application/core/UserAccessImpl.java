package com.yubi.application.core;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
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
		return (User) sessionFactory.getCurrentSession().get(User.class,
				userName);
	}

	@SuppressWarnings("unchecked")
	public User fetchByEmail(String email) {
		Query query = sessionFactory.getCurrentSession().createQuery("from User where emailAddress = :address");
		query.setString("address", email);
		
		List<User> users = query.list();
		
		if (users.size() > 1) {
			throw new IllegalStateException("Duplicate email address found for [" + email + "].");
		}
		
		if (users.size() == 1) 
			return users.get(0);
		
		return null;
	}
}
