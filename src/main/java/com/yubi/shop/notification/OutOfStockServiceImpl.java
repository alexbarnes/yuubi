package com.yubi.shop.notification;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.product.ProductAccess;

@Service
public class OutOfStockServiceImpl implements OutOfStockService {
	
	private final SessionFactory sessionFactory;
	private ProductAccess productAccess;
	
	@Inject
	public OutOfStockServiceImpl(
			SessionFactory sessionFactory,
			ProductAccess productAccess) {
		super();
		this.sessionFactory = sessionFactory;
		this.productAccess = productAccess;
	}

	
	/* (non-Javadoc)
	 * @see com.yubi.shop.notification.OutOfStockServiceInterface#saveNewNotification(java.lang.String, java.lang.String)
	 */
	@Transactional
	public void saveNewNotification(String productCode, String email) {
		OutOfStockNotification notification = new OutOfStockNotification();
		notification.setEmail(email);
		notification.setProduct(productAccess.load(productCode));
		sessionFactory.getCurrentSession().save(notification);
	}

	
	/* (non-Javadoc)
	 * @see com.yubi.shop.notification.OutOfStockService#loadNotificationsForProduct(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<OutOfStockNotification> loadNotificationsForProduct(String code) {
		Query query = sessionFactory.getCurrentSession().createQuery("from OutOfStockNotification where product.code = :code and sent = false");
		query.setString("code", code);
		return query.list();
	}
}
