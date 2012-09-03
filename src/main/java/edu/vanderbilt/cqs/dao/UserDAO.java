package edu.vanderbilt.cqs.dao;

import edu.vanderbilt.cqs.bean.User;

public interface UserDAO extends GenericDAO<User, Long> {
	User validateUser(User user);
	
	User findByEmail(String email);
	
	boolean hasUser(Long id);
	
	void updatePassword(Long id, String newPassword);
}
