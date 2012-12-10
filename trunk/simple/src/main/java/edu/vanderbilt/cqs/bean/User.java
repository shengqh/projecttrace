package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USER")
public class User implements Serializable, UserDetails {
	private static final long serialVersionUID = 7401126221031716368L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "FIRSTNAME")
	private String firstname = "";

	@Column(name = "LASTNAME")
	private String lastname = "";

	@Column(unique = true, name = "EMAIL")
	private String email;

	@Column(name = "TELEPHONE")
	private String telephone = "";

	@Column(name = "CREATEDATE")
	private Date createDate;

	@Column(name = "ENABLED")
	private Boolean enabled = true;

	@Column(name = "ACCOUNT_NOT_LOCKED")
	private Boolean accountNonLocked = true;

	@Column(name = "ACCOUNT_NOT_EXPIRED")
	private Boolean accountNonExpired = true;

	@Column(name = "DELETED")
	private Boolean deleted = false;

	@Column(name = "CREDENTIALS_NOT_EXPIRED")
	private Boolean credentialsNonExpired = true;

	@Column(name = "PASSWORD")
	private String password;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Set<UserRole> roles = new HashSet<UserRole>();

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static boolean notEmpty(String s) {
		return (s != null && s.length() > 0);
	}

	public String getName() {
		if (notEmpty(firstname) && notEmpty(lastname)) {
			return this.firstname + " " + lastname;
		} else if (notEmpty(this.firstname)) {
			return this.firstname;
		} else if (notEmpty(this.lastname)) {
			return this.lastname;
		} else {
			return this.email;
		}
	}

	public boolean isValid() {
		return enabled && (!accountNonLocked) && (!accountNonExpired)
				&& (!deleted);
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
		for (UserRole role : roles) {
			result.add(new SimpleGrantedAuthority(role.getRole().getName()));
		}
		return result;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public boolean hasRole(String roleName) {
		for (UserRole role : roles) {
			if (role.getRole().getName().equals(roleName)) {
				return true;
			}
		}
		return false;
	}
}
