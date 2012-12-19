package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import edu.vanderbilt.cqs.bean.Role;

public class UserForm implements Serializable {

	private static final long serialVersionUID = -2055034778802964462L;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonDeleted() {
		return accountNonDeleted;
	}

	public void setAccountNonDeleted(Boolean accountNonDeleted) {
		this.accountNonDeleted = accountNonDeleted;
	}

	private String firstname = "";

	private String lastname = "";

	@Email
	@NotBlank
	private String email;

	private String telephone = "";

	private Boolean enabled;

	private Boolean accountNonLocked;

	private Boolean accountNonExpired;

	private Boolean accountNonDeleted;
	
	private Boolean isContact = false;

	public Boolean getIsContact() {
		return isContact;
	}

	public void setIsContact(Boolean isContact) {
		this.isContact = isContact;
	}

	private Set<Role> roleList;

	private Set<Long> roles;

	public Set<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(Set<Role> roleList) {
		this.roleList = roleList;
	}

	public Set<Long> getRoles() {
		return roles;
	}

	public void setRoles(Set<Long> roles) {
		this.roles = roles;
	}
}
