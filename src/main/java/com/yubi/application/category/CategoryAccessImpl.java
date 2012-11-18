package com.yubi.application.category;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CategoryAccessImpl implements CategoryAccess {
	
	private final SessionFactory sessionFactory;
	
	@Inject
	public CategoryAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public Category loadWithChildren(long id) {
		Category category = (Category) sessionFactory.getCurrentSession().get(Category.class, id);
		Hibernate.initialize(category.getChildCategories());
		return category;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Category> listProductParentCategories() {
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from Category where parentCategoryId is NULL");
		List<Category> categories = query.setCacheable(true).list();
		return categories;
	}

	
	@Transactional
	public Category load(long id) {
		return (Category) sessionFactory.getCurrentSession().get(Category.class, id);
	}
}
