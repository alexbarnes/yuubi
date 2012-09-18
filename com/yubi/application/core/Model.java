package com.yubi.application.core;

import org.springframework.web.servlet.ModelAndView;

import com.yubi.application.search.SearchParameter;

public class Model extends ModelAndView {
	
	private ScreenMode screenMode = ScreenMode.ENQUIRE;
	
	public Model(ScreenMode screenMode, String view, String modelName, Object model) {
		super(view,modelName,model);
		this.addObject("screenMode", screenMode);
		this.addObject("search", new SearchParameter());
	}
	
	public Model(String view) {
		super(view);
		this.addObject("search", new SearchParameter());
	}
	
	
	public ScreenMode getScreenMode() {
		return screenMode;
	}
}
