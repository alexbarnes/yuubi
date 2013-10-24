package com.yubi.core.view;

import java.util.Currency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yubi.shop.basket.Basket;
import com.yubi.shop.basket.BasketCreationListener;

public class UrlRecordingInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(UrlRecordingInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		
		if (session.getAttribute(BasketCreationListener.BASKET_KEY) == null) {
			log.info("Creating basket for session with id [" + session.getId() + "].");
			Basket basket = new Basket();
			session.setAttribute(BasketCreationListener.BASKET_KEY, basket);
			session.setMaxInactiveInterval(60*10);
			session.setAttribute("currency", Currency.getInstance(request.getLocale()));
		}
		return super.preHandle(request, response, handler);
	}

}
