package com.yubi.core.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionRecordingDeviceResolver extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Device device = new LiteDeviceResolver().resolveDevice(request);
		
		request.getSession(true).setAttribute("mobile", device.isMobile());
		return super.preHandle(request, response, handler);
	}
	
	

}
