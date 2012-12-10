package edu.vanderbilt.cqs.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.vanderbilt.cqs.bean.Role;

@Controller
public class HomeController extends RootController {
	@RequestMapping("/")
	@Secured({Role.ROLE_USER, Role.ROLE_VANGARD_USER, Role.ROLE_VANGARD_BUDGET_USER, Role.ROLE_ADMIN})
	public String goindex() {
		return "redirect:/project";
	}

	@RequestMapping("/home")
	@Secured({Role.ROLE_USER, Role.ROLE_VANGARD_USER, Role.ROLE_VANGARD_BUDGET_USER, Role.ROLE_ADMIN})
	public String gohome(ModelMap model) {
		return "home";
	}
}