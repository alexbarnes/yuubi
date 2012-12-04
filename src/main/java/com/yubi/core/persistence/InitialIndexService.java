package com.yubi.core.persistence;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public interface InitialIndexService extends
		ApplicationListener<ContextRefreshedEvent> {

}
