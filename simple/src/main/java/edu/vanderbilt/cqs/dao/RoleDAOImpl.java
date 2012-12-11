package edu.vanderbilt.cqs.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.Role;

@Repository
public class RoleDAOImpl extends GenericDAOImpl<Role, Long> implements RoleDAO {

	@Override
	public Role findByName(String name) {
		return (Role) (getSession().createCriteria(getPersistentClass()).add(
				Restrictions.eq("name", name)).uniqueResult());
	}
}
