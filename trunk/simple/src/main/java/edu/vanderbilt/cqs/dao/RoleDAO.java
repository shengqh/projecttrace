package edu.vanderbilt.cqs.dao;

import edu.vanderbilt.cqs.bean.Role;

public interface RoleDAO extends GenericDAO<Role, Long> {
	Role findByName(String name);
}
