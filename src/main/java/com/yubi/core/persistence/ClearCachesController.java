package com.yubi.core.persistence;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/caches")
public class ClearCachesController {

	private final static Logger logger = LoggerFactory
			.getLogger(ClearCachesController.class);

	private final SessionFactory sessionFactory;
	private final InitialIndexService initialIndexService;

	@Inject
	public ClearCachesController(SessionFactory sessionFactory, InitialIndexService initialIndexService) {
		super();
		this.sessionFactory = sessionFactory;
		this.initialIndexService = initialIndexService;
	}

	
	@RequestMapping("/clear")
	public @ResponseBody boolean clearCaches() {
		try {
			sessionFactory.getCache().evictEntityRegions();
			sessionFactory.getCache().evictCollectionRegions();
			sessionFactory.getCache().evictDefaultQueryRegion();
			sessionFactory.getCache().evictQueryRegions();
			initialIndexService.onApplicationEvent(null);
		logger.warn("Application caches succesfully cleared.");
		} catch (RuntimeException e) {
			logger.error("Application caches clear failed.");
			return false;
		}
		return true;
	}
}
