package edu.vanderbilt.cqs.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
@SessionAttributes({ "currentuser" })
public class HomeController extends RootController{
	@Autowired
	private ProjectService projectService;

	private static final Logger logger = Logger
			.getLogger(ProjectController.class);

	@RequestMapping("/")
	@Secured("ROLE_OBSERVER")
	public String gohome(ModelMap model) {
		String email = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		if (!model.containsAttribute("currentuser")) {
			User user = projectService.findUserByEmail(email);
			model.addAttribute("currentuser", user);
			logger.info("User " + email + " logged in.");
		}
		return "home";
	}
}