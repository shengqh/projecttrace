package edu.vanderbilt.cqs.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccessController extends RootController {
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
	public String logoutSuccess(ModelMap model) {
		model.remove("currentuser");
		String message = "Logout Success!";
		return "redirect:/login?message=" + message;
	}
}