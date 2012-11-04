package com.yubi.application.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(
		basePackages = { "com.yubi.application" }, 
		includeFilters = { @Filter(type = FilterType.ANNOTATION, value = Controller.class) })
@Import({ DispatcherConfig.class })
public class ApplicationConfig {
	
}