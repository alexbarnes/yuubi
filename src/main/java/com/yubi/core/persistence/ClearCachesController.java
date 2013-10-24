package com.yubi.core.persistence;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/caches")
public class ClearCachesController {

	private final static Logger logger = LoggerFactory
			.getLogger(ClearCachesController.class);

	private final SessionFactory sessionFactory;
	private final Environment environment;
	private final InitialIndexService initialIndexService;

	@Inject
	public ClearCachesController(SessionFactory sessionFactory,
			Environment environment, InitialIndexService initialIndexService) {
		super();
		this.sessionFactory = sessionFactory;
		this.environment = environment;
		this.initialIndexService = initialIndexService;
	}

	@RequestMapping("/clear/{key}")
	public String clearCaches(@PathVariable("key") String key) {
		if (this.environment.getProperty("application.cache.reset.key").equals(
				key)) {
			sessionFactory.getCache().evictEntityRegions();
			sessionFactory.getCache().evictCollectionRegions();
			sessionFactory.getCache().evictDefaultQueryRegion();
			sessionFactory.getCache().evictQueryRegions();
			
			initialIndexService.onApplicationEvent(null);
			
			logger.warn("Application caches succesfully reset on receipt of key [" + key + "].");
		}
		return "redirect:/shop";
	}
}
