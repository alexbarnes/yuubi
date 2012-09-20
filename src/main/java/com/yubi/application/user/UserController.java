package com.yubi.application.user;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;

@Controller
@RequestMapping("/user")
public class UserController {

	private final UserAccess userAccess;

	@Inject
	public UserController(UserAccess userAccess) {
		super();
		this.userAccess = userAccess;
	}

	@RequestMapping("/checkduplicate/{name}")
	@ResponseBody
	public ModelAndView checkDuplicate(@PathVariable(value = "name") String name) {
		return new ModelAndView(new MappingJacksonJsonView(), "duplicate",
				userAccess.loadByUserName(name) != null);
	}

	@RequestMapping("/add")
	public Model addUser() {
		return new Model(ScreenMode.CREATE, "user", "user", new User());
	}

	@RequestMapping("/view/{name}")
	public Model viewUser(@PathVariable(value = "name") String name) {
		User user = userAccess.loadByUserName(name);
		return new Model(ScreenMode.ENQUIRE, "user", "user", user);
	}

	@RequestMapping("/edit/{name}")
	public Model editUser(@PathVariable(value = "name") String name) {
		User user = userAccess.loadByUserName(name);
		return new Model(ScreenMode.UPDATE, "user", "user", user);
	}
}
