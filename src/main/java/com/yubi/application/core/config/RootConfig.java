package com.yubi.application.core.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.yubi.application.core.login.BasketCreationListener;
import com.yubi.application.core.login.BasketExpiryListener;
import com.yubi.application.product.ProductService;

@Configuration
@ComponentScan(basePackages = "com.yubi.application", includeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = Service.class),
		@Filter(type = FilterType.ANNOTATION, value = Repository.class) })
@Import({ HibernateConfig.class })
@ImportResource(value = { "WEB-INF/spring/security.xml" })
@PropertySource(value = "classpath:application.properties")
public class RootConfig {

	@Inject
	private ProductService productService;

	@Bean
	public BasketCreationListener basketCreationListener() {
		return new BasketCreationListener();
	}

	@Bean
	public BasketExpiryListener basketExpiryListener() {
		return new BasketExpiryListener(productService);
	}
}
