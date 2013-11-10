package com.yubi.application.admin;

import java.security.Principal;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.yubi.application.user.PasswordChange;
import com.yubi.application.user.UserService;

@Controller
@RequestMapping("/admin/user")
public class UserController {
	
	private final UserService userService;
	
	@Inject
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@RequestMapping(value="/password/change", method=RequestMethod.POST)
	public ModelAndView changePassword(@Valid @ModelAttribute("password") PasswordChange password, BindingResult bind, Principal principal) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("password", new PasswordChange(principal.getName()));
		
		// -- If values are missing return
		if (bind.hasErrors()) {
			mav.setViewName("/admin/user");
			return mav;
		}
		
		// -- Check the passwords match
		if (!password.getPassword1().equals(password.getPassword2())) {
			bind.rejectValue("password1", "passwords-must-match", "The passwords must match");
		}
		
		if (bind.hasErrors()) {
			mav.setViewName("/admin/user");
			return mav;
		}
		
		// -- Update the password and encode it.
		userService.changePassword(password);
		
		RedirectView redirect = new RedirectView("/admin/user");
		mav.setView(redirect);
		return mav;
	}

}
