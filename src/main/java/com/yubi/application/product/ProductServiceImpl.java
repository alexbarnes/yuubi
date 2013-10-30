package com.yubi.application.product;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.core.mail.EmailService;
import com.yubi.shop.notification.StockNotificationService;

@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	private final SessionFactory sessionFactory;
	
	private final StockNotificationService stockNotificationService;
	
	@Inject
	public ProductServiceImpl(
			SessionFactory sessionFactory,
			StockNotificationService stockNotificationService,
			EmailService emailService) {
		super();
		this.sessionFactory = sessionFactory;
		this.stockNotificationService = stockNotificationService;
	}

	@Transactional
	public boolean checkStockExists(String code, int quantity) {
		log.info("Checking stock level for product [" + code + "] require level of [" + quantity + "] or greater.");
		return ((Product) sessionFactory.getCurrentSession().get(Product.class,
				code)).getStockLevel() >= quantity;
	}

	@Transactional
	public void reduceStockLevel(String code, int quantity) {
		Product product = (Product) sessionFactory.getCurrentSession().get(
				Product.class, code);
		log.info("Reduced stock level for product with code [" + code + "] by [" + quantity + "].");
		product.setStockLevel(product.getStockLevel() - quantity);
		
	}

	@Transactional
	public void increaseStockLevel(String code, int quantity) {
		Product product = (Product) sessionFactory.getCurrentSession().get(
				Product.class, code);
		log.info("Increased stock level for product with code [" + code + "] by [" + quantity + "].");
		product.setStockLevel(product.getStockLevel() + quantity);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Product> search(String query) {
		
		final FullTextSession fullTextSession = Search
				.getFullTextSession(sessionFactory.getCurrentSession());

		final QueryBuilder b = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Product.class).get();

		org.apache.lucene.search.Query luceneQuery = b.keyword().wildcard()
				.onField("title")
				.andField("productDescription")
				.andField("code")
				.andField("category.description")
				.matching(query).createQuery();

		org.hibernate.Query fullTextQuery = fullTextSession
				.createFullTextQuery(luceneQuery);
		
		List<Product> products = fullTextQuery.list();
		 return products;
	}

	@Transactional
	public void setStockLevel(String code, int quantity) {
		Product product = (Product) sessionFactory.getCurrentSession().get(
				Product.class, code);
		log.info("Set stock level for product with code [" + code + "] to [" + quantity + "].");
		product.setStockLevel(quantity);
		stockNotificationService.notify(product);
	}

}
