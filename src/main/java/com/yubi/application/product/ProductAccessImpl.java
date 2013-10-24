package com.yubi.application.product;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductAccessImpl implements ProductAccess {

	private final SessionFactory sessionFactory;

	@Inject
	public ProductAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public Product load(String code) {
		Product product = (Product) sessionFactory.getCurrentSession().get(
				Product.class, code);
		
		Hibernate.initialize(product.getImages());
		Hibernate.initialize(product.getSet());
		if (product.getSet() != null) {
			Hibernate.initialize(product.getSet().getItems());
		}
		return product;
	}

	@Transactional
	public void save(Product product) {
		sessionFactory.getCurrentSession().saveOrUpdate(product);
	}

	@Transactional
	public void deleteImage(long id) {
		ProductImage image = (ProductImage) sessionFactory.getCurrentSession()
				.get(ProductImage.class, id);
		sessionFactory.getCurrentSession().delete(image);
	}

	@Transactional
	public ProductImage loadImage(long id) {
		return (ProductImage) sessionFactory.getCurrentSession()
				.get(ProductImage.class, id);
	}

	@Transactional
	public ProductImage loadPrimaryImage(String code) {
		Query query = 
				sessionFactory.getCurrentSession().createQuery("from ProductImage where product.code = :code and primaryImage = true");
		
		query.setString("code", code);
		query.setCacheable(true);
		return (ProductImage) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Product> listForCategory(long id) {
		Query query = 
				sessionFactory.getCurrentSession().createQuery("from Product where category.id = :categoryId");
		
		
		query.setLong("categoryId", id);
		query.setCacheable(true);
		return query.list();
	}

	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ProductSet> listSets() {
		return sessionFactory.getCurrentSession().createQuery("from ProductSet").list();
	}

	@Transactional
	public int getStockCount(String code) {
		Query query =  sessionFactory.getCurrentSession().createQuery("select stockLevel from Product where code = :code");
		query.setString("code", code);
		return (Integer) query.uniqueResult();
	}
}
