package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
@Table(name = "ROLE")
public class Role implements Serializable, Comparable<Role> {
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_VANGARD_ADSTAFF = "ROLE_VANGARD_ADSTAFF";
	public static final String ROLE_VANGARD_FACULTY = "ROLE_VANGARD_FACULTY";
	public static final String ROLE_VANGARD_STAFF = "ROLE_VANGARD_STAFF";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	private static final long serialVersionUID = -6330012869746878952L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME",unique=true)
	private String name = "";

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval=true)
	private Set<UserRole> roles = new HashSet<UserRole>();

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval=true)
	private Set<RolePermission> permissions = new HashSet<RolePermission>();

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

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
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
		Role other = (Role) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Set<RolePermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<RolePermission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Role o) {
		if(o == null){
			return -1;
		}
		
		if(this.name == null){
			return -1;
		}
		
		return this.name.compareTo(o.name);
	}
	
	public Set<Long> getPermissionIds(){
		Set<Long> result = new LinkedHashSet<Long>();
		for(RolePermission rp:permissions){
			result.add(rp.getPermission().getId());
		}
		return result;
	}
}
