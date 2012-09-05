package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import edu.vanderbilt.cqs.Utils;

public class UserForm implements Serializable {

	private static final long serialVersionUID = -2055034778802964462L;

	private Long id;

	private String firstname;

	private String lastname;

	@Email
	@NotEmpty
	private String email;

	private String telephone;

	private Boolean enabled;

	private Boolean locked;

	private Boolean expired;

	private Integer role;
	
	private Map<Integer, String> roles;

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getName() {
		if (this.getFirstname() != null && this.getLastname() != null) {
			return this.getFirstname() + " " + this.getLastname();
		} else if (this.getFirstname() != null) {
			return this.getFirstname();
		} else if (this.getLastname() != null) {
			return this.getLastname();
		} else {
			return "";
		}
	}

	public String getRoleName() {
		return Utils.getRoleMap().get(this.role);
	}

	public Map<Integer, String> getRoles() {
		return roles;
	}

	public void setRoles(Map<Integer, String> roles) {
		this.roles = roles;
	}
}
