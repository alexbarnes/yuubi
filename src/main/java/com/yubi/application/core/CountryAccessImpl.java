package com.yubi.application.core;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class CountryAccessImpl implements CountryAccess {

	private final SessionFactory sessionFactory;
	
	public CountryAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Country> listAll() {
		return sessionFactory.getCurrentSession().createQuery("from Country").list();
	}
}

