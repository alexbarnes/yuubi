package com.yubi.core.statistics;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "shopEventAccess")
public class ShopEventAccessImpl implements ShopEventAccess {
	
	private final SessionFactory sessionFactory;
	
	@Inject
	public ShopEventAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public void saveEvent(ShopEvent event) {
		sessionFactory.getCurrentSession().save(event);
	}
}