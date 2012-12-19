package edu.vanderbilt.cqs.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.User;

@Controller
public class RootController {
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

	protected boolean isCurrentPowerUser() {
		return currentUser().hasRole(Role.ROLE_ADMIN);
	}
}
