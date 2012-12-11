package edu.vanderbilt.cqs.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.User;

@Controller
public class RootController {

	protected User currentUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	}

	protected boolean isCurrentPowerUser() {
		return currentUser().hasRole(Role.ROLE_ADMIN);
	}
}
