package edu.vanderbilt.cqs.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.vanderbilt.cqs.service.ProjectService;

@Controller
public class HomeController extends RootController{
	@Autowired
	private ProjectService projectService;

	private static final Logger logger = Logger
			.getLogger(ProjectController.class);

	@RequestMapping("/")
	@Secured("ROLE_OBSERVER")
	public String gohome(ModelMap model) {
		logger.info("User " + currentUser().getUsername() + " logged in.");
		return "home";
	}
}