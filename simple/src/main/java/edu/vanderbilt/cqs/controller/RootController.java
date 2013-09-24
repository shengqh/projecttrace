package edu.vanderbilt.cqs.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.vanderbilt.cqs.bean.LogTrace;
import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
public class RootController {
	private static final Logger logger = Logger.getLogger(RootController.class);

	@Autowired
	private ProjectService projectService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// custom date binding
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	protected User currentUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	}

	protected boolean isAdmin() {
		return currentUser().hasRole(Role.ROLE_ADMIN);
	}

	protected boolean isAdStaff() {
		return currentUser().hasRole(Role.ROLE_VANGARD_ADSTAFF);
	}

	protected boolean isVangardUser() {
		User curUser = currentUser();
		return curUser.hasRole(Role.ROLE_ADMIN)
				|| curUser.hasRole(Role.ROLE_VANGARD_ADSTAFF)
				|| curUser.hasRole(Role.ROLE_VANGARD_FACULTY)
				|| curUser.hasRole(Role.ROLE_VANGARD_STAFF);
	}
	
	protected String getIpAddress() {
		return ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest().getRemoteAddr();
	}

	private void addLogTrace(String username, Logger.Level level, String action) {
		addLogTrace(username, level, action, null);
	}

	private void addLogTrace(String username, Logger.Level level,
			String action, Long projectId) {
		LogTrace log = new LogTrace();
		log.setLogDate(Calendar.getInstance().getTime());
		log.setUser(username);
		log.setAction(action.substring(0, Math.min(action.length(), 250)));
		log.setIpaddress(getIpAddress());
		log.setLevel(level.ordinal());
		log.setProjectId(projectId);
		projectService.addLogTrace(log);
	}

	protected void addUserLogInfo(String action, boolean addtodatabase) {
		logger.info(currentUser().getUsername() + ": " + action);
		if (addtodatabase) {
			addLogTrace(currentUser().getUsername(), Logger.Level.INFO, action);
		}
	}

	protected void addUserLogInfo(String action) {
		addUserLogInfo(action, true);
	}

	protected void addUserLogError(String action, boolean addtodatabase) {
		logger.error(currentUser().getUsername() + ": " + action);
		addLogTrace(currentUser().getUsername(), Logger.Level.ERROR, action);
	}

	protected void addUserLogError(String action) {
		addUserLogError(action, true);
	}

	protected void addSystemLogInfo(String action) {
		logger.info("system : " + action);
		addLogTrace("system", Logger.Level.INFO, action);
	}

	protected void addSystemLogError(String action) {
		logger.error("system : " + action);
		addLogTrace("system", Logger.Level.ERROR, action);
	}
}
