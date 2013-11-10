package com.yubi.application.login;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.yubi.application.user.PasswordChange;
import com.yubi.application.user.User;
import com.yubi.application.user.UserAccess;
import com.yubi.application.user.UserService;

@Controller
public class LoginController {
        
        private final UserAccess userAccess;
        private final UserService userService;
        private final AuthenticationManager authenticationManager;
        
        @Inject
        public LoginController(UserAccess userAccess, UserService userService, AuthenticationManager authenticationManager) {
                super();
                this.userAccess = userAccess;
                this.userService = userService;
                this.authenticationManager = authenticationManager;
        }

        
        @RequestMapping("/admin/login")
        public String showLogin(ModelMap map) {
                return "admin/login";
        }
        
        
        @RequestMapping(value="/admin/loginfailed", method = RequestMethod.GET)
        public String loginerror(ModelMap model) {
                model.addAttribute("error", "true");
                return "admin/login";
        }
        
        
        @RequestMapping("/admin/forgotpassword")
        public String forgotPassword() {
                return "admin/forgot";
        }
        
        
        @RequestMapping(value = "/admin/forgotpassword", method = RequestMethod.POST)
        public ModelAndView forgot(String email, RedirectAttributes redirectAttrs) {
                
                if (StringUtils.isEmpty(email)) {
                        return new ModelAndView("admin/forgot", "notSupplied", "true");
                }
                
                // Check the supplied e-mail address
                User user = userAccess.fetchByEmail(email);
                
                // If we have a user matching the e-mail send an e-mail
                if (user != null) {
            		userService.createPasswordReset(email, user.getUserName());
                    redirectAttrs.addFlashAttribute("success", "true");
                    return new ModelAndView("redirect:/admin/resetsent");
                }
                
                // If we can't find a user, warn the user
                return new ModelAndView("admin/forgot", "notFound", "true");
        }
        
        
        @RequestMapping("/admin/resetsent")
        public String passwordResetSent() {
        	return "/admin/passwordresetsent";
        }
        
        
        /**
         * Show the reset password screen. Set up the request with the username.
         * 
         */
        @RequestMapping("/admin/password/change/{token}")
        public ModelAndView updatePasswordScreen(@PathVariable("token") String token) {
        	ModelAndView mav = new ModelAndView();
        	PasswordResetRequest request = userService.loadValidRequest(token);
        	
        	if (request == null) {
        		mav.setViewName("/shop/showerror");
        		return mav;
        	}
        	
        	// -- The token is valid and now used
        	userService.applyPasswordReset(token);
        	
        	mav.addObject("password", new PasswordChange(request.getUserName()));
        	mav.setViewName("/admin/changepassword");
        	return mav;
        }
        
        
        /**
         * Handle the posted reset password form. Return if there are validation errors.
         * 
         */
        @RequestMapping(value = "/admin/password/change", method = RequestMethod.POST)
        public ModelAndView updatePassword(@Valid @ModelAttribute("password") PasswordChange password, BindingResult bind) {
        	ModelAndView mav = new ModelAndView();
        	mav.addObject("password", password);
    		
    		// -- If values are missing return
    		if (bind.hasErrors()) {
    			mav.setViewName("/admin/changepassword");
    			return mav;
    		}
    		
    		// -- Check the passwords match
    		if (!password.getPassword1().equals(password.getPassword2())) {
    			bind.rejectValue("password1", "passwords-must-match", "The passwords must match");
    			
    			// -- Wipe out the passwords since the user needs to start again
    			password.setPassword1(null);
    			password.setPassword2(null);
    		}
    		
    		if (bind.hasErrors()) {
    			mav.setViewName("/admin/changepassword");
    			return mav;
    		}
    		
    		// -- No errors so change the password
        	String encryptedPassword = userService.changePassword(password);
        	
        	// -- Authenticate the user and redirect them to the admin landing page
        	Authentication request = new UsernamePasswordAuthenticationToken(password.getUsername(), encryptedPassword);
        	Authentication result = authenticationManager.authenticate(request);
        	SecurityContextHolder.getContext().setAuthentication(result);
        	
        	RedirectView view = new RedirectView("/admin/home");
        	view.setExposeModelAttributes(false);
        	mav.setView(view);
        	return mav;
        }
}