package com.yubi.application.admin;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopStatusServiceImpl implements ShopStatusService {
	
	private final SessionFactory sessionFactory;
	
	@Inject
	public ShopStatusServiceImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	
	@Transactional
	public boolean isOpen() {
		ShopStatus status = (ShopStatus) sessionFactory.getCurrentSession().get(ShopStatus.class, 1L);
		return status.isOpen();
	}

	
	@Transactional
	public void updateStatus(boolean status) {
		ShopStatus record = (ShopStatus) sessionFactory.getCurrentSession().get(ShopStatus.class, 1L);
		record.setOpen(status);
	}


	@Transactional
	public String getClosedMessage() {
		return ((ShopStatus) sessionFactory.getCurrentSession().get(ShopStatus.class, 1L)).getClosedMessage();
	}
}
