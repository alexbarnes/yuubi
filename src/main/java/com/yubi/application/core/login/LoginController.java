package com.yubi.application.core.login;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yubi.application.core.User;
import com.yubi.application.core.UserAccess;

@Controller
public class LoginController {
	
	private final UserAccess userAccess;
	
	@Inject
	public LoginController(UserAccess userAccess) {
		super();
		this.userAccess = userAccess;
	}

	@RequestMapping("/login")
	public String showLogin(ModelMap map) {
		return "login";
	}
	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
 
		model.addAttribute("error", "true");
		return "login";
 
	}
	
	@RequestMapping("/forgotpassword")
	public String forgotPassword() {
		return "forgot";
	}
	
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ModelAndView forgot(String email) {
		
		User user = userAccess.fetchByEmail(email);
		
		if (user != null)
			return new ModelAndView("redirect:/login");
		
		return new ModelAndView("login", "error", "not found");
		
	}
}
