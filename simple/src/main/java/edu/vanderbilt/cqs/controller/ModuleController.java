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

import edu.vanderbilt.cqs.bean.Module;
import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Technology;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
public class ModuleController extends RootController {
	private static final Logger logger = Logger
			.getLogger(ModuleController.class);

	@Autowired
	private ProjectService projectService;

	@Autowired
	private Validator validator;

	@RequestMapping("/addmodule")
	@Secured(Permission.ROLE_MODULE_EDIT)
	public String add(@RequestParam("technologyid") Long technologyid,
			ModelMap model) {
		Technology tec = projectService.findTechnology(technologyid);
		if (tec == null) {
			return "redirect:/technology";
		}

		Module obj = new Module();
		obj.setTechnology(tec);
		obj.setModuleIndex(tec.getNextModuleIndex());
		model.put("module", obj);
		return "/technology/editmodule";
	}

	@RequestMapping("/editmodule")
	@Secured(Permission.ROLE_MODULE_EDIT)
	public String edit(@RequestParam("id") Long id, ModelMap model) {
		logger.info(currentUser().getUsername() + " editmodule.");

		Module obj = projectService.findModule(id);
		if (obj == null) {
			return "redirect:/technology";
		}

		model.put("module", obj);
		return "/technology/editmodule";
	}

	@RequestMapping(value = "/savemodule", method = RequestMethod.POST)
	@Secured(Permission.ROLE_MODULE_EDIT)
	public String save(@Valid @ModelAttribute("module") Module plat,
			BindingResult result) {
		if (result.hasErrors()) {
			return "/technology/editmodule";
		}

		if (plat.getId() != null) {
			Module obj = projectService.findModule(plat.getId());
			BeanUtils.copyProperties(plat, obj, new String[]{"technology"});
			projectService.updateModule(obj);
			logger.info(currentUser().getUsername() + " updatemodule - "
					+ plat.getName());
		} else {
			Technology tec = projectService.findTechnology(plat.getTechnology().getId());
			if(tec == null){
				return "redirect:/technology";
			}
			plat.setTechnology(tec);
			projectService.addModule(plat);
			logger.info(currentUser().getUsername() + " newtechnology - "
					+ plat.getName());
		}

		return "redirect:/showtechnology?id=" + plat.getTechnology().getId().toString();
	}

	@RequestMapping("/deletemodule/{id}")
	@Secured(Permission.ROLE_MODULE_EDIT)
	public String delete(@PathVariable Long id) {
		Module obj = projectService.findModule(id);
		if(obj == null){
			return "redirect:/technology";
		}
		
		Long technologyid = obj.getTechnology().getId();
		projectService.removeModule(obj);
		logger.info(currentUser().getUsername() + " deletemodule "
				+ id.toString());
		return "redirect:/showtechnology?id=" + technologyid.toString();
	}
}