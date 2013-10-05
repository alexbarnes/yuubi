package com.yubi.core.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
public class HibernateConfig {
	
	@Inject
	private DataSource dataSource;

	/**
	 * Configure a hibernate session factory. This reads properties from the
	 * {@linkplain Environment}.
	 * 
	 * @return an initialised session factory
	 */
	@SuppressWarnings("deprecation")
	@Bean
	public SessionFactory sessionFactory() throws Exception {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "false");
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.search.default.directory_provider",
				"org.hibernate.search.store.impl.RAMDirectoryProvider");
		properties.put("hibernate.cache.use_second_level_cache", true);
		properties.put("hibernate.cache.use_query_cache", true);
		properties.put("hibernate.cache.region.factory_class", 
				"org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");

		return new LocalSessionFactoryBuilder(dataSource)
				.scanPackages("com.yubi").addProperties(properties)
				.buildSessionFactory();
	}

	/**
	 * Return an instance of the {@linkplain HibernateTransactionManager}
	 * configured with a {@linkplain SessionFactory}.
	 * 
	 * @return a {@linkplain HibernateTransactionManager}
	 */
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		return new HibernateTransactionManager(sessionFactory());
	}
	
	@Profile(value = "AWS")
	public static class AWSDataSource {
		
		@SuppressWarnings("deprecation")
		@Bean
		public DataSource dataSource() {
			
			String url = System.getProperty("JDBC_CONNECTION_STRING");
			String userName = System.getProperty("PARAM1");
			String password = System.getProperty("PARAM2");
			
			return new DriverManagerDataSource(
					"com.mysql.jdbc.Driver", 
					url, 
					userName, 
					password);
		}
	}
	
	@Profile(value = "LOCAL")
	public static class LocalDataSource {
		
		@Inject
		private Environment env;
		
		@SuppressWarnings("deprecation")
		@Bean
		public DataSource dataSource() {
			DatabasePlatform platform = 
					DatabasePlatform.valueOf(env.getProperty("database.platform"));
			
			return new DriverManagerDataSource(platform.getDriverName(), 
					platform.getUrl(
							getDatabaseProperty("host"), 
							Integer.parseInt(getDatabaseProperty("port")), 
							getDatabaseProperty("name")), 
					getDatabaseProperty("username"), 
					getDatabaseProperty("password"));
		}
		
		private String getDatabaseProperty(String name) {
			return env.getProperty("database." + name);
		}
	}

	
	
	
}
