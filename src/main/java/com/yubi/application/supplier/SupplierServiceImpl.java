package com.yubi.application.supplier;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.Query;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplierServiceImpl implements SupplierService {

	private SessionFactory sessionFactory;

	@Inject
	public SupplierServiceImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Supplier> search(String query) {
		
		query = StringUtils.lowerCase(query);
		FullTextSession fullTextSession = Search
				.getFullTextSession(sessionFactory.getCurrentSession());

		final QueryBuilder b = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Supplier.class).get();
		Query luceneQuery = b.keyword().wildcard().onField("name")
				.andField("url").matching(query).createQuery();

		org.hibernate.Query fullTextQuery = fullTextSession
				.createFullTextQuery(luceneQuery);
		return fullTextQuery.list();
	}
}
