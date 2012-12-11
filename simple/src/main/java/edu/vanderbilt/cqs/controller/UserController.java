package edu.vanderbilt.cqs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import edu.vanderbilt.cqs.Utils;
import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.bean.UserRole;
import edu.vanderbilt.cqs.form.ChangePasswordForm;
import edu.vanderbilt.cqs.form.UserForm;
import edu.vanderbilt.cqs.service.ProjectService;
import edu.vanderbilt.cqs.validator.ChangePasswordValidator;

@Controller
public class UserController extends RootController {
	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private Validator validator;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ChangePasswordValidator passwordValidator;

	protected boolean sendMail = true;

	protected boolean sendMailToVantageUserOnly = true;

	@RequestMapping("/user")
	@Secured(Permission.ROLE_USER_VIEW)
	public String listUsers(ModelMap model) {
		model.addAttribute("validUserList", projectService.listValidUser());
		return "user/list";
	}

	@RequestMapping("/alluser")
	@Secured(Permission.ROLE_USER_EDIT)
	public String listAllUsers(ModelMap model) {
		model.addAttribute("validUserList", projectService.listValidUser());
		model.addAttribute("invalidUserList", projectService.listInvalidUser());
		return "user/list";
	}

	private void initUserForm(UserForm form, User user) {
		form.setUser(user);
		form.setRoleList(new LinkedHashSet<Role>(projectService.listRole()));

		HashSet<Long> roles = new HashSet<Long>();
		for (UserRole ur : user.getRoles()) {
			roles.add(ur.getRole().getId());
		}
		form.setRoles(roles);
	}

	private void assignRole(User user, Set<Long> roles) {
		List<UserRole> roleList = new ArrayList<UserRole>();
		for (UserRole ur : user.getRoles()) {
			if (roles.contains(ur.getRole().getId())) {
				roleList.add(ur);
				roles.remove(ur.getRole().getId());
			}
		}

		for (Long r : roles) {
			roleList.add(new UserRole(user, projectService.findRole(r)));
		}

		user.getRoles().clear();
		user.getRoles().addAll(roleList);
	}

	@RequestMapping("/adduser")
	@Secured(Permission.ROLE_USER_EDIT)
	public String addUser(ModelMap model) {
		UserForm form = new UserForm();
		User user = new User();
		initUserForm(form, user);
		model.addAttribute("userForm", form);

		logger.info(currentUser().getUsername() + " try to add user ...");
		return "user/edit";
	}

	@RequestMapping("/edituser")
	@Secured(Permission.ROLE_USER_EDIT)
	public String editUser(@RequestParam("userid") Long userid, ModelMap model) {
		logger.info(currentUser().getUsername() + " try to edit user ...");
		User user = projectService.findUser(userid);
		if (user != null) {
			UserForm form = new UserForm();
			initUserForm(form, user);
			model.addAttribute("userForm", form);
			return "user/edit";
		} else {
			return "redirect:/userall";
		}
	}

	@RequestMapping("/saveuser")
	@Secured(Permission.ROLE_USER_EDIT)
	public String saveUser(@ModelAttribute("userForm") UserForm form,
			BindingResult result, SessionStatus status) {
		logger.info(currentUser().getUsername() + " try to save user ...");

		validator.validate(form, result);

		if (result.hasErrors()) {
			form.setRoleList(new HashSet<Role>(projectService.listRole()));
			return "user/edit";
		}

		if (form.getUser().getId() == null) {
			String password = RandomStringUtils.randomAlphanumeric(8);

			User user = form.getUser();
			user.setEmail(user.getEmail().toLowerCase());
			user.setPassword(Utils.md5(password));
			user.setCreateDate(new Date());
			assignRole(user, form.getRoles());

			projectService.addUser(user);
			logger.info(currentUser().getUsername() + " add user "
					+ user.getEmail());
			if (sendMail
					&& (!sendMailToVantageUserOnly || user.isVangardUser())) {
				sendConfirmationEmail(user, password);
			}
		} else {
			User user = projectService.findUser(form.getUser().getId());
			BeanUtils.copyProperties(form.getUser(), user, new String[] {
					"roles", "createDate" });
			assignRole(user, form.getRoles());

			projectService.updateUser(user);

			logger.info(currentUser().getUsername() + " update user "
					+ user.getEmail());
		}
		return "redirect:/alluser";
	}

	private String setUserEnabled(Long userid, Boolean value) {
		User user = projectService.findUser(userid);
		if (user != null) {
			user.setEnabled(value);
			projectService.updateUser(user);
			logger.info(currentUser().getUsername() + " set user "
					+ user.getEmail() + " enabled=" + value.toString());
		}

		return "redirect:/alluser";
	}

	private String setUserLocked(Long userid, Boolean value) {
		User user = projectService.findUser(userid);
		if (user != null) {
			user.setAccountNonLocked(!value);
			projectService.updateUser(user);
			logger.info(currentUser().getUsername() + " set user "
					+ user.getEmail() + " locked=" + value.toString());
		}

		return "redirect:/alluser";
	}

