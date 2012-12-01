package com.yubi.shop.delivery;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DeliveryMethodAccessImpl implements DeliveryMethodAccess {
	
	private final SessionFactory sessionFactory;
	
	@Inject
	public DeliveryMethodAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public DeliveryMethod get(long id) {
		return (DeliveryMethod) sessionFactory.getCurrentSession().get(DeliveryMethod.class, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<DeliveryMethod> loadForCountry(String code) {
		Query query =  sessionFactory.getCurrentSession().createQuery("from DeliveryMethod where country.code = ?");
		query.setString(0, code);
		query.setCacheable(true);
		List<DeliveryMethod> methods = query.list();
		return methods;
	}
}
