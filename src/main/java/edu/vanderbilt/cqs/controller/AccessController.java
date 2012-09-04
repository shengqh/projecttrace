package edu.vanderbilt.cqs.controller;

import org.jboss.logging.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.vanderbilt.cqs.bean.User;

@Controller
@RequestMapping
public class AccessController {
	private static final Logger logger = Logger
			.getLogger(ProjectController.class);
	
	@RequestMapping("/login")
	public String login(ModelMap model,
			@RequestParam(required = false) String message) {
		model.put("message", message);
		return "access/login";
	}

	@RequestMapping(value = "/denied")
	@Secured("ROLE_OBSERVER")
	public String denied() {
		return "access/denied";
	}

	@RequestMapping(value = "/login/failure")
	public String loginFailure() {
		String message = "Login Failure!";
		return "redirect:/login?message=" + message;
	}

	@RequestMapping(value = "/logout/success")
	public String logoutSuccess(@ModelAttribute("currentuser") User currentUser, ModelMap model) {
		logger.info("User " + currentUser.getEmail() + " logged out.");
		model.remove("currentuser");
		String message = "Logout Success!";
		return "redirect:/login?message=" + message;
	}
}