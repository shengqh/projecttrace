package edu.vanderbilt.cqs.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController extends RootController {
	@RequestMapping("/")
	@Secured("ROLE_OBSERVER")
	public String goindex() {
		return "redirect:/project";
	}

	@RequestMapping("/home")
	@Secured("ROLE_OBSERVER")
	public String gohome(ModelMap model) {
		return "home";
	}
}