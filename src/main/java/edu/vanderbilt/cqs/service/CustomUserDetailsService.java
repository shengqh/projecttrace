package edu.vanderbilt.cqs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.vanderbilt.cqs.Role;
import edu.vanderbilt.cqs.bean.SpringSecurityUser;
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

			boolean credentialsNonExpired = true;

			return new SpringSecurityUser(domainUser.getId(),
					domainUser.getRole(), domainUser.getEmail(), domainUser
							.getPassword().toLowerCase(),
					domainUser.getEnabled(), !domainUser.getExpired(),
					credentialsNonExpired, !domainUser.getLocked(),
					getAuthorities(domainUser.getRole()));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieves a collection of {@link GrantedAuthority} based on a numerical
	 * role
	 * 
	 * @param role
	 *            the numerical role
	 * @return a collection of {@link GrantedAuthority
	 */
	public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}

	/**
	 * Converts a numerical role to an equivalent list of roles
	 * 
	 * @param role
	 *            the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	public List<String> getRoles(Integer role) {
		List<String> roles = new ArrayList<String>();

		if (role.intValue() >= Role.OBSERVER) {
			roles.add(Role.getRoleMap().get(Role.OBSERVER));
		}

		if (role.intValue() >= Role.USER) {
			roles.add(Role.getRoleMap().get(Role.USER));
		}

		if (role.intValue() >= Role.MANAGER) {
			roles.add(Role.getRoleMap().get(Role.MANAGER));
		}

		if (role.intValue() >= Role.ADMIN) {
			roles.add(Role.getRoleMap().get(Role.ADMIN));
		}

		return roles;
	}

	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 * 
	 * @param roles
	 *            {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(
			List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
}
