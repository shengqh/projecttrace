package edu.vanderbilt.cqs.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import edu.vanderbilt.cqs.Utils;
import edu.vanderbilt.cqs.bean.CqsUtils;
import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.form.ChangePasswordForm;
import edu.vanderbilt.cqs.form.UserForm;
import edu.vanderbilt.cqs.service.ProjectService;
import edu.vanderbilt.cqs.validator.ChangePasswordValidator;
import edu.vanderbilt.cqs.validator.UserValidator;

@Controller
@SessionAttributes({ "currentuser" })
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private SimpleMailMessage templateMessage;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ChangePasswordValidator passwordValidator;

	@Autowired
	private UserValidator userValidator;

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
			@ModelAttribute("userForm") UserForm form, BindingResult result,
			SessionStatus status) {
		userValidator.validate(form, result);
		if (result.hasErrors()) {
			form.setRoles(CqsUtils.getRoleMap());
			return "edituser";
		} else {
			if (form.getUser().getId() == null) {
				String password = RandomStringUtils.randomAlphanumeric(8);

				User user = form.getUser();
				user.setEmail(user.getEmail().toLowerCase());
				user.setPassword(Utils.md5(password));
				user.setCreateDate(new Date());
				projectService.addUser(user);

				sendConfirmationEmail(user, password);

				logger.info(currentUser.getEmail() + " add user "
						+ user.getEmail() + " as " + user.getRoleName());
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

	@RequestMapping("/changeownpassword")
	@Secured("ROLE_OBSERVER")
	public String changeownpassword(ModelMap model) {
		ChangePasswordForm form = new ChangePasswordForm();
		model.put("changeOwnPasswordForm", form);

		return "changeownpassword";
	}

	@RequestMapping("/saveownpassword")
	@Secured("ROLE_OBSERVER")
	public String saveownpassword(
			@ModelAttribute("currentuser") User currentUser,
			@ModelAttribute("changeOwnPasswordForm") ChangePasswordForm form,
			ModelMap model, BindingResult result, SessionStatus status) {
		form.setCurrentUser(currentUser);

		passwordValidator.validate(form, result);

		if (result.hasErrors()) {
			return "changeownpassword";
		} else {
			String newPassword = Utils.md5(form.getNewPassword());
			projectService.updatePassword(currentUser.getId(), newPassword);
			currentUser.setPassword(newPassword);
			return "home";
		}
	}

	@RequestMapping("/resetpassword")
	@Secured("ROLE_ADMIN")
	public String resetpassword(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long userid, ModelMap model) {
		User user = projectService.findUser(userid);
		if (user != null) {
			String password = RandomStringUtils.randomAlphanumeric(8);

			user.setPassword(Utils.md5(password));
			projectService.updateUser(user);

			sendPasswordChangedEmail(user, password);

			logger.info(currentUser.getEmail() + " reseted user "
					+ user.getEmail() + " password.");
		}
		return "redirect:/user";
	}

	@RequestMapping("/changepassword")
	@Secured("ROLE_ADMIN")
	public String changepassword(@RequestParam("id") Long userid, ModelMap model) {
		User user = projectService.findUser(userid);
		if (user != null) {
			ChangePasswordForm form = new ChangePasswordForm();
			form.setTargetUser(user);
			model.put("changePasswordForm", form);
			return "changepassword";
		} else {
			return "redirect:/user";
		}
	}

	@RequestMapping("/savepassword")
	@Secured("ROLE_ADMIN")
	public String savepassword(@ModelAttribute("currentuser") User currentUser,
			@ModelAttribute("changePasswordForm") ChangePasswordForm form,
			ModelMap model, BindingResult result, SessionStatus status) {

		form.setCurrentUser(currentUser);

		passwordValidator.validate(form, result);

		if (result.hasErrors()) {
			return "changepassword";
		} else {
			String newPassword = Utils.md5(form.getNewPassword());
			projectService.updatePassword(form.getTargetUser().getId(),
					newPassword);
			if (form.getTargetUser().getId() == currentUser.getId()) {
				currentUser.setPassword(newPassword);
			}
			return "redirect:/user";
		}
	}

	private void sendConfirmationEmail(final User user, final String password) {
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
		} catch (MailException ex) {
			logger.error(ex.getMessage());
		}
	}

	private void sendPasswordChangedEmail(final User user, final String password) {
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
		} catch (MailException ex) {
			logger.error(ex.getMessage());
		}

	}
}
