package com.yubi.shop.discount;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DiscountAccessImpl implements DiscountAccess {
	
	private final SessionFactory sessionFactory;
	
	@Inject
	public DiscountAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public Discount get(String code) {
		return (Discount) sessionFactory.getCurrentSession().get(Discount.class, code);
	}

}
