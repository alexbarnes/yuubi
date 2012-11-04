package com.yubi.application.login;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yubi.application.user.User;
import com.yubi.application.user.UserAccess;

@Controller
public class LoginController {
	
	private final UserAccess userAccess;
	
	@Inject
	public LoginController(UserAccess userAccess) {
		super();
		this.userAccess = userAccess;
	}

	@RequestMapping("/admin/login")
	public String showLogin(ModelMap map) {
		return "login";
	}
	
	@RequestMapping(value="/admin/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
 
		model.addAttribute("error", "true");
		return "login";
 
	}
	
	@RequestMapping("/admin/forgotpassword")
	public String forgotPassword() {
		return "forgot";
	}
	
	@RequestMapping(value = "/admin/forgotpassword", method = RequestMethod.POST)
	public ModelAndView forgot(String email, RedirectAttributes redirectAttrs) {
		
		if (StringUtils.isEmpty(email)) {
			return new ModelAndView("forgot", "notSupplied", "true");
		}
		
		// Check the supplied e-mail address
		User user = userAccess.fetchByEmail(email);
		
		// If we have a user matching the e-mail send an e-mail
		if (user != null) {
			redirectAttrs.addFlashAttribute("success", "true");
			return new ModelAndView("redirect:/admin/admin/forgotpassword");
		}
		
		// If we can't find a user, warn the user
		return new ModelAndView("forgot", "notFound", "true");
		
	}
}
