package edu.vanderbilt.cqs.dao;

import java.util.List;

import org.hibernate.criterion.Order;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> listRoles() {
		return getSession().createCriteria(getPersistentClass())
				.addOrder(Order.asc("id")).list();
	}
}
