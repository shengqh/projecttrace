package edu.vanderbilt.cqs.dao;

import edu.vanderbilt.cqs.bean.Permission;

public interface PermissionDAO extends GenericDAO<Permission, Long> {
	Permission findByName(String name);
}
