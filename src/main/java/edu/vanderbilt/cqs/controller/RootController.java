package edu.vanderbilt.cqs.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import edu.vanderbilt.cqs.bean.SpringSecurityUser;

@Controller
public class RootController {

	protected SpringSecurityUser currentUser() {
		return (SpringSecurityUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}
}
