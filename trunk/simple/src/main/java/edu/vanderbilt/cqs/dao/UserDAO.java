package edu.vanderbilt.cqs.dao;

import java.util.List;

import edu.vanderbilt.cqs.bean.User;

public interface UserDAO extends GenericDAO<User, Long> {
	User findByEmail(String email);
	
	boolean hasUser(Long id);
	
	void updatePassword(Long id, String newPassword);
	
	List<User> listValidUser();
	
	List<User> listInvalidUser();
	
	List<User> listValidUser(Integer role);
}
