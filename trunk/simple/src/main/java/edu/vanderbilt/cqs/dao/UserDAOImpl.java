package edu.vanderbilt.cqs.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.User;

@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAO {
	@Override
	public User findByEmail(String email) {
		return (User) (getSession().createCriteria(getPersistentClass()).add(
				Restrictions.eq("email", email)).uniqueResult());
	}

	@Override
	public boolean hasUser(Long id) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.list().get(0)).longValue() > 0;
	}

	@Override
	public void updatePassword(Long id, String newPassword) {
		String hql = "update User set password = :newPassword where id = :id";
		Query query = getSession().createQuery(hql);
		query.setString("newPassword", newPassword);
		query.setLong("id", id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listValidUser() {
		String hql = "from User where (enabled=:enabled) and (accountNonLocked = :accountNonLocked) and (accountNonExpired = :accountNonExpired) and (accountNonDeleted = :accountNonDeleted) order by email";
		Query query = getSession().createQuery(hql);
		query.setParameter("enabled", true);
		query.setParameter("accountNonLocked", true);
		query.setParameter("accountNonExpired", true);
		query.setParameter("accountNonDeleted", true);
		return (List<User>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listInvalidUser() {
		String hql = "from User where (enabled=:enabled) or (accountNonLocked = :accountNonLocked) or (accountNonExpired = :accountNonExpired) or (accountNonDeleted = :accountNonDeleted) order by email";
		Query query = getSession().createQuery(hql);
		query.setParameter("enabled", false);
		query.setParameter("accountNonLocked", false);
		query.setParameter("accountNonExpired", false);
		query.setParameter("accountNonDeleted", false);
		return (List<User>) query.list();
	}
}
