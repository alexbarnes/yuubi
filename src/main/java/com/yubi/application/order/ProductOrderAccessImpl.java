package com.yubi.application.order;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProductOrderAccessImpl implements ProductOrderAccess {
	
	private final SessionFactory sessionFactory;
	
	@Inject
	public ProductOrderAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	
	public long save(ProductOrder order) {
		sessionFactory.getCurrentSession().save(order);
		sessionFactory.getCurrentSession().flush();
		return order.getId();
	}

	
	public ProductOrder get(long id) {
		return (ProductOrder) sessionFactory.getCurrentSession().get(ProductOrder.class, id);
	}


	public ProductOrder loadByReference(String ref) {
		Query query = sessionFactory.getCurrentSession().createQuery("from ProductOrder where orderReference = :orderReference");
		query.setString("orderReference", ref);
		return (ProductOrder) query.uniqueResult();
	}


	@SuppressWarnings("unchecked")
	public List<ProductOrder> recentOrders() {
		Query query = sessionFactory.getCurrentSession().createQuery("from ProductOrder ORDER BY id DESC");
		query.setMaxResults(10);
		return query.list();
	}

}
