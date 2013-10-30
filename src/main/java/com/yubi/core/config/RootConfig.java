package com.yubi.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan(basePackages = { "com.yubi" }, includeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = Service.class),
		@Filter(type = FilterType.ANNOTATION, value = Repository.class) }, excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = Configuration.class),
		@Filter(type = FilterType.ANNOTATION, value = Controller.class) })
@Import({ HibernateConfig.class, MailConfig.class })
@PropertySource(name = "props", value = "classpath:application.properties")
public class RootConfig {
}