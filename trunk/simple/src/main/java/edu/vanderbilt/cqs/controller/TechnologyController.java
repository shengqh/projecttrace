package edu.vanderbilt.cqs.controller;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.Technology;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
public class TechnologyController extends RootController {
	private static final Logger logger = Logger
			.getLogger(TechnologyController.class);

	@Autowired
	private ProjectService projectService;

	@Autowired
	private Validator validator;

	@Secured(Role.ROLE_USER)
	@RequestMapping("/technology")
	public String list(ModelMap model) {
		model.put("technologyList", projectService.listTechnology());
		return "/technology/list";
	}

	@RequestMapping("/addtechnology")
	@Secured(Role.ROLE_VANGARD_USER)
	public String add(ModelMap model) {
		Technology tec = new Technology();
		model.put("technology", tec);
		return "/technology/edit";
	}

	@RequestMapping("/edittechnology")
	@Secured(Role.ROLE_VANGARD_USER)
	public String edit(@RequestParam("id") Long id,
			ModelMap model) {
		logger.info(currentUser().getUsername() + " edittechnology.");

		Technology tec = projectService.findTechnology(id);
		if (tec != null) {
			model.put("technology", tec);
			return "/technology/edit";
		} else {
			return "redirect:/technology";
		}
	}

	@RequestMapping(value = "/savetechnology", method = RequestMethod.POST)
	@Secured(Role.ROLE_VANGARD_USER)
	public String save(
			@Valid @ModelAttribute("technology") Technology tec,
			BindingResult result) {
		if (result.hasErrors()) {
			return "/technology/edit";
		}

		if (tec.getId() != null) {
			Technology old = projectService.findTechnology(tec.getId());
			BeanUtils.copyProperties(tec, old, new String[]{"platforms", "modules"});
			projectService.updateTechnology(old);
			logger.info(currentUser().getUsername() + " updatetechnology - " + tec.getName());
		} else {
			projectService.addTechnology(tec);
			logger.info(currentUser().getUsername() + " newtechnology - " + tec.getName());
		}

		return "redirect:/technology";
	}

	@RequestMapping("/deletetechnology/{id}")
	@Secured(Role.ROLE_ADMIN)
	public String delete(@PathVariable Long id) {
		projectService.removeTechnology(id);

		logger.info(currentUser().getUsername() + " deletetechnology "
				+ id.toString());

		return "redirect:/technology";
	}

	@RequestMapping("/showtechnology")
	@Secured(Role.ROLE_USER)
	public String show(
			@RequestParam("id") Long id,
			ModelMap model) {
		Technology tec = projectService.findTechnology(id);
		if (tec != null) {
			model.put("technology", tec);
			return "/technology/show";
		} else {
			return "redirect:/technology";
		}
	}
	
	@RequestMapping("/enabletechnology/{id}")
	@Secured(Role.ROLE_ADMIN)
	public String enable(@PathVariable Long id) {
		Technology tec = projectService.findTechnology(id);
		if (tec != null) {
			tec.setEnabled(true);
			projectService.updateTechnology(tec);
			logger.info(currentUser().getUsername() + " set technology " + tec.getName() + " enabled.");
		}
		return "redirect:/technology";
	}

	@RequestMapping("/disabletechnology/{id}")
	@Secured(Role.ROLE_ADMIN)
	public String disable(@PathVariable Long id) {
		Technology tec = projectService.findTechnology(id);
		if (tec != null) {
			tec.setEnabled(false);
			projectService.updateTechnology(tec);
			logger.info(currentUser().getUsername() + " set technology " + tec.getName() + " disabled.");
		}
		return "redirect:/technology";
	}
}