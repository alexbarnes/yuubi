package com.yubi.application.core;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CountryAccessImpl implements CountryAccess {

	private final SessionFactory sessionFactory;
	
	@Inject
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

