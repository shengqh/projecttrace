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
	public User validateUser(User user) {
		return (User) (getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("email", user.getEmail()))
				.add(Restrictions.eq("password", user.getPassword()))
				.uniqueResult());
	}

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
	public List<User> getActiveUsers() {
		String hql = "from User where enabled=:enabled and (locked = :locked) and (expired = :expired)";
		Query query = getSession().createQuery(hql);
		query.setParameter("enabled", true);
		query.setParameter("locked", false);
		query.setParameter("expired", false);
		return (List<User>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getActiveUsers(Integer role) {
		String hql = "from User where enabled=:enabled and (locked = :locked) and (expired = :expired) and (role=:role)";
		Query query = getSession().createQuery(hql);
		query.setParameter("enabled", true);
		query.setParameter("locked", false);
		query.setParameter("expired", false);
		query.setParameter("role", role);
		return (List<User>) query.list();
	}
}
