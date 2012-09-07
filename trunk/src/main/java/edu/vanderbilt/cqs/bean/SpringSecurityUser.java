package edu.vanderbilt.cqs.bean;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class SpringSecurityUser extends org.springframework.security.core.userdetails.User {
	private static final long serialVersionUID = 495127224268042426L;
	
	public SpringSecurityUser(Long id, Integer role, String username, String password,
			boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		this.id = id;
		this.role = role;
	}

	private Long id;
	
	private Integer role;

	public Long getId() {
		return id;
	}

	public Integer getRole() {
		return role;
	}
}
