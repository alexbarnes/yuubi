package com.yubi.core.config;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.yubi.application.admin.ShopStatusService;
import com.yubi.core.view.HttpsInterceptor;
import com.yubi.core.view.SessionRecordingDeviceResolver;
import com.yubi.core.view.ShopStatusInterceptor;
import com.yubi.core.view.UrlRecordingInterceptor;

@Configuration
@EnableWebMvc
public class DispatcherConfig extends WebMvcConfigurerAdapter {
	
	@Inject
	ShopStatusService shopStatusService;
	
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
	
	@Bean
    public MessageSource messageSource() { 
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        return messageSource;
    }  
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UrlRecordingInterceptor()).addPathPatterns("/shop/**").excludePathPatterns("/resources/**");
		registry.addInterceptor(new HttpsInterceptor()).addPathPatterns("/shop/**").excludePathPatterns("/resources/**");
		registry.addInterceptor(new ShopStatusInterceptor(shopStatusService)).addPathPatterns("/shop/**").excludePathPatterns("/resources/**").excludePathPatterns("/image/**");
		registry.addInterceptor(new SessionRecordingDeviceResolver()).addPathPatterns("/shop/**").excludePathPatterns("/resources/**").excludePathPatterns("/image/**");
	}
	
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}