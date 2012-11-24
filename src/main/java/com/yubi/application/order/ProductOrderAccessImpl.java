package com.yubi.application.order;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProductOrderAccessImpl implements ProductOrderAccess {
	
	private final SessionFactory sessionFactory;
	
	@Inject
	public ProductOrderAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	public void save(ProductOrder order) {
		sessionFactory.getCurrentSession().save(order);
	}

}
