package com.yubi.application.category;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryAccessImpl implements CategoryAccess {
	
	private final SessionFactory sessionFactory;
	
	@Inject
	public CategoryAccessImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Category> listProductParentCategories() {
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from Category where parentCategoryId is NULL");
		List<Category> categories = query.setCacheable(true).list();
		return categories;
	}

	
	public Category load(long id) {
		return (Category) sessionFactory.getCurrentSession().get(Category.class, id);
	}
}
