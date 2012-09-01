package edu.vanderbilt.cqs.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.User;

@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Integer> implements
		UserDAO {
	@Override
	public User validateUser(User user) {
		return (User) (getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("email", user.getEmail()))
				.add(Restrictions.eq("md5password", user.getMd5password()))
				.uniqueResult());
	}
}
