package com.yubi.application.core.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.yubi.application.core.login.BasketCreationListener;
import com.yubi.application.core.login.BasketExpiryListener;
import com.yubi.application.product.ProductService;

@Configuration
@EnableWebMvc
public class DispatcherConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);

		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/").setCachePeriod(2592000);
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setRequestContextAttribute("requestContext");
		return resolver;
	}

	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

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