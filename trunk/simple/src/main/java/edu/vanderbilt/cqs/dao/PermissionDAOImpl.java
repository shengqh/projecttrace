package edu.vanderbilt.cqs.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.Permission;

@Repository
public class PermissionDAOImpl extends GenericDAOImpl<Permission, Long>
		implements PermissionDAO {

	@Override
	public Permission findByName(String name) {
		return (Permission) (getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("name", name)).uniqueResult());
	}
}
