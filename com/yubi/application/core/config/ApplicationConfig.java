package com.yubi.application.core.config;

import javax.inject.Inject;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.search.Search;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@ComponentScan(basePackages = {"com.yubi.application"}, excludeFilters = {@Filter(type = FilterType.ANNOTATION, value = Configuration.class)} )
@Import({DispatcherConfig.class, HibernateConfig.class})
public class ApplicationConfig implements ApplicationListener<ContextRefreshedEvent> {
	
	@Inject
	private SessionFactory sessionFactory;

	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event)  {
		try {
			Search.getFullTextSession(sessionFactory.getCurrentSession()).createIndexer().threadsToLoadObjects(1).startAndWait();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
