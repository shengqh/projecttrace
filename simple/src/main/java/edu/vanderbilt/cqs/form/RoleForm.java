package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.Set;

import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Role;

public class RoleForm implements Serializable{

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	private static final long serialVersionUID = 2186826131450489036L;
	
	private Role role;
	
	private Set<Permission> permissionList;
	
	public Set<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(Set<Permission> permissionList) {
		this.permissionList = permissionList;
	}

	public Set<Long> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Long> permissions) {
		this.permissions = permissions;
	}

	private Set<Long> permissions;

}
