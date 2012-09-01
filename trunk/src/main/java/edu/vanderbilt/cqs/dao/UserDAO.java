package edu.vanderbilt.cqs.dao;

import edu.vanderbilt.cqs.bean.User;

public interface UserDAO extends GenericDAO<User, Integer> {
	User validateUser(User user);
}
