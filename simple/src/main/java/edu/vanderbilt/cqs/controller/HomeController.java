package edu.vanderbilt.cqs.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.vanderbilt.cqs.bean.Permission;

@Controller
public class HomeController extends RootController {
	@RequestMapping("/")
	@Secured({Permission.ROLE_PROJECT_VIEW})
	public String goindex() {
		return "redirect:/project";
	}

	@RequestMapping("/home")
	@Secured({Permission.ROLE_PROJECT_VIEW})
	public String gohome(ModelMap model) {
		return "home";
	}
}