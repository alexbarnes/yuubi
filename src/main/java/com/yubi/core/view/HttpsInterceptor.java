package com.yubi.core.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class HttpsInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if (request.getHeader("X-Forwarded-Proto") != null) {
			boolean secure = request.getHeader("X-Forwarded-Proto").equalsIgnoreCase("https");
			
			if (!secure) {
				
				String redirectUrl = request.getRequestURL().toString();
				if(request.getQueryString() != null) {
					redirectUrl = redirectUrl + "?" + request.getQueryString();
				}
				
				// -- Replace http with https
				redirectUrl = redirectUrl.replace("http", "https");
				
				response.sendRedirect(redirectUrl);
				return false;
			}
		}
		return super.preHandle(request, response, handler);
	}

}
