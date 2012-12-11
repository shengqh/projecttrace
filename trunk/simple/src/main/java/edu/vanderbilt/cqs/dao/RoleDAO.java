package edu.vanderbilt.cqs.dao;

import java.util.List;

import edu.vanderbilt.cqs.bean.Role;

public interface RoleDAO extends GenericDAO<Role, Long> {
	Role findByName(String name);
	List<Role> listRoles();
}
