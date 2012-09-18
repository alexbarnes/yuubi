package com.yubi.application.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;
import com.yubi.application.core.User;

@Controller
@RequestMapping("/configuration")
public class ConfigurationController {
	
	@RequestMapping("/")
	public Model show() {
		return new Model(ScreenMode.ENQUIRE, "configuration", "configuration", new User());
	}
}
