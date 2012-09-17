package com.yubi.application.supplier;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SupplierAccessImpl implements SupplierAccess {

	private SessionFactory sessionFactory;

	@Inject
	public SupplierAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public Supplier load(long id) {
		return (Supplier) sessionFactory.getCurrentSession().get(
				Supplier.class, id);
	}

	@Transactional
	public void save(Supplier supplier) {
		sessionFactory.getCurrentSession().saveOrUpdate(supplier);
	}

	@Transactional
	public void delete(Supplier supplier) {
		sessionFactory.getCurrentSession().delete(supplier);
	}

}
