package com.yubi.application.product;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	private final SessionFactory sessionFactory;

	@Inject
	public ProductServiceImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
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

}
