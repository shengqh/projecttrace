package edu.vanderbilt.cqs.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.vanderbilt.cqs.bean.Permission;

@Controller
public class HomeController extends RootController {
	@RequestMapping("/")
	@Secured({ Permission.ROLE_ESTIMATION, Permission.ROLE_PROJECT_VIEW,
			Permission.ROLE_PROJECT_EDIT })
	public String goindex() {
		if (currentUser().hasPermission(Permission.ROLE_PROJECT_VIEW)
				|| currentUser().hasPermission(Permission.ROLE_PROJECT_EDIT)) {
			return "redirect:/project";
		} else {
			return "redirect:/estimateptms";
		}
	}

	@RequestMapping("/home")
	public String gohome(ModelMap model) {
		return "home";
	}
}