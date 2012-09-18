package com.yubi.application.component;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.Query;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.home.StockAlert;
import com.yubi.application.supplier.Supplier;

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

	@Transactional
	public List<Component> search(String query) {
		query = StringUtils.lowerCase(query);
		FullTextSession fullTextSession = Search
				.getFullTextSession(sessionFactory.getCurrentSession());

		final QueryBuilder b = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Component.class).get();
		Query luceneQuery = b.keyword().wildcard().onField("description")
				.andField("supplierProductCode").matching(query).createQuery();

		org.hibernate.Query fullTextQuery = fullTextSession
				.createFullTextQuery(luceneQuery);
		return fullTextQuery.list();
	}
}
