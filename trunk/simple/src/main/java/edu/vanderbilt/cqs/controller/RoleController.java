package edu.vanderbilt.cqs.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.RolePermission;
import edu.vanderbilt.cqs.form.RoleForm;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
public class RoleController extends RootController {
	private static final Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	private ProjectService projectService;

	@Autowired
	private Validator validator;

	@RequestMapping("/role")
	@Secured(Permission.ROLE_USER_EDIT)
	public String list(ModelMap model) {
		model.put("roles", projectService.listRole());
		return "/role/list";
	}

	@RequestMapping("/editrole")
	@Secured(Permission.ROLE_USER_EDIT)
	public String edit(@RequestParam("id") Long id, ModelMap model) {
		logger.info(currentUser().getUsername() + " editrole.");

		Role obj = projectService.findRole(id);
		if (obj == null) {
			return "redirect:/role";
		}
		RoleForm form = new RoleForm();
		form.setRole(obj);
		initRoleForm(obj, form);

		model.put("roleForm", form);
		return "/role/edit";
	}

	private void initRoleForm(Role obj, RoleForm form) {
		form.setPermissionList(new LinkedHashSet<Permission>(projectService
				.listPermission()));
		form.setPermissions(obj.getPermissionIds());
	}

	@RequestMapping(value = "/saverole", method = RequestMethod.POST)
	@Secured(Permission.ROLE_USER_EDIT)
	public String save(@Valid @ModelAttribute("roleForm") RoleForm roleForm,
			BindingResult result) {
		Role role = projectService.findRole(roleForm.getRole().getId());
		if (roleForm.getPermissions() == null) {
			role.getPermissions().clear();
		} else {
			List<RolePermission> oldPermission = new ArrayList<RolePermission>(
					role.getPermissions());
			List<Long> newPermission = new ArrayList<Long>(
					roleForm.getPermissions());

			for (RolePermission rp : oldPermission) {
				if (roleForm.getPermissions().contains(
						rp.getPermission().getId())) {
					newPermission.remove(rp.getPermission().getId());
				} else {
					role.getPermissions().remove(rp);
				}
			}

			for (Long id : newPermission) {
				Permission p = projectService.findPermission(id);
				role.getPermissions().add(new RolePermission(role, p));
			}
		}

		projectService.updateRole(role);
		logger.info(currentUser().getUsername() + " updaterole - "
				+ role.getName());

		return "redirect:/role";
	}
}