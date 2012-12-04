package com.yubi.core.persistence;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.search.Search;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InitialIndexServiceImpl implements InitialIndexService {
	
	private final SessionFactory sessionFactory;
	
	@Inject
	public InitialIndexServiceImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Search.getFullTextSession(sessionFactory.getCurrentSession()).createIndexer().start();
	}
}