	private String setUserDeleted(Long userid, Boolean value) {
		User user = projectService.findUser(userid);
		if (user != null) {
			user.setAccountNonDeleted(!value);
			projectService.updateUser(user);
			logger.info(currentUser().getUsername() + " set user "
					+ user.getEmail() + " deleted=" + value.toString());
		}

		return "redirect:/alluser";
	}

	@RequestMapping("/enableuser/{userid}")
	@Secured(Permission.ROLE_USER_EDIT)
	public String enableUser(@PathVariable Long userid) {
		return setUserEnabled(userid, true);
	}

	@RequestMapping("/disableuser/{userid}")
	@Secured(Permission.ROLE_USER_EDIT)
	public String disableUser(@PathVariable Long userid) {
		return setUserEnabled(userid, false);
	}

	@RequestMapping("/lockuser/{userid}")
	@Secured(Permission.ROLE_USER_EDIT)
	public String lockUser(@PathVariable Long userid) {
		return setUserLocked(userid, true);
	}

	@RequestMapping("/unlockuser/{userid}")
	@Secured(Permission.ROLE_USER_EDIT)
	public String unlockUser(@PathVariable Long userid) {
		return setUserLocked(userid, false);
	}

	@RequestMapping("/deleteuser/{userid}")
	@Secured(Permission.ROLE_USER_EDIT)
	public String deleteUser(@PathVariable Long userid) {
		return setUserDeleted(userid, true);
	}

	@RequestMapping("/undeleteuser/{userid}")
	@Secured(Permission.ROLE_USER_EDIT)
	public String undeleteUser(@PathVariable Long userid) {
		return setUserDeleted(userid, false);
	}

	@RequestMapping("/deleteuserforever/{userid}")
	@Secured(Permission.ROLE_USER_EDIT)
	public String deleteUserForever(

	@PathVariable Long userid) {
		User user = projectService.findUser(userid);
		if (user != null) {
			projectService.removeUser(userid);
			logger.info(currentUser().getUsername() + " delete user "
					+ user.getEmail() + " foever");
		}

		return "redirect:/alluser";
	}

	@RequestMapping("/changeownpassword")
	public String changeownpassword(ModelMap model) {
		ChangePasswordForm form = new ChangePasswordForm();
		model.put("changeOwnPasswordForm", form);

		return "user/changeownpassword";
	}

	@RequestMapping("/saveownpassword")
	public String saveownpassword(
			@ModelAttribute("changeOwnPasswordForm") ChangePasswordForm form,
			ModelMap model, BindingResult result, SessionStatus status) {
		User cuser = projectService.findUser(currentUser().getId());

		form.setOldPassword(cuser.getPassword());

		passwordValidator.validate(form, result);

		if (result.hasErrors()) {
			return "user/changeownpassword";
		} else {
			String newPassword = Utils.md5(form.getNewPassword());
			projectService.updatePassword(currentUser().getId(), newPassword);
			return "home";
		}
	}

	@RequestMapping("/resetpassword/{userid}")
	@Secured(Permission.ROLE_USER_EDIT)
	public String resetpassword(
			@ModelAttribute("currentuser") User currentUser,
			@PathVariable Long userid, ModelMap model) {
		User user = projectService.findUser(userid);
		if (user != null) {
			String password = RandomStringUtils.randomAlphanumeric(8);

			user.setPassword(Utils.md5(password));
			projectService.updateUser(user);

			logger.info(currentUser.getEmail() + " reseted user "
					+ user.getEmail() + " password.");

			if (sendMail
					&& (!sendMailToVantageUserOnly || user.isVangardUser())) {
				sendPasswordChangedEmail(user, password);
			}
		}
		return "redirect:/alluser";
	}

	private String sendConfirmationEmail(final User user, final String password) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(user.getEmail());
				message.setSubject("Registration confirmation");
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("user", user);
				model.put("password", password);
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine,
						"edu/vanderbilt/cqs/vm/registration-confirmation.vm",
						model);
				message.setText(text, true);
			}
		};
		logger.info("Sending mail to " + user.getEmail() + " ...");
		try {
			this.mailSender.send(preparator);
			return null;
		} catch (MailException ex) {
			logger.error("Sending mail error " + ex.getMessage());
			return ex.getMessage();
		}
	}

	private String sendPasswordChangedEmail(final User user,
			final String password) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(user.getEmail());
				message.setSubject("Password reset confirmation");
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("user", user);
				model.put("password", password);
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine,
						"edu/vanderbilt/cqs/vm/password-reset-confirmation.vm",
						model);
				message.setText(text, true);
			}
		};
		logger.info("Sending mail to " + user.getEmail() + " ...");
		try {
			this.mailSender.send(preparator);
			return null;
		} catch (MailException ex) {
			logger.error("Sending mail error " + ex.getMessage());
			return ex.getMessage();
		}
	}
}
