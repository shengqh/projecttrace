package edu.vanderbilt.cqs.controller;

import java.util.Date;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.vanderbilt.cqs.Utils;
import edu.vanderbilt.cqs.bean.CqsUtils;
import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.form.UserForm;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
@SessionAttributes({ "currentuser" })
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private ProjectService projectService;

	@RequestMapping("/user")
	@Secured("ROLE_USER")
	public String listUsers(ModelMap model) {
		model.addAttribute("userList", projectService.listUser());
		return "user";
	}

	@RequestMapping("/adduser")
	@Secured("ROLE_ADMIN")
	public String addUser(ModelMap model) {
		UserForm form = new UserForm();
		User user = new User();
		user.setEmail("youremail");
		user.setFirstname("firstname");
		form.setUser(new User());
		form.getUser().setRole(Role.USER);
		form.setRoles(CqsUtils.getRoleMap());
		model.addAttribute("userForm", form);
		return "edituser";
	}

	@RequestMapping("/edituser")
	@Secured("ROLE_ADMIN")
	public String editUser(@RequestParam("id") Long userid, ModelMap model) {
		User user = projectService.findUser(userid);
		if (user != null) {
			UserForm form = new UserForm();
			form.setUser(user);
			form.setRoles(CqsUtils.getRoleMap());
			model.addAttribute("userForm", form);
			return "edituser";
		} else {
			return "redirect:/user";
		}
	}

	@RequestMapping("/saveuser")
	@Secured("ROLE_ADMIN")
	public String saveUser(@ModelAttribute("currentuser") User currentUser,
			@ModelAttribute("userform") UserForm form) {
		if (form.getUser().getId() == null) {
			if (form.getUserPassword().equals(form.getConfirmPassword())) {
				User user = form.getUser();
				user.setPassword(Utils.md5(form.getUserPassword()));
				user.setCreateDate(new Date());
				projectService.addUser(user);

				logger.info(currentUser.getEmail() + " add user "
						+ user.getEmail() + " as " + user.getRoleName());
			} else {
				logger.error("Password not matched!");
			}
		} else {
			User user = projectService.findUser(form.getUser().getId());
			user.setRole(form.getUser().getRole());
			user.setFirstname(form.getUser().getFirstname());
			user.setLastname(form.getUser().getLastname());
			user.setEnabled(form.getUser().getEnabled());
			user.setExpired(form.getUser().getExpired());
			user.setLocked(form.getUser().getLocked());
			user.setTelephone(form.getUser().getTelephone());
			projectService.updateUser(user);

			logger.info(currentUser.getEmail() + " update user "
					+ user.getEmail() + " as " + user.getRoleName());
		}

		return "redirect:/user";
	}

	@RequestMapping("/deleteuser")
	@Secured("ROLE_ADMIN")
	public String deleteUser(@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long userid) {
		User user = projectService.findUser(userid);
		if (user != null) {
			projectService.removeUser(userid);
			logger.info(currentUser.getEmail() + " delete user "
					+ user.getEmail() + " as " + user.getRoleName());
		}

		return "redirect:/user";
	}
}
