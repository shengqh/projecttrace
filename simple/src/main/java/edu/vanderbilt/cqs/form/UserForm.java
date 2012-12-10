package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.Set;

import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.User;

public class UserForm implements Serializable {

	private static final long serialVersionUID = -2055034778802964462L;

	private User user;

	private Set<Role> roleList;

	private Set<Role> roles;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(Set<Role> roleList) {
		this.roleList = roleList;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
