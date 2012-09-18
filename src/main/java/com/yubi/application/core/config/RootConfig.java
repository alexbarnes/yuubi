package com.yubi.application.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan(
		basePackages = "com.yubi.application", 
		includeFilters = {
				@Filter(type = FilterType.ANNOTATION, value = Service.class),
				@Filter(type = FilterType.ANNOTATION, value = Repository.class)
				}
		)
@Import({HibernateConfig.class})
@ImportResource(value = {"WEB-INF/spring/security.xml"})
public class RootConfig {

}
