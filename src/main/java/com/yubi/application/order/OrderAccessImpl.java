package com.yubi.application.order;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderAccessImpl implements OrderAccess {
	
	private SessionFactory sessionFactory;
	
	@Inject
	public OrderAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	
	@SuppressWarnings("unchecked")
	public List<ComponentOrder> listDeliveriesBeforeOrOn(LocalDate date) {
		Query query = sessionFactory.getCurrentSession().createQuery("from ComponentOrder where deliveryDate <= :horizon");
		query.setParameter("horizon", new LocalDate().plusDays(3).toDate());
		query.setCacheable(true);
		
		return query.list();
	}

	@Transactional
	public ComponentOrder load(long id) {
		ComponentOrder order = (ComponentOrder) sessionFactory.getCurrentSession().get(ComponentOrder.class, id);
		Hibernate.initialize(order.getItems());
		return order;
	}


	public void save(ComponentOrder order) {
		sessionFactory.getCurrentSession().saveOrUpdate(order);
	}

}
