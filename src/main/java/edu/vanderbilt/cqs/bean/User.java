package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.ArrayList;
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

import edu.vanderbilt.cqs.Role;

@Entity
@Table(name = "USER")
public class User implements Serializable {
	private static final long serialVersionUID = 7401126221031716368L;

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;

	@Column(name="FIRSTNAME")
	private String firstname = "";

	@Column(name="LASTNAME")
	private String lastname = "";

	@Column(unique = true, name="EMAIL")
	private String email;

	@Column(name="TELEPHONE")
	private String telephone = "";

	@Column(name="CREATEDATE")
	private Date createDate;

	@Column(name="ENABLED")
	private Boolean enabled = true;

	@Column(name="LOCKED")
	private Boolean locked = false;

	@Column(name="EXPIRED")
	private Boolean expired = false;

	@Column(name="DELETED")
	private Boolean deleted = false;

	@Column(name="PASSWORD")
	private String password;

	@Column(name="ROLE")
	private Integer role;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Set<ProjectUser> projects = new HashSet<ProjectUser>();

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
		return Role.getRoleMap().get(this.role);
	}

	public boolean isValid() {
		return enabled && (!locked) && (!expired) && (!deleted);
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Set<ProjectUser> getProjects() {
		return projects;
	}

	public void setProjects(Set<ProjectUser> projects) {
		this.projects = projects;
	}

	public List<Project> getProjectList() {
		List<Project> result = new ArrayList<Project>();
		for (ProjectUser pu : getProjects()) {
			result.add(pu.getProject());
		}
		return result;
	}
}
