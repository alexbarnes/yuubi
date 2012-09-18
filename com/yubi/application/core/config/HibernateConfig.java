package com.yubi.application.core.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	/**
	 * Configure a hibernate session factory. This reads properties from the
	 * {@linkplain Environment}.
	 * 
	 * @return an initialised session factory
	 */
	@Bean
	public SessionFactory sessionFactory() throws Exception {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.search.default.directory_provider",
				"org.hibernate.search.store.impl.RAMDirectoryProvider");

		return new LocalSessionFactoryBuilder(dataSource())
				.scanPackages("com.yubi.application").addProperties(properties)
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

	@Bean
	public DataSource dataSource() {
		/*return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.build();
		*/
		return new DriverManagerDataSource(
				"com.mysql.jdbc.Driver", 
				"jdbc:mysql://localhost:3306/yubi", 
				"test", 
				"test");
	}

}
