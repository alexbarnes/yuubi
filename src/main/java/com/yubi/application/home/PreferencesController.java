package com.yubi.application.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;
import com.yubi.application.user.User;

@Controller
@RequestMapping("/admin/preferences")
public class PreferencesController {
	
	@RequestMapping("/")
	public Model show() {
		return new Model(ScreenMode.ENQUIRE, "preferences", "user", new User());
	}

}
