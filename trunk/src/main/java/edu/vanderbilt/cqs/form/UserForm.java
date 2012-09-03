package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.Map;

import edu.vanderbilt.cqs.bean.User;

public class UserForm implements Serializable {

	private static final long serialVersionUID = 5338177987871337284L;

	private User user;

	private Map<Integer, String> roles;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<Integer, String> getRoles() {
		return roles;
	}

	public void setRoles(Map<Integer, String> roles) {
		this.roles = roles;
	}
}
