package edu.vanderbilt.cqs.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = 8773798041075554951L;

	public static final Integer ADMIN = 1000;
	public static final Integer MANAGER = 100;
	public static final Integer USER = 10;
	public static final Integer OBSERVER = 1;

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private User user;

	@Column
	private Integer role;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

}
