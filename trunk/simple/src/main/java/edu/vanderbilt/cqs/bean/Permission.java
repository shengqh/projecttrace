package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PERMISSION")
public class Permission implements Serializable {

	private static final long serialVersionUID = 2538772037653034859L;
	public static final String ROLE_PROJECT_VIEW = "ROLE_PROJECT_VIEW";
	public static final String ROLE_PROJECT_EDIT = "ROLE_PROJECT_EDIT";
	public static final String ROLE_USER_VIEW = "ROLE_USER_VIEW";
	public static final String ROLE_USER_EDIT = "ROLE_USER_EDIT";

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name = "";
	
	@OneToMany(mappedBy = "permission", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval=true)
	private Set<RolePermission> roles = new HashSet<RolePermission>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permission other = (Permission) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Set<RolePermission> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolePermission> roles) {
		this.roles = roles;
	}
}
