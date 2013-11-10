package com.yubi.core.view;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yubi.application.admin.ShopStatusService;

public class ShopStatusInterceptor extends HandlerInterceptorAdapter {
	
	private final ShopStatusService shopStatusService;
	
	@Inject
	public ShopStatusInterceptor(ShopStatusService shopStatusService) {
		super();
		this.shopStatusService = shopStatusService;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			boolean open = shopStatusService.isOpen();
			modelAndView.addObject("open", open);
			if (!open) {
				modelAndView.addObject("closedmessage", shopStatusService.getClosedMessage());
			}
		}
		
		super.postHandle(request, response, handler, modelAndView);
	}
	
	

}
