package com.yubi.application.product;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductAccessImpl implements ProductAccess {
	
	private final SessionFactory sessionFactory;
	
	@Inject
	public ProductAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public Product load(String code) {
		Product product =  (Product) sessionFactory.getCurrentSession().get(Product.class, code);
		Hibernate.initialize(product.getComponents());
		return product;
	}
}
