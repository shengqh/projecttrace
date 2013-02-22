package edu.vanderbilt.cqs.dao;

import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.RolePermission;

@Repository
public class RolePermissionDAOImpl extends GenericDAOImpl<RolePermission, Long>
		implements RolePermissionDAO {
}
