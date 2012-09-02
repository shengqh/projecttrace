package edu.vanderbilt.cqs.dao;

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
}
