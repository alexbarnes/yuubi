package com.yubi.application.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;

@Controller
@RequestMapping("/admin/user")
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
	
	@RequestMapping("/checkduplicateemail")
	@ResponseBody
	public ModelAndView checkDuplicateEmail(String address) {
		return new ModelAndView(new MappingJacksonJsonView(), "duplicate",
				userAccess.fetchByEmail(address) != null);
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

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Model save(@ModelAttribute("user") @Valid User user,
			BindingResult result, String screenMode) {

		if (screenMode.equals("CREATE")) {
			if (userAccess.loadByUserName(user.getUserName()) != null) {
				result.addError(new FieldError("user", "userName",
						"The username is in use"));
			} 
			
			if (userAccess.fetchByEmail(user.getEmailAddress()) != null) {
				result.addError(new FieldError("user", "userName",
						"The username is in use"));
			}
		}

		// Now check for binding errors and return to the screen if there
		// are any. Ensure that the screen mode is retained.
		if (result.hasErrors()) {
			return new Model(ScreenMode.valueOf(screenMode), "user", "user", user);
		}
		
		// No binding errors? Save or update the user.
		userAccess.save(user);
		
		// Send a redirect to the saved user. Ensure that we correctly encode the username.
		try {
			return new Model("redirect:/admin/user/view/" + URLEncoder.encode(user.getUserName(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
