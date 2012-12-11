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

import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Platform;
import edu.vanderbilt.cqs.bean.Technology;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
public class PlatformController extends RootController {
	private static final Logger logger = Logger
			.getLogger(PlatformController.class);

	@Autowired
	private ProjectService projectService;

	@Autowired
	private Validator validator;

	@RequestMapping("/addplatform")
	@Secured(Permission.ROLE_MODULE_EDIT)
	public String add(@RequestParam("technologyid") Long technologyid,
			ModelMap model) {
		Technology tec = projectService.findTechnology(technologyid);
		if (tec == null) {
			return "redirect:/technology";
		}

		Platform plat = new Platform();
		plat.setTechnology(tec);
		model.put("platform", plat);
		return "/technology/editplatform";
	}

	@RequestMapping("/editplatform")
	@Secured(Permission.ROLE_MODULE_EDIT)
	public String edit(@RequestParam("id") Long id, ModelMap model) {
		logger.info(currentUser().getUsername() + " editplatform.");

		Platform plat = projectService.findPlatform(id);
		if (plat == null) {
			return "redirect:/technology";
		}

		model.put("platform", plat);
		return "/technology/editplatform";
	}

	@RequestMapping(value = "/saveplatform", method = RequestMethod.POST)
	@Secured(Permission.ROLE_MODULE_EDIT)
	public String save(@Valid @ModelAttribute("platform") Platform plat,
			BindingResult result) {
		if (result.hasErrors()) {
			return "/technology/editplatform";
		}

		if (plat.getId() != null) {
			Platform old = projectService.findPlatform(plat.getId());
			BeanUtils.copyProperties(plat, old, new String[]{"technology"});
			projectService.updatePlatform(old);
			logger.info(currentUser().getUsername() + " updateplatform - "
					+ plat.getName());
		} else {
			Technology tec = projectService.findTechnology(plat.getTechnology().getId());
			if(tec == null){
				return "redirect:/technology";
			}
			plat.setTechnology(tec);
			projectService.addPlatform(plat);
			logger.info(currentUser().getUsername() + " newtechnology - "
					+ plat.getName());
		}

		return "redirect:/showtechnology?id=" + plat.getTechnology().getId().toString();
	}

	@RequestMapping("/deleteplatform/{id}")
	@Secured(Permission.ROLE_MODULE_EDIT)
	public String delete(@PathVariable Long id) {
		Platform plat = projectService.findPlatform(id);
		if(plat == null){
			return "redirect:/technology";
		}
		
		Long technologyid = plat.getTechnology().getId();
		projectService.removePlatform(plat);
		logger.info(currentUser().getUsername() + " deleteplatform "
				+ id.toString());
		return "redirect:/showtechnology?id=" + technologyid.toString();
	}
}