package com.yubi.application.component;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.home.StockAlert;

@Service
public class ComponentServiceImpl implements ComponentService {

	private final SessionFactory sessionFactory;
	
	private final ComponentAccess componentAccess;

	@Inject
	public ComponentServiceImpl(SessionFactory sessionFactory, ComponentAccess componentAccess) {
		super();
		this.sessionFactory = sessionFactory;
		this.componentAccess = componentAccess;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<StockAlert> stockAlerts() {
		List<Component> components = sessionFactory.getCurrentSession()
				.createQuery("from Component where stock <= stockAlertLimit")
				.list();

		List<StockAlert> alerts = new ArrayList<StockAlert>();
		for (Component component : components) {
			alerts.add(new StockAlert(component.getId(), component.getStock(),
					component.getDescription()));
		}
		return alerts;
	}

	@Transactional
	public Component loadComponentWithHistory(long id) {
		Component component = componentAccess.load(id);
		Hibernate.initialize(component.getStockHistory());
		return component;
	}
}
