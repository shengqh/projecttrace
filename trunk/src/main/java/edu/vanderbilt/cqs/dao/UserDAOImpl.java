package edu.vanderbilt.cqs.dao;

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
}
