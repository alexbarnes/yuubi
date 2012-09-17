package com.yubi.application.component;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ComponentAccessImpl implements ComponentAccess {

	private SessionFactory sessionFactory;

	@Inject
	public ComponentAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public Component load(long id) {
		return (Component) sessionFactory.getCurrentSession().get(
				Component.class, id);
	}

	@Transactional
	public void save(Component component) {
		sessionFactory.getCurrentSession().saveOrUpdate(component);
		
	}

}
