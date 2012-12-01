package com.yubi.shop.paypal;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Alex Barnes
 */
@Repository
public class PaypalRequestAccessImpl implements PaypalRequestAccess {
	
	private final SessionFactory sessionFactory;

	/**
	 * @param sessionFactory
	 */
	@Inject
	public PaypalRequestAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public void save(PaypalRequest request) {
		sessionFactory.getCurrentSession().save(request);
	}

	@Transactional
	public void update(PaypalRequest request) {
		sessionFactory.getCurrentSession().saveOrUpdate(request);
	}
}
