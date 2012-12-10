package edu.vanderbilt.cqs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.vanderbilt.cqs.dao.UserDAO;

/**
 * A custom {@link UserDetailsService} where user information is retrieved from
 * a JPA repository
 */
@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	/**
	 * Returns a populated {@link UserDetails} object. The username is first
	 * retrieved from the database and then mapped to a {@link UserDetails}
	 * object.
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		try {
			edu.vanderbilt.cqs.bean.User domainUser = userDAO
					.findByEmail(username);

			if (domainUser == null) {
				throw new UsernameNotFoundException(username);
			}

			domainUser.getAuthorities();

			return domainUser;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
