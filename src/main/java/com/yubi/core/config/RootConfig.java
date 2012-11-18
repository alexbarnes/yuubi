package com.yubi.core.config;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Configuration
@ComponentScan(basePackages = {"com.yubi"}, includeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = Service.class),
		@Filter(type = FilterType.ANNOTATION, value = Repository.class) })
@Import({ HibernateConfig.class })
@ImportResource(value = { "/WEB-INF/spring/security.xml", "/WEB-INF/spring/integration.xml" })
@PropertySource(name = "props", value = "classpath:application.properties")
public class RootConfig implements ApplicationListener<ContextRefreshedEvent> {

	@Inject
	private SessionFactory sessionFactory;

	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
	
	}

}
